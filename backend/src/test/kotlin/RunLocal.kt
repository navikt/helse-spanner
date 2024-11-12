package no.nav.spanner

import com.auth0.jwk.JwkProviderBuilder
import com.github.navikt.tbd_libs.speed.SpeedClient
import com.github.navikt.tbd_libs.spurtedu.SpurteDuClient
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.cors.routing.CORS
import io.mockk.mockk
import no.nav.security.mock.oauth2.MockOAuth2Server
import no.nav.security.mock.oauth2.OAuth2Config
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback
import no.nav.spanner.no.nav.spanner.LokaleKjenninger
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
        jwkProvider = JwkProviderBuilder(mockAuth.wellKnownUrl("default").toUrl()).build(),
        issuer = "default",
        clientId = "whatever"
    )
    val spannerConfig = Config(
        true,
        8080,
        "0.0.0.0",
        "not is use",
        "not is use",
        "not is use",
        "not is use"
    )

    val speedClient = mockk<SpeedClient>()
    val spurteDuClient = mockk<SpurteDuClient>()
    val påkrevdSpurteduTilgang = "skikkelig_tøysete_claim"
    val spleis = LokaleKjenninger

    embeddedServer(CIO, environment = applicationEnvironment {
        log = LoggerFactory.getLogger("Spanner")
    }, configure = {
        connector {
            port = spannerConfig.port
        }
    }) {
        install(CORS) {
            this.anyHost()
        }
        spanner(spleis, speedClient, spurteDuClient, påkrevdSpurteduTilgang, adConfig, spannerConfig.development)
    }.start(true)
    mockAuth.shutdown()
}

fun main() = startLocal()