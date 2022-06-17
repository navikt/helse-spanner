package no.nav.spanner

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing


internal fun Application.naisApi() {
    routing {
        get("/internal/isalive") {
            call.respondText("ALIVE")
        }

        get("/internal/isready") {
            call.respondText("READY")
        }
    }
}
