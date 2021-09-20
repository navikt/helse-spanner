package no.nav.spanner

import com.auth0.jwt.JWT
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.features.*
import io.ktor.http.*
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger(Spleis::class.java)

interface Personer {
    suspend fun person(id: String, idType: IdType, accessToken: String): String
}

class Spleis(private val azureAD: AzureAD, url: String = "http://spleis-api.tbd.svc.cluster.local") :
    Personer {
    private val httpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = JacksonSerializer {
                registerModule(JavaTimeModule())
            }
        }
    }
    private val url = URLBuilder(url).path("api", "person-json").build()

    companion object {
        fun from(spannerConfig: Config, azureAD: AzureAD) = Spleis(
            azureAD = azureAD,
            url = spannerConfig.spleisUrl,
        )
    }

    override suspend fun person(id: String, idType: IdType, accessToken: String): String {
        val oboToken =
            token(accessToken)
        val log = Log.logger(Personer::class.java)
        log
            .sensitivt("oboTokenLength", oboToken.length)
            .info("Retreiving on behalf of token")
        val response = httpClient.get<HttpResponse>(url) {
            header("Authorization", "Bearer $oboToken")
            header(idType.header, id)
            accept(ContentType.Application.Json)
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

    private fun lesJson(filnavn: String) =
        Personer::class.java.getResource("/personer/$filnavn.json")?.readText()!!
}