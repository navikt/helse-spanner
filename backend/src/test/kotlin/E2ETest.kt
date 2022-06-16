package no.nav.spanner

import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.JacksonConverter
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
        clientSecret = "supersecret",
        spleisClientId = "los spleisos"
    )

    private val azureAD = AzureAD(azureADConfig)
    private val spleis = LokaleKjenninger

    suspend fun ApplicationTestBuilder.login() {
        val loginLocation = expectRedirect("/login")
        val httpClient = HttpClient(Apache) {
            followRedirects = false
            this.expectSuccess = false
            install(ContentNegotiation) {
                register(ContentType.Application.Json, JacksonConverter())
            }
        }
        val authResponse = runBlocking { httpClient.prepareGet(loginLocation).body<HttpResponse>() }
        assertEquals(HttpStatusCode.Found, authResponse.status)
        val authLocation = authResponse.headers["location"]!!
        client.prepareGet(authLocation.removePrefix("http://localhost")).execute()
    }

    private suspend fun ApplicationTestBuilder.expectRedirect(url: String): String {
        val response = client.prepareGet(url).execute()
        assertEquals(HttpStatusCode.Found, response.status)
        return response.headers["location"]!!
    }

    @Test
    fun login() {
        testApplication {
            val client = createClient {
                install(HttpCookies)
            }
            application { spanner(spleis, azureADConfig, true) }
            val response = client.get("/")

            assertEquals(HttpStatusCode.Found, response.status)
            login()
            assertEquals(HttpStatusCode.OK, response.status)

        }

    }

    @Test
    fun `respond with redirect on no session`() {
        testApplication {
            application { spanner(spleis, azureADConfig, true) }

            val response = client.get("/api/person/") {
                header(IdType.FNR.header, "12345678910")
            }
            assertEquals(HttpStatusCode.Found, response.status)
        }
    }

    @Test
    fun `respond with person json on person endpoint`() {
        mockAuth.enqueueCallback(
            DefaultOAuth2TokenCallback(
                claims = mapOf("NAVident" to "H12345")
            )
        )
        println(" mockAuthport: " + mockAuth.config.httpServer.port())

        testApplication {
            val client = createClient {
                install(HttpCookies)
            }
            application { spanner(spleis, azureADConfig, true) }

            val response = client.get("/api/person/") {
                header(IdType.FNR.header, "42")
            }

            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(
                response.bodyAsText().contains("\"aktørId\": \"42\""),
                "response was: ${response.bodyAsText()}"
            )
        }
    }

    @Test
    fun `respond with message json on hendelse endpoint`() {
        mockAuth.enqueueCallback(
            DefaultOAuth2TokenCallback(
                claims = mapOf("NAVident" to "H12345")
            )
        )

        testApplication {

            val client = createClient {
                install(HttpCookies)
            }
            application { spanner(spleis, azureADConfig, true) }

            login()

            val response = client.get("/api/hendelse/42")
            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(
                response.bodyAsText() == "{}"
            )
        }

    }

    @Test
    fun `unauthorized response when session expires`() {
        mockAuth.enqueueCallback(
            DefaultOAuth2TokenCallback(
                expiry = 1,
                claims = mapOf("NAVident" to "H12345")
            )
        )
        testApplication {
            val client = createClient {
                install(HttpCookies)
            }
            application { spanner(spleis, azureADConfig, true) }

            val response = client.get("/api/person/")

            assertEquals(HttpStatusCode.Unauthorized, response.status)
            val feil = objectMapper.readValue<FeilRespons>(response.bodyAsText())
            assertTrue(!feil.description.isNullOrEmpty())
        }
    }

    @Test
    fun `valid json error message response`() {
        testApplication {
            val client = createClient {
                install(HttpCookies)
            }
            application { spanner(spleis, azureADConfig, true) }

            login()

            val response = client.get("/api/person/")

            assertEquals(HttpStatusCode.InternalServerError, response.status)
            val feil = objectMapper.readValue<FeilRespons>(response.bodyAsText())
            assertTrue(!feil.description.isNullOrEmpty())
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