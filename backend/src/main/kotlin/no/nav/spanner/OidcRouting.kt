package no.nav.spanner

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import no.nav.spanner.Log.Companion.logger
import org.intellij.lang.annotations.Language


fun Route.oidc() {
    val logg = logger(Route::class.java)
    get("/login") { /* Redirects to 'authorizeUrl' automatically*/ }

    get("/oauth2/callback") {
        val principal: OAuthAccessTokenResponse.OAuth2 = call.principal() ?: return@get call.respondRedirect("/").also {
            logg.info("principal var visst null, vi redirecter til '/'!")
        }

        val accessToken = principal.accessToken
        val refreshToken = principal.refreshToken!!
        val idToken = principal.extraParameters["id_token"]!!
        val expiry = principal.expiresIn
        call.sessions.set(SpannerSession(accessToken, refreshToken, idToken, expiry))
        logg.info("Hentet tokens")
        @Language("HTML")
        val refreshHtml = """<!doctype html>
        <html>
            <head>  
                <meta charset="UTF-8">
                <title>OIDC site-refresh</title>  
                <meta http-equiv="refresh" content="0;URL='/'" />
            </head>
            <body>
                <p>Tar deg tilbake, vent litt 🤪</p>
            </body>
        </html>"""
        call.respondText(refreshHtml, ContentType.Text.Html)
    }
}