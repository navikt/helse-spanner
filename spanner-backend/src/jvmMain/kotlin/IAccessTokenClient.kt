import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant
import kotlin.collections.set

internal interface IAccessTokenClient {
    suspend fun hentAccessToken(scope: String): String

    companion object {
        internal fun accessTokenClient(isLocal: Boolean, azureAdClient: HttpClient, env: Map<String, String>) =
            if (isLocal) LocalAccessTokenClient
            else AccessTokenClient(
                aadAccessTokenUrl = env.getValue("AZURE_OPENID_CONFIG_TOKEN_ENDPOINT"),
                clientId = env.getValue("AZURE_APP_CLIENT_ID"),
                clientSecret = env.getValue("AZURE_APP_CLIENT_SECRET"),
                httpClient = azureAdClient
            )
    }
}

internal object LocalAccessTokenClient : IAccessTokenClient {
    override suspend fun hentAccessToken(scope: String): String {
        return "access_token"
    }
}

internal class AccessTokenClient(
    private val aadAccessTokenUrl: String,
    private val clientId: String,
    private val clientSecret: String,
    private val httpClient: HttpClient
): IAccessTokenClient {

    private val log = LoggerFactory.getLogger(IAccessTokenClient::class.java)
    private val mutex = Mutex()

    @Volatile
    private var tokenMap = HashMap<String, AadAccessToken>()

    override suspend fun hentAccessToken(scope: String): String {
        val omToMinutter = Instant.now().plusSeconds(120L)
        return mutex.withLock {
            (tokenMap[scope]
                ?.takeUnless { it.expiry.isBefore(omToMinutter) }
                ?: run {
                    log.info("Henter nytt token fra Azure AD")
                    val response: AadAccessToken = try {
                        httpClient.post(aadAccessTokenUrl) {
                            accept(ContentType.Application.Json)
                            method = HttpMethod.Post
                            body = FormDataContent(Parameters.build {
                                append("client_id", clientId)
                                append("scope", scope)
                                append("grant_type", "client_credentials")
                                append("client_secret", clientSecret)
                            })
                        }
                    } catch (e: Exception) {
                        throw RuntimeException("Klarte ikke hente nytt token fra Azure AD", e)
                    }
                    tokenMap[scope] = response
                    log.debug("Har hentet accesstoken")
                    return@run response
                }).access_token
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class AadAccessToken(
    val access_token: String,
    val expires_in: Duration
) {
    internal val expiry = Instant.now().plus(expires_in)
}
