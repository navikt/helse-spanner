package no.nav.spanner

import com.auth0.jwk.JwkProviderBuilder
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.navikt.tbd_libs.speed.SpeedClient
import com.github.navikt.tbd_libs.spurtedu.SkjulRequest
import com.github.navikt.tbd_libs.spurtedu.SkjulResponse
import com.github.navikt.tbd_libs.spurtedu.SpurteDuClient
import com.github.navikt.tbd_libs.spurtedu.VisTekstResponse
import io.ktor.http.*
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import no.nav.spanner.no.nav.spanner.LokaleKjenninger
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.util.UUID

class E2ETest {

    private val azureADConfig = AzureADConfig(
        jwkProvider = JwkProviderBuilder(mockAuth.wellKnownUrl("default").toUrl()).build(),
        issuer = "default",
        clientId = "whatever"
    )
    private val speedClient = mockk<SpeedClient>()
    private val påkrevdSpurteduTilgang = "skikkelig_tøysete_claim"
    private val spurteDuClient = mockk<SpurteDuClient> {
        val skjulRequests = mutableListOf<SkjulRequest>()
        every { skjul(capture(skjulRequests)) } answers {
            val forrigeRequest = skjulRequests.last()
            check(forrigeRequest.påkrevdTilgang == påkrevdSpurteduTilgang) {
                "påkrevd tilgang er ikke $påkrevdSpurteduTilgang"
            }
            val maskertId = when (forrigeRequest) {
                is SkjulRequest.SkjulTekstRequest -> {
                    val skjultVerdi = objectMapper.readTree(forrigeRequest.tekst).path("ident").asText()
                    when (skjultVerdi) {
                        "42",
                        "12020052345" -> "113eb3df-102d-4a07-9270-2caa648c62f4"
                        "2392363031327" -> "48bfef57-3080-4f19-98eb-5cc72d9d16c5"
                        else -> throw NotFoundException("no person with identifier: $skjultVerdi")
                    }
                }
                is SkjulRequest.SkjulUrlRequest -> error("Støtter ikke dette")
            }
            SkjulResponse(
                id = UUID.fromString(maskertId),
                url = "http://foo/",
                path = "/foo"
            )
        }

        val visRequests = mutableListOf<UUID>()
        every { vis(capture(visRequests), any(), any()) } answers {
            val maskertId = visRequests.last()
            val ident = when (maskertId.toString()) {
                "113eb3df-102d-4a07-9270-2caa648c62f4" -> "12020052345"
                "48bfef57-3080-4f19-98eb-5cc72d9d16c5" -> "2392363031327"
                else -> error("Kjenner ikke til $maskertId")
            }
            VisTekstResponse(text = """{
                "ident": "$ident",
                "identtype": "FNR"
            }""")
        }
    }

    private val lokaleKjenninger = LokaleKjenninger

    @Test
    fun `respond with person json on person endpoint`() {
        mockAuth.enqueueCallback(
            DefaultOAuth2TokenCallback(
                claims = mapOf("NAVident" to "H12345")
            )
        )
        withTestApplication({
            spanner(lokaleKjenninger, speedClient, spurteDuClient, påkrevdSpurteduTilgang, azureADConfig, true)
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
            spanner(lokaleKjenninger, speedClient, spurteDuClient, påkrevdSpurteduTilgang, azureADConfig, true)
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
            spanner(lokaleKjenninger, speedClient, spurteDuClient, påkrevdSpurteduTilgang, azureADConfig, true)
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
            spanner(lokaleKjenninger, speedClient, spurteDuClient, påkrevdSpurteduTilgang, azureADConfig, true)
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
