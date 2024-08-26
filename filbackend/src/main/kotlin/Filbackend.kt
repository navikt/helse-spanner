import Filsluse.finnHendelse
import Filsluse.finnPerson
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
    embeddedServer(CIO, environment = applicationEngineEnvironment {
        val personer = mutableMapOf<UUID, String>()
        val hendelser = mutableMapOf<UUID, String>()

        module {
            routing {
                frontendRouting()

                get("/api/meg") {
                    call.respondText("""{ "navn": "Viggo Velferdsvenn", "ident": "V1660" } """, Json, OK)
                }

                get("/api/person/") {
                    val maskertId = call.request.headers["maskertId"]?.let { UUID.fromString(it) } ?: return@get call.respond(BadRequest)
                    val person = personer[maskertId] ?: maskertId.finnPerson() ?: return@get call.respond(NotFound)
                    call.respondText(person, Json, OK)
                }

                post("/api/uuid/") {
                    val maskertId = call.request.headers["maskertId"]?.let { UUID.fromString(it) } ?: return@post call.respond(BadRequest)
                    val person = personer[maskertId] ?: maskertId.finnPerson() ?: return@post call.respond(NotFound)
                    call.respondText(person, Json, OK)
                }

                get("/api/hendelse/{meldingsreferanse}") {
                    val meldingsreferanse = call.parameters["meldingsreferanse"]?.let { UUID.fromString(it) } ?: return@get call.respond(BadRequest)
                    val hendelse = hendelser[meldingsreferanse] ?: meldingsreferanse.finnHendelse() ?: return@get call.respond(NotFound)
                    call.respondText(hendelse, Json, OK)
                }

                post("/api/{type}/{uuid}") {
                    val type = call.parameters["type"]?.takeIf { it in setOf("person", "hendelse") } ?: return@post call.respond(BadRequest)
                    val uuid = call.parameters["uuid"]?.let { UUID.fromString(it) } ?: return@post call.respond(BadRequest)
                    val json = call.receiveText()
                    if (type == "hendelse") hendelser[uuid] = json
                    else personer[uuid] = json
                    call.respond(Created)
                }

                get("/internal/*") {
                    call.respond(OK)
                }
            }
        }

        connector {
            port = 3000
        }
    }).start(true)
}

private fun Route.frontendRouting() {
    get("/person/*") {
        call.respondText(
            this::class.java.classLoader.getResource("static/index.html")!!.readText(),
            ContentType.Text.Html
        )
    }
    staticResources("/", "/static/")
}
