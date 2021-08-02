import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.apache.http.impl.conn.SystemDefaultRoutePlanner
import java.net.ProxySelector
import java.time.LocalDateTime

internal interface IAzureAdClient {
    companion object {
        fun client(config: IAzureAdConfig, isLocal: Boolean) =
            if (!isLocal) AzureAdClient(config) else LocalAzureAdClient
    }

    fun verifyIdToken(idToken: String): Pair<String, Jws<Claims>>
    fun configuration(): OAuthServerSettings.OAuth2ServerSettings
    suspend fun refreshAccessToken(session: SpannerSession): Boolean
    val httpClient: HttpClient
}

internal object LocalAzureAdClient : IAzureAdClient {
    override fun verifyIdToken(idToken: String): Pair<String, Jws<Claims>> {
        throw NotImplementedError("Er ikke i bruk lokalt")
    }

    override fun configuration() = OAuthServerSettings.OAuth2ServerSettings(
        name = "unknown",
        authorizeUrl = "unknown",
        accessTokenUrl = "unknown",
        clientId = "unknown",
        clientSecret = "unknown",
        defaultScopes = emptyList(),
        requestMethod = HttpMethod.Post
    )

    override suspend fun refreshAccessToken(session: SpannerSession): Boolean {
        TODO("Not yet implemented")
    }

    override val httpClient = HttpClient(Apache)
}

internal class AzureAdClient(private val config: IAzureAdConfig) : IAzureAdClient {
    override val httpClient = HttpClient(Apache) {
        engine {
            customizeClient {
                setRoutePlanner(SystemDefaultRoutePlanner(ProxySelector.getDefault()))
            }
        }
        install(JsonFeature) {
            serializer = JacksonSerializer {
                registerModule(JavaTimeModule())
            }
        }
        install(HttpCookies) { storage = AcceptAllCookiesStorage() }
    }

    override fun verifyIdToken(idToken: String): Pair<String, Jws<Claims>> {
        val claims = config.verifySignature(idToken)
        val idTokenExpiration = claims.body.expiration

        require(
            idTokenExpiration.toLocalDateTime() > LocalDateTime.now()
        ) { "idToken er utgått" }

        return idToken to claims
    }

    override fun configuration() = config.oauth2ServerSettings(this)

    override suspend fun refreshAccessToken(session: SpannerSession): Boolean {
        if (!session.accessToken.harRefreshToken()) return false

        val responseBody = listOf("grant_type" to "refresh_token")
            .let {
                val list = config.createRefreshRequestBody(it)
                session.accessToken.createRefreshRequestBody(list)
            }.formUrlEncode()

        val response = httpClient.request<HttpResponse>(config.accessTokenUrl) {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.FormUrlEncoded)
            method = HttpMethod.Post
            body = responseBody
        }

        val accessToken = AccessToken.from(response.call.receive<JsonNode>())

        try {
            accessToken.verifyOidcResponse(this)
        } catch (e: Exception) {
            return false
        }

        session.update(accessToken)

        return true
    }
}

