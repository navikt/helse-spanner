import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.api(restClient: RestClient) {
    routing {
        authenticate("oidc") {
            get("/api/person-fnr") {
                val fnr = call.request.header("fnr")

                if (fnr == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val person = restClient.hentPersonMedFnr(fnr)
                call.respond(HttpStatusCode.OK, person)
            }

            get("/api/person-aktorid") {
                val aktørId = call.request.header("aktørId")

                if (aktørId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val person = restClient.hentPersonMedAktørId(aktørId)?.let { call.respond(it) }
                    ?: call.respond(HttpStatusCode.NotFound, "Resource not found")
            }
        }
    }
}