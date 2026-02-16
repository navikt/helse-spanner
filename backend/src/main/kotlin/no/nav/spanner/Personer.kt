package no.nav.spanner

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.util.RawValue
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.navikt.tbd_libs.azure.AzureTokenProvider
import com.github.navikt.tbd_libs.result_object.getOrThrow
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
import java.time.LocalDate
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.*
import no.nav.spanner.requests.HentAltSpiskammersetRequest

interface Personer {
    suspend fun person(call: ApplicationCall, fnr: String, aktørId: String)
    suspend fun speilperson(call: ApplicationCall, fnr: String)
    suspend fun spiskammersetPerioder(call: ApplicationCall, fnr: String, fom: LocalDate, tom: LocalDate)
    suspend fun spiskammersetOpplysninger(call: ApplicationCall, behandlingId: UUID, opplysninger: List<String>)
    suspend fun spiskammersetHentAlt(call: ApplicationCall, request: HentAltSpiskammersetRequest)
    suspend fun hendelse(call: ApplicationCall, meldingsreferanse: String)
}

class Spleis(
    private val azureAD: AzureTokenProvider,
    private val baseUrl: String = "http://spleis-api.tbd.svc.cluster.local",
    spleisScope: String,
    private val sparsomBaseUrl: String = "http://sparsom-api.tbd.svc.cluster.local",
    private val spiskammersetBaseUrl: String = "http://spiskammerset.tbd.svc.cluster.local",
    sparsomScope: String,
    spiskammersetScope: String?
) : Personer {
    private val spleis = ClientIdWithName(spleisScope, "spleis")
    private val sparsom = ClientIdWithName(sparsomScope, "sparsom")
    private val spiskammerset = spiskammersetScope?.let { ClientIdWithName(it, "spiskammerset") }
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
            spiskammersetScope = spannerConfig.spiskammersetScope
        )

        private val ApplicationCall.bearerToken: String? get() {
            val httpAuthHeader = request.parseAuthorizationHeader() ?: return null
            if (httpAuthHeader !is HttpAuthHeader.Single) return null
            return httpAuthHeader.blob
        }
    }

    override suspend fun person(call: ApplicationCall, fnr: String, aktørId: String) {
        val accessToken = call.bearerToken ?: return call.respond(Unauthorized)
        val url = URLBuilder(baseUrl).apply {
            path("api", "person-json")
        }.build()

        val log = Log.logger(this::class.java)

        val response = coroutineScope {
            val aktivitetslogg: Deferred<String?> = async(Dispatchers.IO) {
                aktivitetslogg(accessToken, fnr, call.callId ?: UUID.randomUUID().toString())
            }

            val oboToken = spleis.token(azureAD, accessToken)

            val response =
                try {
                    httpClient.post(url) {
                        header("Authorization", "Bearer $oboToken")
                        accept(Json)
                        contentType(Json)
                        setBody(mapOf(
                            "fødselsnummer" to fnr
                        ))
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
            node.put("aktørId", aktørId)
            node.putRawValue("aktivitetsloggV2", RawValue(aktivitetslogg.await()))
            node.toString()
        }
        call.respondText(response, Json, OK)
    }

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
                    setBody("""{
            "query": "",
            "variables": {
              "fnr": "$fnr"
            },
            "operationName": "HentSnapshotSpanner"
        }""")
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

    override suspend fun spiskammersetPerioder(call: ApplicationCall, fnr: String, fom: LocalDate, tom: LocalDate) {
        if (spiskammerset == null) return call.respond(HttpStatusCode.NotFound)
        val accessToken = call.bearerToken ?: return call.respond(Unauthorized)
        val url = URLBuilder(spiskammersetBaseUrl).apply { path("perioder") }.build()
        val oboToken = spiskammerset.token(azureAD, accessToken)
        val log = Log.logger(Personer::class.java)

        val response =
            httpClient.post(url) {
                header("Authorization", "Bearer $oboToken")
                accept(Json)
                setBody(
                    """{
                        "personidentifikatorer": ["$fnr"],
                        "fom": "$fom",
                        "tom": "$tom"
                   }"""
                )
            }

        log.response(response).info("Response from spiskammerset")
        val node = objectMapper.readTree(response.bodyAsText()) as ObjectNode
        call.respondText(node.toString(), Json, OK)
    }

    override suspend fun spiskammersetOpplysninger(call: ApplicationCall, behandlingId: UUID, opplysninger: List<String>) {
        if (spiskammerset == null) return call.respond(HttpStatusCode.NotFound)
        val accessToken = call.bearerToken ?: return call.respond(Unauthorized)
        val url = URLBuilder(spiskammersetBaseUrl).apply {
            path("behandling", behandlingId.toString())
            parameters.appendAll("opplysning", opplysninger)
        }.build()
        val oboToken = spiskammerset.token(azureAD, accessToken)
        val log = Log.logger(Personer::class.java)

        val response =
            httpClient.get(url) {
                header("Authorization", "Bearer $oboToken")
                accept(Json)
            }

        log.response(response).info("Response from spiskammerset")
        val node = objectMapper.readTree(response.bodyAsText()) as ObjectNode
        call.respondText(node.toString(), Json, OK)
    }

    override suspend fun spiskammersetHentAlt(call: ApplicationCall, request: HentAltSpiskammersetRequest) {
        if (spiskammerset == null) return call.respond(HttpStatusCode.NotFound)
        val accessToken = call.bearerToken ?: return call.respond(Unauthorized)
        val url = URLBuilder(spiskammersetBaseUrl).apply {
            path("hentAlt")
        }.build()

        val oboToken = spiskammerset.token(azureAD, accessToken)
        val log = Log.logger(Personer::class.java)

        val response =
            httpClient.post(url) {
                header("Authorization", "Bearer $oboToken")
                contentType(Json)
                accept(Json)
                setBody(request)
            }

        log.response(response).info("Response from spiskammerset")
        val node = objectMapper.readTree(response.bodyAsText()) as ObjectNode
        call.respondText(node.toString(), Json, OK)
    }


    private suspend fun aktivitetslogg(accessToken: String, ident: String, callId: String): String? {
        val log = Log.logger(Personer::class.java)
        val oboToken = sparsom.token(azureAD, accessToken)

        log
            .sensitivt("ident", ident)
            .info("henter aktivitetslogg fra sparsom")
        val url = URLBuilder(sparsomBaseUrl).apply {
            path("api", "aktiviteter")
        }.build()
        return try {
            val response = httpClient.post(url) {
                header("Authorization", "Bearer $oboToken")
                header("callId", callId)
                accept(Json)
                contentType(Json)
                setBody(mapOf("ident" to ident))
            }
            log
                .response(response)
                .info("Response from sparsom")
            response.bodyAsText()
        } catch (err : ClientRequestException) {
            log.info("fikk feil fra sparsom: ${err.message}\n${err.stackTraceToString()}")
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
        }.getOrThrow().token
    }
}
