import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.slf4j.LoggerFactory


internal fun Application.api(restClient: IRestClient, isLocal: Boolean, azureAdClient: IAzureAdClient) {
    val sikkerLogg = LoggerFactory.getLogger("tjenestekall")

    routing {
        authenticate(AZURE_OAUTH, optional = isLocal) {
            get("/login") {}

            get("/api/oauth2/callback") {
                val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                    ?: throw Exception("No principal was given")

                azureAdClient.verifyOidcResponse(principal)

                val accessToken = principal.accessToken
                val refreshToken = principal.refreshToken
                val expiresIn = principal.expiresIn

                val (idTokenString, idTokenClaims) = azureAdClient.verifyOidcResponse(principal)
                val bruker = idTokenClaims.body["name"].toString()

                sikkerLogg.info("$bruker har muligens logget inn")

                val session = SpannerSession(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    expiresIn = expiresIn,
                    idToken = idTokenString,
                    bruker = bruker
                )

                call.sessions.set(session)
                call.respondRedirect("/")
            }

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