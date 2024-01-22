package no.nav.spanner

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
