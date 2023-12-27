package no.nav.spanner

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.natpryce.konfig.Configuration
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

class AzureADConfig(
    private val jwkProvider: JwkProvider,
    private val issuer: String,
    val tokenEndpoint: String,
    val clientId: String,
    val clientSecret: String
) {
    fun konfigurerJwtAuth(config: AuthenticationConfig) {
        config.jwt {
            verifier(jwkProvider, issuer) {
                withAudience(clientId)
                withClaimPresence("NAVident")
                withClaimPresence("preferred_username")
                withClaimPresence("name")
            }
            validate { credentials ->
                JWTPrincipal(credentials.payload)
            }
        }
    }

    companion object {
        fun fromEnv(config: Configuration) = AzureADConfig(
            jwkProvider = JwkProviderBuilder(URI(config[Key("AZURE_OPENID_CONFIG_JWKS_URI", stringType)]).toURL()).build(),
            issuer = config[Key("AZURE_OPENID_CONFIG_ISSUER", stringType)],
            tokenEndpoint = config[Key("AZURE_OPENID_CONFIG_TOKEN_ENDPOINT", stringType)],
            clientId = config[Key("AZURE_APP_CLIENT_ID", stringType)],
            clientSecret = config[Key("AZURE_APP_CLIENT_SECRET", stringType)],
        )
    }
}
