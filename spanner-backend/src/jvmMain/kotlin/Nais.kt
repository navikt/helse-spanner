import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

internal fun Application.nais() {
    routing {
        get("/isalive") {
            call.respondText("ALIVE", ContentType.Text.Plain)
        }

        get("/isready") {
            call.respondText("READY", ContentType.Text.Plain)
        }

        get("/stop") {
            call.respondText("STOPPED", ContentType.Text.Plain)
        }
    }
}
