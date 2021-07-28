import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.http.*
import io.ktor.util.*
import org.apache.http.impl.conn.SystemDefaultRoutePlanner
import java.net.ProxySelector
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal interface IAzureAdClient {
    companion object {
        fun client(config: IAzureAdConfig, isLocal: Boolean) =
            if (!isLocal) AzureAdClient(config) else LocalAzureAdClient
    }

    fun verifyOidcResponse(principal: OAuthAccessTokenResponse.OAuth2): Pair<String, Jws<Claims>>
    fun configuration(): OAuthServerSettings.OAuth2ServerSettings
    val httpClient: HttpClient
}

internal object LocalAzureAdClient : IAzureAdClient {
    override fun verifyOidcResponse(principal: OAuthAccessTokenResponse.OAuth2): Pair<String, Jws<Claims>> {
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
    }

    override fun verifyOidcResponse(principal: OAuthAccessTokenResponse.OAuth2): Pair<String, Jws<Claims>> {
        val expiresIn = principal.expiresIn
        require(
            LocalDateTime.ofInstant(Instant.ofEpochMilli(expiresIn), ZoneId.systemDefault()) > LocalDateTime.now()
        ) { "AccessToken er utgått" }

        val idTokenString = principal.extraParameters.getOrFail("id_token")
        val claims = config.verifySignature(idTokenString)
        val idTokenExpiration = claims.body.expiration

        require(
            idTokenExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() > LocalDateTime.now()
        ) { "idToken er utgått" }

        return idTokenString to claims
    }

    override fun configuration() = config.oauth2ServerSettings(this)
}
