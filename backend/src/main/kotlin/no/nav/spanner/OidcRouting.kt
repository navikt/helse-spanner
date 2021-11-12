package no.nav.spanner

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import no.nav.spanner.Log.Companion.logger


fun Route.oidc() {
    val logg = logger(Route::class.java)
    get("/login") { /* Redirects to 'authorizeUrl' automatically*/ }

    get("/oauth2/callback") {
        val principal: OAuthAccessTokenResponse.OAuth2 = call.principal()!!

        val accessToken = principal.accessToken
        val idToken = principal.extraParameters["id_token"]!!
        val expiry = principal.expiresIn
        // val refreshToken = principal.refreshToken!! TODO vi får ikke refresh token fra AD
        call.sessions.set(SpannerSession(accessToken, idToken, "refreshToken", expiry))
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