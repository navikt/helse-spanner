package no.nav.spanner


import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.overriding
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.slf4j.LoggerFactory


val logg = Log.logger("Spanner")

fun main() {
    val configProps = ConfigurationProperties.systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource(".env");
    val spannerConfig = Config.from(configProps)
    logg
        .åpent("Spanner config", spannerConfig)
        .info("Spanner startet")

    val adConfig = AzureADConfig.fromEnv(configProps)
    val spleis = if (spannerConfig.development) LokaleKjenninger else Spleis.from(spannerConfig, AzureAD(adConfig))

    embeddedServer(CIO, environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("Spanner")
        developmentMode = spannerConfig.development

        module {
            spanner(spleis, adConfig, spannerConfig.development)
        }

        connector {
            port = spannerConfig.port
            host = spannerConfig.host
        }
    }).start(true)
}
