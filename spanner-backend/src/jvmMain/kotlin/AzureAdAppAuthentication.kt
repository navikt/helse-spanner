import io.ktor.application.*
import io.ktor.auth.*

internal val AZURE_OAUTH = "azure_oauth"

internal fun Application.azureAdAppAuthentication(azureAdClient: IAzureAdClient, host: Host) {
    install(Authentication) {
        oauth(AZURE_OAUTH) {
            urlProvider = { "{${host.protocol()}://{${host.hostname()}/oauth2/callback" }
            providerLookup = {
                azureAdClient.configuration()
            }
            client = azureAdClient.httpClient
        }
    }
}
