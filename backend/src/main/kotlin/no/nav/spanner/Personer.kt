package no.nav.spanner

import com.auth0.jwt.JWT
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.features.*
import io.ktor.http.*
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger(Spleis::class.java)

interface Personer {
    suspend fun person(id: String, idType: IdType, accessToken: String): String
    suspend fun hendelse(meldingsreferanse: String, accessToken: String): String
}

class Spleis(private val azureAD: AzureAD, private val baseUrl: String = "http://spleis-api.tbd.svc.cluster.local") :
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
        )
    }

    override suspend fun person(id: String, idType: IdType, accessToken: String): String {
     val url = URLBuilder(baseUrl).path("api", "person-json").build()
        val oboToken =
            token(accessToken)
        val log = Log.logger(Personer::class.java)
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
                if(e.response.status == HttpStatusCode.NotFound) {
                    throw NotFoundException("Fant ikke person")
                }
                throw e
            }
        log
            .response(response)
            .info("Response from spleis")
        return response.readText()
    }

    override suspend fun hendelse(meldingsreferanse: String, accessToken: String): String {
     val url = URLBuilder(baseUrl).path("api", "hendelse-json", meldingsreferanse).build()
        val oboToken =
            token(accessToken)
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
            if(e.response.status == HttpStatusCode.NotFound) {
                throw NotFoundException("Fant ikke melding med referanse $meldingsreferanse")
            }
            throw e
        }
        log
            .response(response)
            .info("Response from spleis")
        return response.readText()
    }


    private suspend fun token(accessToken: String) = (azureAD.hentOnBehalfOfToken(JWT.decode(accessToken)))
}

object LokaleKjenninger : Personer {
    override suspend fun person(id: String, idType: IdType, accessToken: String) = when (id) {
        "42",
        "12020052345" -> lesJson("12020052345")
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
