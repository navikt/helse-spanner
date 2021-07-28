import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.ktor.auth.*
import io.ktor.http.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

internal abstract class IAzureAdConfig(
    private val clientId: String,
    private val clientSecret: String
) {
    abstract val authorizeUrl: String
    abstract val accessTokenUrl: String

    fun oauth2ServerSettings(azureAdClient: AzureAdClient) = OAuthServerSettings.OAuth2ServerSettings(
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
}

internal class AzureAdConfig(
    clientId: String,
    clientSecret: String,
    configurationUrl: String
): IAzureAdConfig(clientId, clientSecret) {
    override val authorizeUrl: String
    override val accessTokenUrl: String

    init {
        configurationUrl.getJson().also {
            this.authorizeUrl = it["authorization_endpoint"].textValue()
            this.accessTokenUrl = it["token_endpoint"].textValue()
        }
    }

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