package no.nav.spanner

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import no.nav.security.mock.oauth2.MockOAuth2Server
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class E2ETest {
    private val azureADConfig = AzureADConfig(
        discoveryUrl = mockAuth.wellKnownUrl("default").toString(),
        clientId = "whatever",
        clientSecret = "supersecret",
        spleisClientId = "los spleisos"
    )

    private val azureAD = AzureAD(azureADConfig)
    private val spleis = LokaleKjenninger

    fun TestApplicationEngine.login() {
        val loginLocation = expectRedirect("/login")
        val httpClient = HttpClient(Apache) {
            followRedirects = false
            this.expectSuccess = false
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }
        val authResponse = runBlocking { httpClient.get<HttpResponse>(loginLocation).receive<HttpResponse>() }
        assertEquals(HttpStatusCode.Found, authResponse.status)
        val authLocation = authResponse.headers["location"]!!
        val callbackResponse = handleRequest(HttpMethod.Get, authLocation.removePrefix("http://localhost")).response
    }

    private fun TestApplicationEngine.expectRedirect(url: String): String {
        val response = handleRequest(HttpMethod.Get, url).response
        assertEquals(HttpStatusCode.Found, response.status())
        return response.headers["location"]!!
    }

    @Test
    fun login() {
        withTestApplication({
            configuredModule(spleis, azureADConfig, EnvType.LOCAL)
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
            configuredModule(spleis, azureADConfig, EnvType.LOCAL)
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
        withTestApplication({
            configuredModule(spleis, azureADConfig, EnvType.LOCAL)
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