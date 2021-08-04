import com.auth0.jwt.JWT
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.JsonNode
import io.ktor.auth.*
import io.ktor.util.*
import java.time.LocalDateTime

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
internal class AccessToken private constructor(
    private val accessToken: String,
    private val refreshToken: String?,
    private val expiresAt: LocalDateTime,
    private val idToken: String,
    private val bruker: String
) {
    fun bruker() = bruker
    fun harRefreshToken() = refreshToken != null

    fun verifyOidcResponse(client: IAzureAdClient) = client.verifyIdToken(idToken)

    fun createRefreshRequestBody(list: List<Pair<String, String>>) = refreshToken?.let {
        list.toMutableList().apply { add("refresh_token" to it) }.toList()
    } ?: list

    fun hasExpired() = LocalDateTime.now().plusSeconds(30) >= expiresAt

    override fun toString() = accessToken

    companion object {
        fun from(oauth2Principal: OAuthAccessTokenResponse.OAuth2): AccessToken {
            val accessTokenString = oauth2Principal.accessToken
            val expiresAt = JWT.decode(accessTokenString).expiresAt.toLocalDateTime()
            val idTokenString = oauth2Principal.extraParameters.getOrFail("id_token")
            val bruker = JWT.decode(idTokenString).claims["name"]?.asString() ?: "unknown user"
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
                bruker = JWT.decode(idToken).claims["name"]?.asString() ?: "unknown user"
            )
        }

        fun createLocalAccessToken(accessToken: String = "access_token") = AccessToken(
            accessToken = accessToken,
            refreshToken = "refresh_token",
            expiresAt = LocalDateTime.now().plusHours(1),
            idToken = "id_token",
            bruker = "spanner_dev"
        )
    }
}