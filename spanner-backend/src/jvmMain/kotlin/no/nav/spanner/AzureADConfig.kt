package no.nav.spanner

import io.github.cdimascio.dotenv.dotenv
import io.ktor.config.*

class AzureADConfig(
    val issuer: String,
    val discoveryUrl: String,
    val acceptedAudience: String,
    val clientId: String,
    val clientSecret: String,
    val spleisClientId: String,
    autorhizationUrl: String? = null
) {
    private val discovered = discoveryUrl.getJson()
    val tokenEndpoint = discovered["token_endpoint"]?.textValue()
        ?: throw RuntimeException("Unable to discover token endpoint")
    val authorizationEndpoint = autorhizationUrl ?: discovered["authorization_endpoint"]?.textValue()
        ?: throw RuntimeException("Unable to discover authorization endpoint")

    fun ktorStyle() = MapApplicationConfig().apply {
        put("no.nav.security.jwt.issuers.size", "1")
        put("no.nav.security.jwt.issuers.0.issuer_name", issuer)
        put("no.nav.security.jwt.issuers.0.discoveryurl", discoveryUrl)
        put("no.nav.security.jwt.issuers.0.accepted_audience", acceptedAudience)
        put("no.nav.security.jwt.issuers.0.cookie_name", "aad-idtoken")
    }

    companion object {
        fun fromEnv() = with(dotenv()) {
            AzureADConfig(
                issuer = get("ISSUER"),
                discoveryUrl = get("DISCOVERY_URL"),
                acceptedAudience = get("ACCEPTED_AUDIENCE"),
                clientId = get("AZURE_APP_CLIENT_ID"),
                clientSecret = get("AZURE_APP_CLIENT_SECRET"),
                spleisClientId = get("SPLEIS_CLIENT_ID"),
                autorhizationUrl = get("AUTHORIZATION_URL", null),
            )
        }
    }

}