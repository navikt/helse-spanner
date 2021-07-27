import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.auth.jwt.*
import io.ktor.server.engine.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

internal class AppConfig(env: Map<String, String> = System.getenv(), isLocal: Boolean) {
    internal val ktorConfig = KtorConfig(
        httpPort = env["HTTP_PORT"]?.toInt() ?: 9000
    )

    internal val azureConfig = if (!isLocal)
        AzureAdAppConfig(
            clientId = env.getValue("AZURE_APP_CLIENT_ID"),
            configurationUrl = env.getValue("AZURE_APP_WELL_KNOWN_URL")
        ) else LocalAzureAdAppConfig
}

internal class KtorConfig(private val httpPort: Int = 9000) {
    fun configure(builder: ApplicationEngineEnvironmentBuilder) {
        builder.connector {
            port = httpPort
        }
    }
}

internal interface IAzureAdAppConfig {
    fun configureVerification(configuration: JWTAuthenticationProvider.Configuration)
}

internal object LocalAzureAdAppConfig : IAzureAdAppConfig {
    override fun configureVerification(configuration: JWTAuthenticationProvider.Configuration) { return }
}

internal class AzureAdAppConfig(private val clientId: String, configurationUrl: String) : IAzureAdAppConfig {
    private val issuer: String
    private val jwkProvider: JwkProvider
    private val jwksUri: String

    init {
        configurationUrl.getJson().also {
            this.issuer = it["issuer"].textValue()
            this.jwksUri = it["jwks_uri"].textValue()
        }

        jwkProvider = JwkProviderBuilder(URL(this.jwksUri)).build()
    }

    override fun configureVerification(configuration: JWTAuthenticationProvider.Configuration) {
        configuration.verifier(jwkProvider, issuer) {
            withAudience(clientId)
        }
        configuration.validate { credentials -> JWTPrincipal(credentials.payload) }
    }

    private fun String.getJson(): JsonNode {
        val (responseCode, responseBody) = this.fetchUrl()
        if (responseCode >= 300 || responseBody == null) throw RuntimeException("got status $responseCode from ${this}.")
        return jacksonObjectMapper().readTree(responseBody)
    }

    private fun String.fetchUrl() = with(URL(this).openConnection() as HttpURLConnection) {
        requestMethod = "GET"
        val stream: InputStream? = if (responseCode < 300) this.inputStream else this.errorStream
        responseCode to stream?.bufferedReader()?.readText()
    }
}
