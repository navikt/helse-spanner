import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.slf4j.LoggerFactory

internal fun Application.authApi(azureAdClient: IAzureAdClient, isLocal: Boolean) {
    val sikkerLogg = LoggerFactory.getLogger("tjenestekall")

    routing {
        intercept(phase = ApplicationCallPipeline.Call) {
            if (call.request.path() in listOf("/login", "/oauth2/callback", "/logout")) return@intercept

            sikkerLogg.info("Nå intercepter vi :)")
            val session = call.sessions.get<SpannerSession>()

            if (session != null &&
                (!session.accessToken.hasExpired() || azureAdClient.refreshAccessToken(session))
            ) {
                return@intercept
            } else {
                call.respondRedirect("/login")
                return@intercept finish()
            }
        }

        authenticate(AZURE_OAUTH, optional = isLocal) {
            get("/login") {}

            get("/oauth2/callback") {
                val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                    ?: throw Exception("No principal was given")

                val accessToken = AccessToken.from(principal)

                try {
                    accessToken.verifyOidcResponse(azureAdClient)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Unauthorized)
                    sikkerLogg.info("Feil ved verifisering av aksesspolett")
                }

                val session = SpannerSession(accessToken)
                call.sessions.set(session)

                sikkerLogg.info("${accessToken.bruker()} har logget inn")
                call.respondText("Logget inn som ${accessToken.bruker()}")
            }
        }

        get("/logout") {
            call.sessions.clear<SpannerSession>()
            sikkerLogg.info("Bruker har logget ut")
            call.respondText("Bruker har logget ut")
        }

        get("/respond-ok") {
            sikkerLogg.info("${call.sessions.get<SpannerSession>()?.accessToken?.bruker() ?: "ingen bruker"} er allerede logget inn")
            call.respondText("Er allerede logget inn som ${call.sessions.get<SpannerSession>()?.accessToken?.bruker()}")
        }
    }
}


internal fun Application.api(restClient: IRestClient, isLocal: Boolean) {
    routing {
        authenticate(AZURE_OAUTH, optional = isLocal) {
            get("/api/person-fnr") {
                val fnr = call.request.header("fnr")

                if (fnr == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val accessToken = call.sessions.get<SpannerSession>()!!.accessToken
                val person = restClient.hentPersonMedFnr(fnr, accessToken)

                call.respond(HttpStatusCode.OK, person)
            }

            get("/api/person-aktorid") {
                val aktørId = call.request.header("aktorId")

                if (aktørId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val accessToken = call.sessions.get<SpannerSession>()!!.accessToken
                val person = restClient.hentPersonMedAktørId(aktørId, accessToken)

                call.respond(HttpStatusCode.OK, person)
            }
        }
    }
}