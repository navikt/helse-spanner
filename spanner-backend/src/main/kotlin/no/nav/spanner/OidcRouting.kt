package no.nav.spanner

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*


fun Route.oidc() {
    get("/login") { /* Redirects to 'authorizeUrl' automatically*/ }

    get("/oauth2/callback") {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        val accessToken = principal?.accessToken.toString()
        call.sessions.set(SpannerSession(accessToken))
        // todo set cookie
        call.respondRedirect("/")
    }
}