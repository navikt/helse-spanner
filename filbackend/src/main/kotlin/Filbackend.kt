import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun main() {
    embeddedServer(
        factory = CIO,
        environment = applicationEnvironment {},
        configure = {
            connector {
                port = 3000
            }
        }
    ) {
        // Mediumenes rekkefølge er _ikke_ likegyldig. De er i prioritert rekkefølge
        val hentingsmedium = listOf(Minne, Fil)
        val lagringsmedium = listOf<Lagringsmedium>(Minne)

        routing {
            frontendRouting()

            get("/api/meg") {
                call.respondText("""{ "navn": "Viggo Velferdsvenn", "ident": "V1660" } """, Json, OK)
            }

            get("/api/person/") {
                val maskertId = call.uuidHeader("maskertId") ?: return@get call.respond(BadRequest)
                val person = hentingsmedium.hentPerson(maskertId) ?: return@get call.respond(NotFound)
                call.respondText(person, Json, OK)
            }

            post("/api/uuid/") {
                val maskertId = call.uuidHeader("maskertId") ?: return@post call.respond(BadRequest)
                val person = hentingsmedium.hentPerson(maskertId) ?: return@post call.respond(NotFound)
                call.respondText(person, Json, OK)
            }

            get("/api/hendelse/{meldingsreferanse}") {
                val meldingsreferanse = call.uuidParameter("meldingsreferanse") ?: return@get call.respond(BadRequest)
                val hendelse = hentingsmedium.hentHendelse(meldingsreferanse) ?: return@get call.respond(NotFound)
                call.respondText(hendelse, Json, OK)
            }

            post("/api/person/{uuid}") {
                val uuid = call.uuidParameter("uuid") ?: return@post call.respond(BadRequest)
                val person = call.receiveText()
                lagringsmedium.lagrePerson(uuid, person)
                call.respond(Created)
            }

            post("/api/hendelse/{uuid}") {
                val uuid = call.uuidParameter("uuid") ?: return@post call.respond(BadRequest)
                val hendelse = call.receiveText()
                lagringsmedium.lagreHendelse(uuid, hendelse)
                call.respond(Created)
            }

            get("/internal/*") {
                call.respond(OK)
            }
        }
    }.start(true)
}

private fun ApplicationCall.uuidHeader(navn: String) = request.headers[navn]?.let { UUID.fromString(it) }
private fun ApplicationCall.uuidParameter(navn: String) = parameters[navn]?.let { UUID.fromString(it) }
private fun Route.frontendRouting() {
    get("/person/*") {
        call.respondText(
            this::class.java.classLoader.getResource("static/index.html")!!.readText(),
            ContentType.Text.Html
        )
    }
    staticResources("/", "/static/")
}
