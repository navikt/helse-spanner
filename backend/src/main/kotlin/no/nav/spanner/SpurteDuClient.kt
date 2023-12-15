package no.nav.spanner

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.server.plugins.*
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration


class SpurteDuClient(
    private val objectMapper: ObjectMapper
) {
    private companion object {
        private const val tbdgruppeProd = "f787f900-6697-440d-a086-d5bb56e26a9c"
    }
    fun utveksle(ident: String, identtype: IdType): String? {
        val httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build()

        val jsonInputString = objectMapper.writeValueAsString(mapOf(
            "tekst" to objectMapper.writeValueAsString(mapOf(
                "ident" to ident,
                "identtype" to when (identtype) {
                    IdType.FNR, IdType.AKTORID -> identtype.name
                    else -> throw BadRequestException("støtter kun fnr og aktorid")
                }
            )),
            "påkrevdTilgang" to tbdgruppeProd
        ))

        val request = HttpRequest.newBuilder()
            .uri(URI("http://spurtedu/skjul_meg"))
            .timeout(Duration.ofSeconds(10))
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return try {
            objectMapper.readTree(response.body()).path("id").asText()
        } catch (err: JsonParseException) {
            null
        }
    }
}