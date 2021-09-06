package no.nav.spanner

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.OAuth2Config
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import org.slf4j.LoggerFactory

fun startLocal() {
    val mockAuth = MockOAuth2Server(config = OAuth2Config(true))
    mockAuth.enqueueCallback(
        DefaultOAuth2TokenCallback(
            expiry = 3600,
            claims = mapOf("NAVident" to "H12345")
        )
    )
    mockAuth.start()

    val adConfig = AzureADConfig(
        discoveryUrl = mockAuth.wellKnownUrl("default").toString(),
        clientId = "whatever",
        clientSecret = "supersecret",
        spleisClientId = "los spleisos"
    )
    val spannerConfig = Config(
        true,
        8080,
        "0.0.0.0",
    "not is use"
    )

    val spleis = LokaleKjenninger

    embeddedServer(CIO, environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("Spanner")
        developmentMode = spannerConfig.development

        module {
            spanner(spleis, adConfig, spannerConfig.development)
        }

        connector {
            port = spannerConfig.port
        }
    }).start(true)
    mockAuth.shutdown()
}

fun main() = startLocal()