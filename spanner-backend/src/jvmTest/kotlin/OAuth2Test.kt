import io.ktor.application.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import org.junit.jupiter.api.*
import java.io.IOException
import java.net.ServerSocket

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OAuth2Test {

    private val mockOAuth2Server = MockOAuth2Server()
    private val clientId = "some_id"
    private val clientSecret = "some_secret"
    private val issuerId = "some_issuer"

    @BeforeAll
    fun beforeAll() {
        mockOAuth2Server.start()
    }

    @AfterAll
    fun afterAll() {
        mockOAuth2Server.shutdown()
    }

    @Test
    fun `can authenticate with oidc`() {
        val port = randomPort()
        val host = Localhost(port)
        val config = Config(clientId, clientSecret, issuerId, mockOAuth2Server)
        val client = AzureAdClient(config)
        mockOAuth2Server.enqueueCallback(DefaultOAuth2TokenCallback(issuerId = issuerId, subject = "some_uuid"))

        withEmbeddedServer(
            port = host.port(),
            moduleFunction = {
                module(client, host) {
                    authApi(client, false)
                }
            }) {
                val t = client.get<HttpResponse>("$host/login")
            }
    }

    private class Config(
        clientId: String,
        clientSecret: String,
        issuerId: String,
        mockOAuth2Server: MockOAuth2Server
    ) : IAzureAdConfig(clientId, clientSecret) {
        override val authorizeUrl = mockOAuth2Server.authorizationEndpointUrl(issuerId).toString()
        override val accessTokenUrl = mockOAuth2Server.tokenEndpointUrl(issuerId).toString()
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