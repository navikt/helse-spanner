package no.nav.spanner

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import com.natpryce.konfig.Configuration
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.net.URI

class AzureADConfig(
    private val jwkProvider: JwkProvider,
    private val issuer: String,
    private val clientId: String
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
            clientId = config[Key("AZURE_APP_CLIENT_ID", stringType)],
        )
    }
}
