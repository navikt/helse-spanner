package no.nav.spanner

import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.principal
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import no.nav.spanner.Log.Companion.logger


fun Route.oidc() {
    val logg = logger(Route::class.java)
    get("/login") { /* Redirects to 'authorizeUrl' automatically*/ }

    get("/oauth2/callback") {
        val principal: OAuthAccessTokenResponse.OAuth2 = call.principal()!!

        val accessToken = principal.accessToken
        val idToken = principal.extraParameters["id_token"]!!
        val expiry = principal.expiresIn
        call.sessions.set(SpannerSession(accessToken, idToken, expiry))
        logg
            .info("Hentet tokens")
        call.respondText(
            " <html xmlns=\"http://www.w3.org/1999/xhtml\">    \n" +
                    "  <head>      \n" +
                    "    <title>OIDC site-refresh</title>      \n" +
                    "    <meta http-equiv=\"refresh\" content=\"0;URL='/'\" />    \n" +
                    "  </head>    \n" +
                    "  <body> \n" +
                    "    <p>Refreshing site \uD83D\uDD12</p> \n" +
                    "  </body>  \n" +
                    "</html>     ", ContentType.Text.Html
        )
    }
}