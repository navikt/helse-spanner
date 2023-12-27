package no.nav.spanner

import com.auth0.jwk.JwkProviderBuilder
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.*
import io.ktor.server.testing.*
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import no.nav.spanner.no.nav.spanner.LokaleKjenninger
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class E2ETest {

    private val azureADConfig = AzureADConfig(
        jwkProvider = JwkProviderBuilder(mockAuth.wellKnownUrl("default").toUrl()).build(),
        issuer = "default",
        tokenEndpoint = mockAuth.tokenEndpointUrl("issuer").toString(),
        clientId = "whatever",
        clientSecret = "supersecret"
    )

    private val lokaleKjenninger = LokaleKjenninger

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
                val uuid = objectMapper.readTree(handleRequest(HttpMethod.Post, "/api/uuid/") {
                    addHeader(IdType.FNR.header, "42")
                }.response.content).path("id").asText()
                handleRequest(HttpMethod.Get, "/api/person/") {
                    this.addHeader(IdType.MASKERT_ID.header, uuid)
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
                val uuid = objectMapper.readTree(handleRequest(HttpMethod.Post, "/api/uuid/") {
                    addHeader(IdType.FNR.header, "42")
                }.response.content).path("id").asText()
                handleRequest(HttpMethod.Get, "/api/person/") {
                    this.addHeader(IdType.MASKERT_ID.header, uuid)
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
                handleRequest(HttpMethod.Get, "/api/person/") { /* ingen ident i header */ }
                    .apply {
                        assertEquals(HttpStatusCode.BadRequest, response.status())
                        println(response.content!!)
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
