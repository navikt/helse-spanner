package no.nav.spanner

import io.github.cdimascio.dotenv.Dotenv

class AzureADConfig(
    val discoveryUrl: String,
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

    companion object {
        fun fromEnv() = with(Dotenv.configure().ignoreIfMissing().load()) {
            AzureADConfig(
                discoveryUrl = get("AZURE_APP_WELL_KNOWN_URL"),
                clientId = get("AZURE_APP_CLIENT_ID"),
                clientSecret = get("AZURE_APP_CLIENT_SECRET"),
                spleisClientId = get("SPLEIS_CLIENT_ID"),
                autorhizationUrl = get("AUTHORIZATION_URL", null),
            )
        }
    }
}
