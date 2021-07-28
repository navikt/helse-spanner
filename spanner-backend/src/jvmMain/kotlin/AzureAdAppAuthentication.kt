import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.client.*
import io.ktor.http.*

internal val AZURE_OAUTH = "azure_oauth"

internal fun Application.azureAdAppAuthentication(azureAdClient: IAzureAdClient, host: Host) {
    install(Authentication) {
        oauth(AZURE_OAUTH) {
            urlProvider = { "$host/oauth2/callback" }
            providerLookup = {
                azureAdClient.configuration()
            }
            client = azureAdClient.httpClient
        }
    }
}
