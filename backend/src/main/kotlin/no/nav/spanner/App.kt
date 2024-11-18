package no.nav.spanner


import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.navikt.tbd_libs.azure.AzureToken
import com.github.navikt.tbd_libs.azure.AzureTokenProvider
import com.github.navikt.tbd_libs.azure.createAzureTokenClientFromEnvironment
import com.github.navikt.tbd_libs.result_object.Result
import com.github.navikt.tbd_libs.result_object.ok
import com.github.navikt.tbd_libs.speed.SpeedClient
import com.github.navikt.tbd_libs.spurtedu.SpurteDuClient
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.overriding
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.slf4j.LoggerFactory
import java.io.File
import java.net.http.HttpClient
import java.time.LocalDateTime


private val logg = Log.logger("Main")

fun main() {
    val configProps = ConfigurationProperties.systemProperties() overriding
            ConfigurationProperties.fromOptionalFile(File(".env")) overriding
            EnvironmentVariables()

    val spannerConfig = Config.from(configProps)
    logg
        .åpent("Spanner config", spannerConfig)
        .info("Spanner startet")

    val adConfig = AzureADConfig.fromEnv(configProps)
    val tokenProvider = createAzureTokenClientFromEnvironment()

    val speedClient = SpeedClient(
        httpClient = HttpClient.newHttpClient(),
        objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule()),
        tokenProvider = if (System.getenv("NAIS_CLUSTER_NAME") == "dev-gcp") {
            object : AzureTokenProvider {
                override fun bearerToken(scope: String): Result<AzureToken> {
                    return AzureToken("dummy_token_i_dev", LocalDateTime.MAX).ok()
                }

                override fun onBehalfOfToken(scope: String, token: String): Result<AzureToken> {
                    return AzureToken("dummy_obo_token_i_dev", LocalDateTime.MAX).ok()
                }
            }
        } else tokenProvider,
        baseUrl = if (System.getenv("NAIS_CLUSTER_NAME") == "dev-gcp") {
            "http://speed-api-dev-proxy"
        } else null
    )
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
