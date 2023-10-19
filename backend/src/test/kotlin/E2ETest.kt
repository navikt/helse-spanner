package no.nav.spanner

import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class E2ETest {

    private val azureADConfig = AzureADConfig(
        discoveryUrl = mockAuth.wellKnownUrl("default").toString(),
        clientId = "whatever",
        clientSecret = "supersecret"
    )

    private val lokaleKjenninger = LokaleKjenninger

    fun TestApplicationEngine.login() {
        val loginLocation = expectRedirect("/login")
        val httpClient = HttpClient(Apache) {
            followRedirects = false
            this.expectSuccess = false
            install(ContentNegotiation) {
                jackson()
            }
        }
        val authResponse = runBlocking { httpClient.get(loginLocation).body<HttpResponse>() }
        assertEquals(HttpStatusCode.Found, authResponse.status)
        val authLocation = authResponse.headers["location"]!!
        handleRequest(HttpMethod.Get, authLocation.removePrefix("http://localhost")).response
    }

    private fun TestApplicationEngine.expectRedirect(url: String): String {
        val response = handleRequest(HttpMethod.Get, url).response
        assertEquals(HttpStatusCode.Found, response.status())
        return response.headers["location"]!!
    }

    @Test
    fun login() {

        withTestApplication({
            spanner(lokaleKjenninger, azureADConfig, true)
        }) {
            cookiesSession {
                handleRequest(HttpMethod.Get, "/") { }
                    .apply {
                        assertEquals(HttpStatusCode.Found, response.status())
                    }
                login()
                handleRequest(HttpMethod.Get, "/") { }
                    .apply {
                        assertEquals(HttpStatusCode.OK, response.status())
                    }
            }
        }
    }

    @Test
    fun `respond with redirect on no session`() {

        withTestApplication({
            spanner(lokaleKjenninger, azureADConfig, true)
        }) {
            handleRequest(HttpMethod.Get, "/api/person/") {
                this.addHeader(IdType.FNR.header, "12345678910")
            }.apply {
                assertEquals(HttpStatusCode.Found, response.status())
            }
        }
    }

    @Test
    fun `respond with person json on person endpoint`() {
        mockAuth.enqueueCallback(
            DefaultOAuth2TokenCallback(
                claims = mapOf("NAVident" to "H12345")
            )
        )
        withTestApplication({
            spanner(lokaleKjenninger, azureADConfig, true)
        }) {
            cookiesSession {
                login()
                handleRequest(HttpMethod.Get, "/api/person/") {
                    this.addHeader(IdType.FNR.header, "42")
                }.apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                    assertTrue(
                        response.content?.contains("\"aktørId\": \"42\"") ?: false,
                        "response was: ${response.content}"
                    )
                }
            }

        }
    }

    @Test
    fun `respond with message json on hendelse endpoint`() {
        mockAuth.enqueueCallback(
            DefaultOAuth2TokenCallback(
                claims = mapOf("NAVident" to "H12345")
            )
        )
        withTestApplication({
            spanner(lokaleKjenninger, azureADConfig, true)
        }) {
            cookiesSession {
                login()
                handleRequest(HttpMethod.Get, "/api/hendelse/42").apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                    assertTrue(
                        response.content == "{}"
                    )
                }
            }

        }
    }

    @Test
    fun `session should refresh token when access token expires`() {
        mockAuth.enqueueCallback(
            DefaultOAuth2TokenCallback(
                expiry = 1,
                claims = mapOf("NAVident" to "H12345")
            )
        )
        withTestApplication({
            spanner(lokaleKjenninger, azureADConfig, true)
        }) {
            cookiesSession {
                login()
                handleRequest(HttpMethod.Get, "/api/person/") {
                    this.addHeader(IdType.FNR.header, "42")
                }.apply {
                    assertEquals(HttpStatusCode.OK, response.status())
                    assertTrue(
                        response.content?.contains("\"aktørId\": \"42\"") ?: false,
                        "response was: ${response.content}"
                    )
                }
            }
        }
    }

    @Test
    fun `valid json error message response`() {
        withTestApplication({
            spanner(lokaleKjenninger, azureADConfig, true)
        }) {
            cookiesSession {
                login()
                handleRequest(HttpMethod.Get, "/api/person/") { /* ingen ident i header */ }
                    .apply {
                        assertEquals(HttpStatusCode.InternalServerError, response.status())
                        val feil = objectMapper.readValue<FeilRespons>(response.content!!)
                        assertTrue(!feil.description.isNullOrEmpty())
                    }
            }
        }
    }

    companion object {
        val mockAuth = MockOAuth2Server()

        @BeforeAll
        @JvmStatic
        fun setupMock() {
            mockAuth.start()
        }

        @AfterAll
        @JvmStatic
        fun shutdownMock() {
            mockAuth.shutdown()
        }
    }
}
