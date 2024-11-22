package no.nav.spanner


import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.navikt.tbd_libs.azure.createAzureTokenClientFromEnvironment
import com.github.navikt.tbd_libs.azure.createDefaultAzureTokenClient
import com.github.navikt.tbd_libs.speed.SpeedClient
import com.github.navikt.tbd_libs.spurtedu.SpurteDuClient
import com.natpryce.konfig.Configuration
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.Key
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType
import io.ktor.server.cio.CIO
import io.ktor.server.engine.applicationEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URI
import java.net.http.HttpClient

private val logg = Log.logger("Main")
private fun Configuration.stringProp(navn: String) = get(Key(navn, stringType))

fun main() {
    val config = ConfigurationProperties.systemProperties() overriding
            ConfigurationProperties.fromOptionalFile(File(".env")) overriding
            EnvironmentVariables()

    val spannerConfig = Config.from(config)
    logg
        .åpent("Spanner config", spannerConfig)
        .info("Spanner startet")

    val adConfig = AzureADConfig.fromEnv(config)

    val speedClient = SpeedClient(
        httpClient = HttpClient.newHttpClient(),
        objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule()),
        tokenProvider = createDefaultAzureTokenClient(
            tokenEndpoint = URI(config.stringProp(config.stringProp("TOKEN_ENDPOINT_ENV_KEY"))),
            clientId = config.stringProp("AZURE_APP_CLIENT_ID"),
            clientSecret = config.stringProp("AZURE_APP_CLIENT_SECRET")
        ),
        baseUrl = config.stringProp("SPEED_API_URL")
    )

    val tokenProvider = createAzureTokenClientFromEnvironment()
    val spurteDuClient = SpurteDuClient(
        objectMapper = objectMapper,
        tokenProvider = tokenProvider
    )
    val spleis = Spleis.from(spannerConfig, tokenProvider)

    System.setProperty("io.ktor.development", spannerConfig.development.toString())
    embeddedServer(CIO, environment = applicationEnvironment {
        log = LoggerFactory.getLogger("Spanner")
    }, configure = {
        connector {
            port = spannerConfig.port
        }
    }) {
        spanner(spleis, speedClient, spurteDuClient, påkrevdSpurteduTilgang = System.getenv("SPURTE_DU_TILGANG"), adConfig, spannerConfig.development)
    }.start(true)
}
