package no.nav.spanner

import com.auth0.jwk.JwkProviderBuilder
import com.fasterxml.jackson.databind.JsonNode
import com.github.navikt.tbd_libs.result_object.ok
import com.github.navikt.tbd_libs.speed.IdentResponse
import com.github.navikt.tbd_libs.speed.SpeedClient
import com.github.navikt.tbd_libs.spurtedu.SkjulRequest
import com.github.navikt.tbd_libs.spurtedu.SkjulResponse
import com.github.navikt.tbd_libs.spurtedu.SpurteDuClient
import com.github.navikt.tbd_libs.spurtedu.VisTekstResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.serialization.jackson.JacksonConverter
import io.ktor.server.application.Application
import io.ktor.server.engine.connector
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
import java.net.ServerSocket
import java.util.UUID

class E2ETest {

    private val azureADConfig = AzureADConfig(
        jwkProvider = JwkProviderBuilder(mockAuth.wellKnownUrl("default").toUrl()).build(),
        issuer = "default",
        clientId = "whatever"
    )
    private val speedClient = mockk<SpeedClient> {
        every { hentFødselsnummerOgAktørId(any(), any()) } returns IdentResponse(
            fødselsnummer = "fnr",
            aktørId = "aktør",
            npid = null,
            kilde = IdentResponse.KildeResponse.PDL
        ).ok()
    }
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
        e2e { client ->
            val uuid = client.post("/api/uuid/") {
                header(IdType.FNR.header, "42")
            }
                .body<JsonNode>()
                .path("id")
                .asText()

            client.get("/api/person/") {
                header(IdType.MASKERT_ID.header, uuid)
            }.apply {
                assertTrue(status.isSuccess())
                val bodyAsText = bodyAsText()
                assertTrue(bodyAsText.contains("\"aktørId\": \"42\"")) {
                    "response was: $bodyAsText"
                }
            }
        }
    }

    @Test
    fun `respond with message json on hendelse endpoint`() = e2e { client ->
        mockAuth.enqueueCallback(
            DefaultOAuth2TokenCallback(
                claims = mapOf("NAVident" to "H12345")
            )
        )

        client.get("/api/hendelse/42").apply {
            assertTrue(status.isSuccess())
            val bodyAsText = bodyAsText()
            assertEquals("{}", bodyAsText) {
                "response was: $bodyAsText"
            }
        }
    }

    @Test
    fun `valid json error message response`() = e2e { client ->
        client.get("/api/person/").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
            val feil = body<FeilRespons>()
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

    private fun e2e(testblokk: suspend (HttpClient) -> Unit) {
        e2e(
            applicationModule = {
                spanner(lokaleKjenninger, speedClient, spurteDuClient, påkrevdSpurteduTilgang, azureADConfig, true)
            },
            testblokk = testblokk
        )
    }
}


fun e2e(applicationModule: Application.() -> Unit, testblokk: suspend (HttpClient) -> Unit) {
    testApplication {
        val randomPort = ServerSocket(0).localPort
        engine {
            connector {
                host = "localhost"
                port = randomPort
            }
        }
        application {
            applicationModule()
        }

        val testClient = createClient {
            defaultRequest {
                port = randomPort
            }
            install(ContentNegotiation) {
                register(ContentType.Application.Json, JacksonConverter(objectMapper))
            }
        }

        testblokk(testClient)
    }
}