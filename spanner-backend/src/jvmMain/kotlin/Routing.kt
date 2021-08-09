import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.auth.OAuthAccessTokenResponse
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.header
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.sessions.clear
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import org.slf4j.LoggerFactory

private val sikkerLogg = LoggerFactory.getLogger("tjenestekall")

internal fun Application.authApi(azureAdClient: IAzureAdClient, isLocal: Boolean) {

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

internal fun Application.frontendRouting() {
    routing {
        get("/") {
            call.respondText(
                this::class.java.classLoader.getResource("static/index.html")!!.readText(),
                ContentType.Text.Html
            )
        }
        static("/") {
            resources("static/")
        }
    }
}

internal fun Application.api(restClient: IRestClient, azureAdClient: IAzureAdClient, isLocal: Boolean) {
    routing {
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

private suspend fun hentOnBehalfOfToken(azureAdClient: IAzureAdClient, call: ApplicationCall) =
    azureAdClient.hentOnBehalfOfToken(call.sessions.get<SpannerSession>()!!).also {
        if (it == null) call.respond(HttpStatusCode.Unauthorized)
    }

private suspend fun hentId(idHeaderType: String, call: ApplicationCall) =
    call.request.header(idHeaderType).also {
        if (it == null) call.respond(HttpStatusCode.BadRequest)
    }
