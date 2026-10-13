package no.nav.spanner.spurtedu

import com.github.navikt.tbd_libs.azure.AzureToken
import com.github.navikt.tbd_libs.azure.AzureTokenProvider
import com.github.navikt.tbd_libs.mock.MockHttpResponse
import com.github.navikt.tbd_libs.result_object.Result
import com.github.navikt.tbd_libs.result_object.ok
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tools.jackson.module.kotlin.jacksonObjectMapper
import java.net.http.HttpClient
import java.time.LocalDateTime
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

class SpurteDuClientTest {
    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `veksler til 'on behalf of' token hvis det er satt`() {
        val (spurteDuClient, httpClient) = mockClient(okVisResponse)
        spurteDuClient.vis(UUID.randomUUID(), "et_bruker_token")
        verifiserRequestHeader(httpClient, "Authorization") { verdi ->
            verdi == "Bearer on_behalf_of_token"
        }
    }

    @Test
    fun `veksler til bearer token hvis obo-token ikke er satt`() {
        val (spurteDuClient, httpClient) = mockClient(okVisResponse)
        spurteDuClient.vis(UUID.randomUUID(), null)
        verifiserRequestHeader(httpClient, "Authorization") { verdi ->
            verdi == "Bearer bearer_token"
        }
    }

    @Test
    fun `vise skjult verdi - uten tilgang`() {
        val (spurteDuClient, httpClient) = mockClient(errorVisResponse, 404)
        val secret = UUID.fromString("2d05217c-1c16-4581-b4e8-08e115a2274d")
        assertThrows<SpurteDuException> { spurteDuClient.vis(secret, "et feilaktig token") }
        verify {
            httpClient.send<String>(match { request -> request.method().uppercase() == "GET" }, any())
        }
    }

    @Test
    fun `vise skjult verdi - med tilgang`() {
        val (spurteDuClient, _) = mockClient(okVisResponse)
        val secret = UUID.fromString("2d05217c-1c16-4581-b4e8-08e115a2274d")
        val response = spurteDuClient.vis(secret, "et ok token")
        assertEquals("en jsonmelding", objectMapper.readTree(response.text).path("foo").asString())
    }

    private fun mockClient(
        response: String,
        statusCode: Int = 200,
    ): Pair<SpurteDuClient, HttpClient> {
        val httpClient =
            mockk<HttpClient> {
                every {
                    send<String>(any(), any())
                } returns MockHttpResponse(response, statusCode)
            }
        val tokenProvider =
            object : AzureTokenProvider {
                override fun onBehalfOfToken(
                    scope: String,
                    token: String,
                ): Result<AzureToken> = AzureToken("on_behalf_of_token", LocalDateTime.now()).ok()

                override fun bearerToken(scope: String): Result<AzureToken> = AzureToken("bearer_token", LocalDateTime.now()).ok()
            }
        val spurteDuClient = SpurteDuClient(httpClient, objectMapper, tokenProvider, "http://spurtedu", "api://test.tbd.spurtedu/.default")
        return spurteDuClient to httpClient
    }

    private fun verifiserRequestHeader(
        httpClient: HttpClient,
        headerName: String,
        verifisering: (String?) -> Boolean,
    ) {
        verify {
            httpClient.send<String>(
                match { request ->
                    verifisering(request.headers().firstValue(headerName).getOrNull())
                },
                any(),
            )
        }
    }

    @Language("JSON")
    private val okVisResponse = """{
    "text": "{ \"foo\": \"en jsonmelding\" }"
}"""

    private val errorVisResponse = """fant ikke noe spesielt til deg"""
}
