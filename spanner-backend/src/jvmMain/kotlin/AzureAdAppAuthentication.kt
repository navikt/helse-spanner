import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

internal const val API_BRUKER = "api_bruker"
internal const val API_SERVICE = "api_service"

internal fun Application.azureAdAppAuthentication(config: IAzureAdAppConfig) {
    install(Authentication) {
        jwt(API_SERVICE) {
            config.configureVerification(this)
        }
    }
}
