package no.nav.spanner.spurtedu

import com.github.navikt.tbd_libs.azure.AzureTokenProvider
import com.github.navikt.tbd_libs.result_object.Result
import tools.jackson.core.exc.StreamReadException
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.readValue
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.UUID

class SpurteDuClient(
    private val httpClient: HttpClient,
    private val objectMapper: ObjectMapper,
    private val tokenProvider: AzureTokenProvider,
    private val baseUrl: String,
    private val scope: String,
) {
    fun vis(
        id: UUID,
        onBehalfOfToken: String?,
    ): VisTekstResponse {
        val tokenResult = onBehalfOfToken?.let { tokenProvider.onBehalfOfToken(scope, onBehalfOfToken) } ?: tokenProvider.bearerToken(scope)
        if (tokenResult is Result.Error) throw SpurteDuException("Kunne ikke hente token fra azure: ${tokenResult.error}", tokenResult.cause)
        tokenResult as Result.Ok
        val bearerToken = tokenResult.value.token
        val request =
            HttpRequest
                .newBuilder()
                .uri(URI("$baseUrl/vis_meg/$id"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer $bearerToken")
                .header("callId", "${UUID.randomUUID()}")
                .GET()
                .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200) {
            throw SpurteDuException("Finner ikke hemmeligheten, mulig det er krøll med tilganger (status code ${response.statusCode()})", null)
        }
        return try {
            objectMapper.readValue<VisTekstResponse>(response.body())
        } catch (err: StreamReadException) {
            throw SpurteDuException(err.message ?: "JSON parsing error", err)
        }
    }
}

data class VisTekstResponse(
    val text: String,
)

class SpurteDuException(
    override val message: String,
    override val cause: Throwable?,
) : RuntimeException()
