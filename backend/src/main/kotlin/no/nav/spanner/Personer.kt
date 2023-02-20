package no.nav.spanner

import com.auth0.jwt.JWT
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.util.RawValue
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.features.*
import io.ktor.http.*
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger(Spleis::class.java)

interface Personer {
    suspend fun person(id: String, idType: IdType, accessToken: String): String
    suspend fun hendelse(meldingsreferanse: String, accessToken: String): String
}

class Spleis(
    private val azureAD: AzureAD,
    private val baseUrl: String = "http://spleis-api.tbd.svc.cluster.local",
    private val spleisClientId: String,
    private val sparsomBaseUrl: String = "http://sparsom-api.tbd.svc.cluster.local",
    private val sparsomClientId: String
) :
    Personer {
    private val httpClient = HttpClient(CIO) {
        engine {
            requestTimeout = 60000
        }
        install(JsonFeature) {
            serializer = JacksonSerializer {
                registerModule(JavaTimeModule())
            }
        }
    }

    companion object {
        fun from(spannerConfig: Config, azureAD: AzureAD) = Spleis(
            azureAD = azureAD,
            baseUrl = spannerConfig.spleisUrl,
            spleisClientId = spannerConfig.spleisClientId,
            sparsomBaseUrl = spannerConfig.sparsomUrl,
            sparsomClientId = spannerConfig.sparsomClientId
        )
    }

    override suspend fun person(id: String, idType: IdType, accessToken: String): String {
        val url = URLBuilder(baseUrl).path("api", "person-json").build()
        val oboToken = token(accessToken, spleisClientId)
        val log = Log.logger(Personer::class.java)
        val aktivitetslogg: Deferred<String?> =
            withContext(Dispatchers.IO) {
                async {
                    aktivitetslogg(accessToken, id)
                }
            }

        log
            .sensitivt("oboTokenLength", oboToken.length)
            .info("Retreiving on behalf of token")
        val response =
            try {
                httpClient.get<HttpResponse>(url) {
                    header("Authorization", "Bearer $oboToken")
                    header(idType.header, id)
                    accept(ContentType.Application.Json)
                }
            } catch (e : ClientRequestException) {
                if (e.response.status == HttpStatusCode.NotFound) {
                    throw NotFoundException("Fant ikke person")
                }
                throw e
            }

        log
            .response(response)
            .info("Response from spleis")
        val node = objectMapper.readTree(response.readText()) as ObjectNode
        node.putRawValue("aktivitetsloggV2", RawValue(aktivitetslogg.await()))
        return node.toString()
    }

    private suspend fun aktivitetslogg(accessToken: String, ident: String): String? {
        val url = URLBuilder(sparsomBaseUrl).path("api", "aktiviteter").apply {
            parameters.append("ident", ident)
        }.build()
        val oboToken = token(accessToken, sparsomClientId)
        val log = Log.logger(Personer::class.java)
        log
            .sensitivt("oboTokenLength", oboToken.length)
            .info("Retreiving on behalf of token")
        try {
            val response = httpClient.get<HttpResponse>(url) {
                header("Authorization", "Bearer $oboToken")
                accept(ContentType.Application.Json)
            }
            log
                .response(response)
                .info("Response from sparsom")
            return response.readText()
        } catch (e : ClientRequestException) {
            return null
        }
    }

    override suspend fun hendelse(meldingsreferanse: String, accessToken: String): String {
     val url = URLBuilder(baseUrl).path("api", "hendelse-json", meldingsreferanse).build()
        val oboToken = token(accessToken, spleisClientId)
        val log = Log.logger(Personer::class.java)
        log
            .sensitivt("oboTokenLength", oboToken.length)
            .info("Retreiving on behalf of token")
        val response = try {
        httpClient.get<HttpResponse>(url) {
            header("Authorization", "Bearer $oboToken")
            accept(ContentType.Application.Json)
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
        return response.readText()
    }


    private suspend fun token(accessToken: String, clientId: String) = (azureAD.hentOnBehalfOfToken(JWT.decode(accessToken), clientId))
}

object LokaleKjenninger : Personer {
    override suspend fun person(id: String, idType: IdType, accessToken: String) = when (id) {
        "42",
        "12020052345" -> lesJson("12020052345")
        "2392363031327" -> lesJson("2392363031327")
        else -> throw NotFoundException("no person with identifier: ${id}")
    }.also {
        logger.trace("person fetched = ${it}")
    }

    override suspend fun hendelse(meldingsreferanse: String, accessToken: String): String {
        return "{}"
    }

    private fun lesJson(filnavn: String) =
        Personer::class.java.getResource("/personer/$filnavn.json")?.readText()!!
}
