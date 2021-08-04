import com.auth0.jwt.JWT
import com.nimbusds.jose.util.Base64
import io.ktor.application.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.IOException
import java.net.ServerSocket

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OAuth2Test {
    private val mockOAuth2Server = MockOAuth2Server()
    private val clientId = "some_id"
    private val clientSecret = "some_secret"
    private val encodedClientSecret = Base64.encode(clientSecret).toString()
    private val spleisClientId = "spleis_client_id"
    private val issuerId = "some_issuer"
    private val name = "tullebukk"
    private val port = randomPort()
    private val host = Localhost(port)

    @BeforeAll
    fun beforeAll() {
        mockOAuth2Server.start()
    }

    @AfterAll
    fun afterAll() {
        mockOAuth2Server.shutdown()
    }

    @Test
    fun `Kan autentisere med oidc og forblir innlogget`() {
        val config = Config(clientId, encodedClientSecret, spleisClientId, issuerId, mockOAuth2Server)
        val client = AzureAdClient(config)

        kjørTestKall(host, client) {
            runBlocking {
                val loginResponse = client.get<HttpResponse>("$host/respond-ok")
                val receivedLogin = loginResponse.receive<String>()

                assertEquals(loginResponse.status, HttpStatusCode.OK)
                assertEquals("Logget inn som $name", receivedLogin)

                val alreadyLoggedInResponse = client.get<HttpResponse>("$host/respond-ok")
                val receivedAlreadyLoggedIn = alreadyLoggedInResponse.receive<String>()

                assertEquals(HttpStatusCode.OK, alreadyLoggedInResponse.status)
                assertEquals("Er allerede logget inn som $name", receivedAlreadyLoggedIn)
            }
        }
    }

    @Test
    fun `Kan logge ut, og må logge inn igjen etter det`() {
        val config = Config(clientId, encodedClientSecret, spleisClientId, issuerId, mockOAuth2Server)
        val client = AzureAdClient(config)

        enqueueCallback() // Legger en ny callback i køen fordi vi vil logge inn to ganger

        kjørTestKall(host, client) {
            runBlocking {
                val loginResponse = client.get<HttpResponse>("$host/respond-ok")
                val receivedLogin = loginResponse.receive<String>()

                assertEquals(loginResponse.status, HttpStatusCode.OK)
                assertEquals("Logget inn som $name", receivedLogin)

                val alreadyLoggedInResponse = client.get<HttpResponse>("$host/respond-ok")
                val receivedAlreadyLoggedIn = alreadyLoggedInResponse.receive<String>()

                assertEquals(HttpStatusCode.OK, alreadyLoggedInResponse.status)
                assertEquals("Er allerede logget inn som $name", receivedAlreadyLoggedIn)

                val logoutResponse = client.get<HttpResponse>("$host/logout")
                val receiveLogout = logoutResponse.receive<String>()

                assertEquals(HttpStatusCode.OK, logoutResponse.status)
                assertEquals("Bruker har logget ut", receiveLogout)

                val loginAgainResponse = client.get<HttpResponse>("$host/respond-ok")
                val receivedLoginAgain = loginAgainResponse.receive<String>()

                assertEquals(loginAgainResponse.status, HttpStatusCode.OK)
                assertEquals("Logget inn som $name", receivedLoginAgain)
            }
        }
    }

    @Test
    fun `Kan hente onBehalfOf-token`() {
        val config = Config(clientId, encodedClientSecret, spleisClientId, issuerId, mockOAuth2Server)
        val client = AzureAdClient(config)

        runBlocking {
            val initialToken = mockOAuth2Server.issueToken(subject = clientId)
            val session = SpannerSession(AccessToken.createLocalAccessToken(initialToken.serialize()))
            val onBehalfOfToken = client.hentOnBehalfOfToken(session)

            val decodedOnBehalfOf = JWT.decode(onBehalfOfToken)

            assertEquals(clientId, decodedOnBehalfOf.subject)
            assertEquals(listOf(spleisClientId), decodedOnBehalfOf.audience)
        }
    }

    private fun enqueueCallback() {
        mockOAuth2Server.enqueueCallback(
            DefaultOAuth2TokenCallback(
                issuerId = issuerId,
                subject = "some_uuid",
                claims = mapOf("name" to name)
            )
        )
    }

    private fun kjørTestKall(host: Host, client: IAzureAdClient, blokk: () -> Unit) {
        enqueueCallback()

        withEmbeddedServer(
            port = host.port(),
            moduleFunction = {
                module(client, host) {
                    authApi(client, false)
                }
            }) {
            blokk()
        }
    }

    private class Config(
        clientId: String,
        clientSecret: String,
        spleisClientId: String,
        issuerId: String,
        mockOAuth2Server: MockOAuth2Server
    ) : IAzureAdConfig(clientId, clientSecret, spleisClientId) {
        override val authorizeUrl = mockOAuth2Server.authorizationEndpointUrl(issuerId).toString()
        override val accessTokenUrl = mockOAuth2Server.tokenEndpointUrl(issuerId).toString()
        override val jwksUri = mockOAuth2Server.jwksUrl(issuerId).toString()
    }

    private inline fun <reified R> AzureAdClient.get(url: String): R = runBlocking { httpClient.get(url) }

    private fun <R> withEmbeddedServer(
        moduleFunction: Application.() -> Unit,
        port: Int,
        test: ApplicationEngine.() -> R
    ): R {
        val engine = embeddedServer(Netty, port = port) {
            moduleFunction(this)
        }
        engine.start()
        try {
            return engine.test()
        } finally {
            engine.stop(0L, 0L)
        }
    }

    private fun randomPort() = try {
        ServerSocket(0).use { serverSocket -> serverSocket.localPort }
    } catch (e: IOException) {
        fail("Port is not available")
    }
}