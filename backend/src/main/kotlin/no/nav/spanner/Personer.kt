package no.nav.spanner

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.util.RawValue
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.navikt.tbd_libs.azure.AzureTokenProvider
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.auth.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.response.*
import io.ktor.util.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import java.util.*

val logger = LoggerFactory.getLogger(Spleis::class.java)

interface Personer {
    suspend fun person(call: ApplicationCall, id: String, idType: IdType)
    suspend fun maskerPerson(call: ApplicationCall, id: String, idType: IdType)
    suspend fun speilperson(call: ApplicationCall, fnr: String)
    suspend fun hendelse(call: ApplicationCall, meldingsreferanse: String)
}

class Spleis(
    private val azureAD: AzureTokenProvider,
    private val baseUrl: String = "http://spleis-api.tbd.svc.cluster.local",
    spleisScope: String,
    private val sparsomBaseUrl: String = "http://sparsom-api.tbd.svc.cluster.local",
    sparsomScope: String,
    private val spurteDuClient: SpurteDuClient
) : Personer {
    private val spleis = ClientIdWithName(spleisScope, "spleis")
    private val sparsom = ClientIdWithName(sparsomScope, "sparsom")
    private val httpClient = HttpClient(CIO) {
        engine {
            requestTimeout = 60000
        }
        install(ContentNegotiation) {
            jackson {
                registerModule(JavaTimeModule())
            }
        }
    }

    companion object {
        fun from(spannerConfig: Config, azureAD: AzureTokenProvider) = Spleis(
            azureAD = azureAD,
            baseUrl = spannerConfig.spleisUrl,
            spleisScope = spannerConfig.spleisScope,
            sparsomBaseUrl = spannerConfig.sparsomUrl,
            sparsomScope = spannerConfig.sparsomScope,
            spurteDuClient = SpurteDuClient(objectMapper)
        )

        private val ApplicationCall.bearerToken: String? get() {
            val httpAuthHeader = request.parseAuthorizationHeader() ?: return null
            if (httpAuthHeader !is HttpAuthHeader.Single) return null
            return httpAuthHeader.blob
        }
    }

    override suspend fun maskerPerson(call: ApplicationCall, id: String, idType: IdType) {
        val maskertId = spurteDuClient.utveksle(id, idType) ?: throw BadRequestException("kunne ikke kontakte spurte du")
        call.respondText(""" { "id": "$maskertId" } """, Json, OK)
    }

    override suspend fun person(call: ApplicationCall, id: String, idType: IdType) {
        val accessToken = call.bearerToken ?: return call.respond(Unauthorized)
        val url = URLBuilder(baseUrl).apply {
            if (idType == IdType.MASKERT_ID) path("api", "person-json", id)
            else path("api", "person-json")
        }.build()

        val log = Log.logger(Personer::class.java)

        val response = coroutineScope {
            val aktivitetslogg: Deferred<String?> = async(Dispatchers.IO) {
                aktivitetslogg(accessToken, id, call.callId ?: UUID.randomUUID().toString())
            }

            val oboToken = spleis.token(azureAD, accessToken)

            val response =
                try {
                    httpClient.get(url) {
                        header("Authorization", "Bearer $oboToken")
                        header(idType.header, id)
                        accept(Json)
                    }
                } catch (e: ClientRequestException) {
                    if (e.response.status == HttpStatusCode.NotFound) {
                        throw NotFoundException("Fant ikke person")
                    }
                    throw e
                }

            log.response(response).info("Response from spleis")

            if (response.status == HttpStatusCode.NotFound) throw NotFoundException("Fant ikke person")
            if (response.status.value !in 200..299) throw BadRequestException("Fikk problemer mot spleis")

            val node = objectMapper.readTree(response.bodyAsText()) as ObjectNode
            node.putRawValue("aktivitetsloggV2", RawValue(aktivitetslogg.await()))
            node.toString()
        }
        call.respondText(response, Json, OK)
    }

    @OptIn(InternalAPI::class)
    override suspend fun speilperson(call: ApplicationCall, fnr: String) {
        val accessToken = call.bearerToken ?: return call.respond(Unauthorized)
        val url = URLBuilder(baseUrl).apply { path("graphql") }.build()
        val oboToken = spleis.token(azureAD, accessToken)
        val log = Log.logger(Personer::class.java)

        val response =
            try {
                httpClient.post(url) {
                    header("Authorization", "Bearer $oboToken")
                    accept(Json)
                    body = """{
            "query": "",
            "variables": {
              "fnr": "$fnr"
            },
            "operationName": "HentSnapshotSpanner"
        }"""
                }
            } catch (e: ClientRequestException) {
                if (e.response.status == HttpStatusCode.NotFound) {
                    throw NotFoundException("Fant ikke person")
                }
                throw e
            }

        log
            .response(response)
            .info("Response from spleis")
        val node = objectMapper.readTree(response.bodyAsText()) as ObjectNode
        call.respondText(node.toString(), Json, OK)
    }

    private suspend fun aktivitetslogg(accessToken: String, ident: String, callId: String): String? {
        val log = Log.logger(Personer::class.java)
        val oboToken = sparsom.token(azureAD, accessToken)

        val url = URLBuilder(sparsomBaseUrl).apply {
            path("api", "aktiviteter")
            parameters.append("ident", ident)
        }.build()
        return try {
            val response = httpClient.get(url) {
                header("Authorization", "Bearer $oboToken")
                header("callId", callId)
                accept(Json)
            }
            log
                .response(response)
                .info("Response from sparsom")
            response.bodyAsText()
        } catch (e : ClientRequestException) {
            null
        }
    }

    override suspend fun hendelse(call: ApplicationCall, meldingsreferanse: String) {
        val accessToken = call.bearerToken ?: return call.respond(Unauthorized)
        val url = URLBuilder(baseUrl).apply { path("api", "hendelse-json", meldingsreferanse) }.build()
        val log = Log.logger(Personer::class.java)
        val oboToken = spleis.token(azureAD, accessToken)
        val response = try {
        httpClient.get(url) {
            header("Authorization", "Bearer $oboToken")
            accept(Json)
        }
        } catch (e : ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) {
                throw NotFoundException("Fant ikke melding med referanse $meldingsreferanse")
            }
            throw e
        }
        log
            .response(response)
            .info("Response from spleis")
        call.respondText(response.bodyAsText(), Json, OK)
    }
}

data class ClientIdWithName(val scope: String, val displayName: String) {
    private  companion object {
        val log = Log.logger(this::class.java)
    }
    fun token(azureAD: AzureTokenProvider, accessToken: String): String {
        log.info("Retrieving OBO token for $displayName")
        return azureAD.onBehalfOfToken(scope, accessToken).also {
            log.info("OBO token for $displayName retrieved")
        }.token
    }
}
