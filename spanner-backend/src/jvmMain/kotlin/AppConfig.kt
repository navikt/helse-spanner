import io.jsonwebtoken.*
import io.ktor.auth.*
import io.ktor.http.*
import org.apache.commons.codec.binary.Base64.decodeBase64
import java.math.BigInteger
import java.security.Key
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec

internal abstract class IAzureAdConfig(
    private val clientId: String,
    private val clientSecret: String,
    private val spleisClientId: String
) {
    abstract val authorizeUrl: String
    abstract val accessTokenUrl: String
    abstract val jwksUri: String
    private val defaultScopes = listOf("profile", "offline_access", "openid", "email")

    fun oauth2ServerSettings(azureAdClient: AzureAdClient) = OAuthServerSettings.OAuth2ServerSettings(
        name = "azure",
        authorizeUrl = authorizeUrl,
        accessTokenUrl = accessTokenUrl,
        clientId = clientId,
        clientSecret = clientSecret,
        defaultScopes = defaultScopes,
        requestMethod = HttpMethod.Post
    )

    fun verifySignature(jwtToken: String): Jws<Claims> =
        Jwts.parserBuilder().setSigningKeyResolver(AzureAdSigningKeyResolver(jwksUri)).build().parseClaimsJws(jwtToken)

    fun createRefreshRequestBody(list: List<Pair<String, String>>) =
        createTokenRequestBody(list, defaultScopes.joinToString(" "))

    fun createOnBehalfOfRequestBody(list: List<Pair<String, String>>) = createTokenRequestBody(list, spleisClientId)

    private fun createTokenRequestBody(list: List<Pair<String, String>>, scope: String) =
        list.toMutableList().apply {
            add("client_id" to clientId)
            add("client_secret" to clientSecret)
            add("scope" to scope)
        }.toList()


    companion object {
        fun config(
            clientId: String,
            clientSecret: String,
            spleisClientId: String,
            configurationUrl: String,
            isLocal: Boolean
        ) =
            if (!isLocal) AzureAdConfig(clientId, clientSecret, spleisClientId, configurationUrl)
            else LocalAzureAdConfig(clientId, clientSecret, spleisClientId)
    }
}

internal class LocalAzureAdConfig(
    clientId: String,
    clientSecret: String,
    spleisClientId: String
) : IAzureAdConfig(clientId, clientSecret, spleisClientId) {
    override val authorizeUrl: String = "unknown"
    override val accessTokenUrl: String = "unknown"
    override val jwksUri: String = "unknown"
}

internal class AzureAdConfig(
    clientId: String,
    clientSecret: String,
    spleisClientId: String,
    configurationUrl: String
) : IAzureAdConfig(clientId, clientSecret, spleisClientId) {
    override val authorizeUrl: String
    override val accessTokenUrl: String
    override val jwksUri: String

    init {
        configurationUrl.getJson().also {
            this.authorizeUrl = it["authorization_endpoint"].textValue()
            this.accessTokenUrl = it["token_endpoint"].textValue()
            this.jwksUri = it["jwks_uri"].textValue()
        }
    }
}

internal class AzureAdSigningKeyResolver(private val jwksUri: String) : SigningKeyResolverAdapter() {
    override fun resolveSigningKey(jwsHeader: JwsHeader<*>, claims: Claims): Key {
        val kid = jwsHeader.keyId
        val json = jwksUri.getJson()
        val keys = json["keys"]

        val modulus = keys.find { it["kid"].asText() == kid }!!["n"].asText()
        val exponent = keys.find { it["kid"].asText() == kid }!!["e"].asText()
        val encodedModulus = decodeBase64(modulus)
        val encodedExponent = decodeBase64(exponent)


        val keySpec = RSAPublicKeySpec(BigInteger(1, encodedModulus), BigInteger(1, encodedExponent))
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }
}