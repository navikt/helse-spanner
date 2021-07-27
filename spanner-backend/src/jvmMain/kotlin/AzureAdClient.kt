import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.http.*
import io.ktor.util.*
import org.apache.http.impl.conn.SystemDefaultRoutePlanner
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.ProxySelector
import java.net.URL
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal interface IAzureAdClient {
    companion object {
        fun client(env: Map<String, String>, isLocal: Boolean) =
            if (!isLocal) AzureAdClient(env) else LocalAzureAdClient
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

internal class AzureAdClient(env: Map<String, String>) : IAzureAdClient {
    private val azureConfig = AzureAdAppConfig(
        clientId = env.getOrDefault("AZURE_APP_CLIENT_ID", "unknown"),
        clientSecret = env.getOrDefault("AZURE_APP_CLIENT_SECRET", "unknown"),
        configurationUrl = env.getOrDefault("AZURE_APP_WELL_KNOWN_URL", "unknown")
    )

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
        val claims = azureConfig.verifySignature(idTokenString)
        val idTokenExpiration = claims.body.expiration

        require(
            idTokenExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() > LocalDateTime.now()
        ) { "idToken er utgått" }

        return idTokenString to claims
    }

    override fun configuration() = azureConfig.oauth2ServerSettings()

    private inner class AzureAdAppConfig(
        private val clientId: String,
        private val clientSecret: String,
        configurationUrl: String
    ) {
        private val authorizeUrl: String
        private val accessTokenUrl: String

        init {
            configurationUrl.getJson().also {
                this.authorizeUrl = it["authorization_endpoint"].textValue()
                this.accessTokenUrl = it["token_endpoint"].textValue()
            }
        }

        fun oauth2ServerSettings() = OAuthServerSettings.OAuth2ServerSettings(
            name = "azure",
            authorizeUrl = authorizeUrl,
            accessTokenUrl = accessTokenUrl,
            clientId = clientId,
            clientSecret = clientSecret,
            defaultScopes = listOf("profile", "offline_access", "openid", "email"),
            requestMethod = HttpMethod.Post
        )

        fun verifySignature(jwtToken: String): Jws<Claims> =
            Jwts.parserBuilder().setSigningKey(clientSecret).build().parseClaimsJws(jwtToken)

        private fun String.getJson(): JsonNode {
            val (responseCode, responseBody) = this.fetchUrl()
            if (responseCode >= 300 || responseBody == null) throw RuntimeException("got status $responseCode from ${this}.")
            return jacksonObjectMapper().readTree(responseBody)
        }

        private fun String.fetchUrl() = with(URL(this).openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            val stream: InputStream? = if (responseCode < 300) this.inputStream else this.errorStream
            responseCode to stream?.bufferedReader()?.readText()
        }
    }
}
