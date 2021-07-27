import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

internal fun Application.api(restClient: IRestClient) {
    routing {
        authenticate(API_SERVICE) {
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
                val person = restClient.hentPersonMedAktørId(aktørId)
                call.respond(HttpStatusCode.OK, person)
            }
        }
    }
}