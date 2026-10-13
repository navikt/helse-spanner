package no.nav.spanner

import com.github.navikt.tbd_libs.azure.createAzureTokenClientFromEnvironment
import com.github.navikt.tbd_libs.azure.createDefaultAzureTokenClient
import com.github.navikt.tbd_libs.personpseudoid.ValkeyPersonPseudoIdProvider
import com.github.navikt.tbd_libs.speed.SpeedClient
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
import no.nav.spanner.spurtedu.SpurteDuClient
import org.slf4j.LoggerFactory
import tools.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.net.URI
import java.net.http.HttpClient

private val logg = Log.logger("Main")

private fun Configuration.stringProp(navn: String) = get(Key(navn, stringType))

fun main() {
    val config =
        ConfigurationProperties.systemProperties() overriding
            ConfigurationProperties.fromOptionalFile(File(".env")) overriding
            EnvironmentVariables()

    val spannerConfig = Config.from(config)
    logg
        .åpent("Spanner config", spannerConfig)
        .info("Spanner startet")

    val adConfig = AzureADConfig.fromEnv(config)

    val speedClient =
        SpeedClient(
            httpClient = HttpClient.newHttpClient(),
            objectMapper = jacksonObjectMapper(),
            tokenProvider =
                createDefaultAzureTokenClient(
                    tokenEndpoint = URI(config.stringProp("AZURE_OPENID_CONFIG_TOKEN_ENDPOINT")),
                    clientId = config.stringProp("AZURE_APP_CLIENT_ID"),
                    clientSecret = config.stringProp("AZURE_APP_CLIENT_SECRET"),
                ),
        )

    val tokenProvider = createAzureTokenClientFromEnvironment()
    val spurteDuClient =
        SpurteDuClient(
            httpClient = HttpClient.newHttpClient(),
            objectMapper = objectMapper,
            tokenProvider = tokenProvider,
            baseUrl = "http://spurtedu",
            scope = "api://${System.getenv("NAIS_CLUSTER_NAME")}.tbd.spurtedu/.default",
        )
    val spleis = Spleis.from(spannerConfig, tokenProvider)
    val personPseudoIdProvider = ValkeyPersonPseudoIdProvider.fraEnv(instansNavn = "personpseudoid", env = System.getenv())

    System.setProperty("io.ktor.development", spannerConfig.development.toString())
    embeddedServer(
        CIO,
        environment =
            applicationEnvironment {
                log = LoggerFactory.getLogger("Spanner")
            },
        configure = {
            connector {
                port = spannerConfig.port
            }
        },
    ) {
        spanner(spleis, speedClient, personPseudoIdProvider, spurteDuClient, adConfig, spannerConfig.development)
    }.start(true)
}
