import com.auth0.jwt.JWT
import com.fasterxml.jackson.databind.JsonNode
import io.ktor.auth.*
import io.ktor.util.*
import java.time.LocalDateTime

internal class AccessToken private constructor(
    private val accessToken: String,
    private val refreshToken: String?,
    private val expiresAt: LocalDateTime,
    private val idToken: String, // IdToken
    private val bruker: String
) {
    fun bruker() = bruker
    fun harRefreshToken() = refreshToken != null

    fun verifyOidcResponse(client: IAzureAdClient) = client.verifyIdToken(idToken)

    fun createRefreshRequestBody(map: Map<String, String>) = refreshToken?.let {
        map.toMutableMap().apply { put("refresh_token", it) }.toMap()
    } ?: map

    fun isExpired() = LocalDateTime.now().plusSeconds(30) <= expiresAt

    override fun toString() = accessToken

    companion object {
        fun from(oauth2Principal: OAuthAccessTokenResponse.OAuth2): AccessToken {
            val accessTokenString = oauth2Principal.accessToken
            val expiresAt = JWT.decode(accessTokenString).expiresAt.toLocalDateTime()
            val idTokenString = oauth2Principal.extraParameters.getOrFail("id_token")
            val bruker = JWT.decode(idTokenString).claims["name"].toString()
            return AccessToken(
                accessToken = accessTokenString,
                refreshToken = oauth2Principal.refreshToken,
                expiresAt = expiresAt,
                idToken = oauth2Principal.extraParameters.getOrFail("id_token"),
                bruker = bruker
            )
        }

        fun from(json: JsonNode): AccessToken {
            val accessToken = json["access_token"].toString()
            val idToken = json["id_token"].toString()
            return AccessToken(
                accessToken = accessToken,
                refreshToken = json["refresh_token"].toString(),
                expiresAt = JWT.decode(accessToken).expiresAt.toLocalDateTime(),
                idToken = idToken,
                bruker = JWT.decode(idToken).claims["name"].toString()
            )
        }
    }
}