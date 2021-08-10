package no.nav.spanner

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

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
