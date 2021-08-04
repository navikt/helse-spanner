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

            if (isLocal) {
                if (session == null) call.sessions.set(SpannerSession.createLocalSession())
                return@intercept
            }

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

internal fun Application.api(restClient: IRestClient, azureAdClient: IAzureAdClient, isLocal: Boolean) {
    routing {
        authenticate(AZURE_OAUTH, optional = isLocal) {
            get("/api/person-fnr") {
                val fnr = hentId("fnr", call).takeIf { it !== null } ?: return@get
                val onBehalfOfToken = hentOnBehalfOfToken(azureAdClient, call).takeIf { it !== null } ?: return@get

                val person = restClient.hentPersonMedFnr(fnr, onBehalfOfToken)
                call.respond(HttpStatusCode.OK, person)
            }

            get("/api/person-aktorid") {
                val aktørId = hentId("aktorId", call).takeIf { it !== null } ?: return@get
                val onBehalfOfToken = hentOnBehalfOfToken(azureAdClient, call).takeIf { it !== null } ?: return@get

                val person = restClient.hentPersonMedAktørId(aktørId, onBehalfOfToken)
                call.respond(HttpStatusCode.OK, person)
            }
        }
    }
}

private suspend fun hentOnBehalfOfToken(azureAdClient: IAzureAdClient, call: ApplicationCall) =
    azureAdClient.hentOnBehalfOfToken(call.sessions.get<SpannerSession>()!!).also {
        if (it == null) call.respond(HttpStatusCode.Unauthorized)
    }

private suspend fun hentId(idHeaderType: String, call: ApplicationCall) =
    call.request.header(idHeaderType).also {
        if (it == null) call.respond(HttpStatusCode.BadRequest)
    }