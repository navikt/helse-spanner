package no.nav.spanner

import com.fasterxml.jackson.core.JsonParseException
import com.github.navikt.tbd_libs.result_object.getOrThrow
import com.github.navikt.tbd_libs.speed.SpeedClient
import com.github.navikt.tbd_libs.spurtedu.SkjulRequest
import com.github.navikt.tbd_libs.spurtedu.SpurteDuClient
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.nav.spanner.AuditLogger.Companion.audit
import no.nav.spanner.Log.Companion.LogLevel
import no.nav.spanner.Log.Companion.LogLevel.*
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import java.io.IOException
import java.time.LocalDate
import java.util.*
import no.nav.spanner.requests.HentAltSpiskammersetRequest

enum class IdType(val header: String) {
    FNR("fnr"), AKTORID("aktorId"), MASKERT_ID("maskertId")
}

private val logg = Log.logger("Spanner")

fun Application.spanner(
    spleis: Personer,
    speedClient: SpeedClient,
    spurteDuClient: SpurteDuClient,
    påkrevdSpurteduTilgang: String,
    config: AzureADConfig,
    development: Boolean,
) {

    install(CallId) {
        generate {
            UUID.randomUUID().toString()
        }
    }
    install(CallLogging) {
        disableDefaultColors()
        logger = LoggerFactory.getLogger("CallLogging")
        level = Level.INFO
        callIdMdc("callId")
        filter { call -> !call.request.path().startsWith("/internal") }
    }
    install(XForwardedHeaders)

    install(StatusPages) {
        suspend fun respondToException(
            status: HttpStatusCode,
            call: ApplicationCall,
            cause: Throwable,
            level: LogLevel
        ) {
            val errorId = UUID.randomUUID()
            logg
                .åpent("errorId", errorId)
                .åpent("status", status)
                .call(call)
                .exception(cause)
                .log(level)
            call.respond(status, FeilRespons(errorId.toString(), cause.message))
        }
        exception<NotFoundException> { call, cause ->
            respondToException(HttpStatusCode.NotFound, call, cause, INFO)
        }
        exception<BadRequestException> { call, cause ->
            respondToException(HttpStatusCode.BadRequest, call, cause, INFO)
        }
        exception<IOException> { call, cause ->
            if (cause.message == "Broken pipe") respondToException(HttpStatusCode.ServiceUnavailable, call, cause, WARN)
            else respondToException(HttpStatusCode.InternalServerError, call, cause, ERROR)
        }
        exception<Throwable> { call, cause ->
            respondToException(HttpStatusCode.InternalServerError, call, cause, ERROR)
        }
    }

    install(ContentNegotiation) { register(Json, JacksonConverter(objectMapper)) }

    install(Authentication) {
        config.konfigurerJwtAuth(this)
    }

    naisApi()

    routing {
        authenticate(optional = development) {
            frontendRouting()
            get("/api/meg") {
                val principal = call.principal<JWTPrincipal>() ?: return@get call.respond(HttpStatusCode.Unauthorized)
                val navn = principal["name"]
                val ident = principal["NAVident"]
                call.respondText("""{ "navn": "$navn", "ident": "$ident" } """, Json, OK)
            }
            get("/api/person/") {
                audit()
                val maskertId = call.request.headers[IdType.MASKERT_ID.header]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, FeilRespons("bad_request", "header ${IdType.MASKERT_ID.header} må være satt"))
                logg.call(this.call).info()

                val id = try {
                    UUID.fromString(maskertId)
                } catch (_: Exception) {
                    return@get call.respond(HttpStatusCode.BadRequest, FeilRespons("bad_request", "maskert id må være uuid"))
                }

                val tekstinnhold = spurteDuClient.vis(id, call.bearerToken).text
                val fødselsnummer = try {
                    val node = objectMapper.readTree(tekstinnhold)
                    val ident = node.path("ident").asText()
                    val identype = node.path("identtype").asText()
                    when (identype.lowercase()) {
                        "fnr" -> ident
                        else -> null
                    }
                } catch (_: JsonParseException) {
                    null
                }

                if (fødselsnummer == null) return@get call.respond(HttpStatusCode.BadRequest, FeilRespons("bad_request", "maskertId pekte ikke på et fødselsnummer"))
                val aktørId = speedClient.hentFødselsnummerOgAktørId(fødselsnummer, call.callId ?: UUID.randomUUID().toString()).getOrThrow().aktørId
                spleis.person(call, fødselsnummer, aktørId)
            }
            post("/api/uuid/") {
                audit()
                val (idType, idValue) = call.personId()
                logg
                    .åpent("idType", idType)
                    .sensitivt("idValue", idValue)
                    .call(this.call)
                    .info()
                if (idValue.isNullOrBlank())
                    return@post call.respond(HttpStatusCode.BadRequest, FeilRespons("bad_request", "${IdType.AKTORID.header} or ${IdType.FNR.header} must be set"))

                val fnr = when (idType) {
                    IdType.FNR -> idValue
                    IdType.AKTORID -> speedClient.hentFødselsnummerOgAktørId(idValue, call.callId ?: UUID.randomUUID().toString()).getOrThrow().fødselsnummer
                    IdType.MASKERT_ID -> return@post call.respond(HttpStatusCode.BadRequest, FeilRespons("bad_request", "${IdType.AKTORID.header} or ${IdType.FNR.header} must be set"))
                }

                val payload = SkjulRequest.SkjulTekstRequest(
                    tekst = objectMapper.writeValueAsString(mapOf(
                        "ident" to fnr,
                        "identtype" to IdType.FNR.name
                    )),
                    påkrevdTilgang = påkrevdSpurteduTilgang
                )
                val maskertId = spurteDuClient.skjul(payload)
                call.respondText(""" { "id": "${maskertId.id}" } """, Json, OK)
            }
            /*
                har ikke funksjonalitet fra frontend ennå, men kan kalles manuelt fra devtools feks:

                fetch("/api/speil-person/", {
                    method: 'get',
                    headers: {
                        Accept: 'application/json',
                        fnr: 'xxxxxxxxxxx'
                    }
                }).then(function (response) {
                    console.log(response)
                    response.json().then(function (json) {
                        console.log(json)
                    })
                })
             */
            get("/api/speil-person/") {
                audit()
                val (idType, idValue) = call.personId()
                if (idType != IdType.FNR) return@get call.respond(HttpStatusCode.BadRequest, "funker bare med fnr")
                logg
                    .åpent("idType", idType)
                    .sensitivt("idValue", idValue)
                    .call(this.call)
                    .info()
                if (idValue.isNullOrBlank())
                    return@get call.respond(HttpStatusCode.BadRequest, FeilRespons("bad_request", "${IdType.FNR.header} must be set"))
                spleis.speilperson(call, idValue)
            }

            /*
                har ikke funksjonalitet fra frontend ennå, men kan kalles manuelt fra devtools feks:

                fetch("/api/spiskammerset/perioder?fom=2026-01-01&tom=2026-01-31", {
                    method: 'get',
                    headers: {
                        Accept: 'application/json',
                        fnr: 'xxxxxxxxxxx'
                    }
                }).then(function (response) {
                    console.log(response)
                    response.json().then(function (json) {
                        console.log(json)
                    })
                })
            */
            get("/api/spiskammerset/perioder") {
                audit()
                val (idType, idValue) = call.personId()
                if (idType != IdType.FNR) return@get call.respond(HttpStatusCode.BadRequest, "funker bare med fnr")
                val fom = LocalDate.parse(call.queryParameters["fom"]!!)
                val tom = LocalDate.parse(call.queryParameters["tom"]!!)
                logg
                    .åpent("idType", idType)
                    .sensitivt("idValue", idValue)
                    .call(this.call)
                    .info()
                if (idValue.isNullOrBlank())
                    return@get call.respond(HttpStatusCode.BadRequest, FeilRespons("bad_request", "${IdType.FNR.header} must be set"))
                spleis.spiskammersetPerioder(call, idValue, fom, tom)
            }

            /*
            har ikke funksjonalitet fra frontend ennå, men kan kalles manuelt fra devtools feks:

            fetch("/api/spiskammerset/behandling/{behandlingId}?opplysning=forsikring&opplysning=noe-annet", {
                method: 'get',
                headers: {
                    Accept: 'application/json',
                    fnr: 'xxxxxxxxxxx'
                }
            }).then(function (response) {
                console.log(response)
                response.json().then(function (json) {
                    console.log(json)
                })
            })
            */
            get("/api/spiskammerset/behandling/{behandlingId}") {
                audit()
                val (idType, idValue) = call.personId()
                if (idType != IdType.FNR) return@get call.respond(HttpStatusCode.BadRequest, "funker bare med fnr")
                val behandlingId = call.parameters["behandlingId"]?.let { UUID.fromString(it) } ?: return@get call.respond(HttpStatusCode.BadRequest, "mangler behandlingId")
                val opplysninger = call.queryParameters.getAll("opplysning") ?: emptyList()
                logg
                    .åpent("idType", idType)
                    .sensitivt("idValue", idValue)
                    .call(this.call)
                    .info()
                if (idValue.isNullOrBlank()) return@get call.respond(HttpStatusCode.BadRequest, FeilRespons("bad_request", "${IdType.FNR.header} must be set"))
                spleis.spiskammersetOpplysninger(call, behandlingId, opplysninger)
            }

            get("/api/hendelse/{meldingsreferanse}") {
                audit()
                val meldingsreferanse = call.parameters["meldingsreferanse"] ?: throw BadRequestException("Mangler meldingsreferanse")
                logg
                    .åpent("meldingsreferanse", meldingsreferanse)
                    .call(this.call)
                    .info()

                spleis.hendelse(call, meldingsreferanse)
            }

            post("/api/spiskammerset/hentAlt") {
                audit()
                val request = call.receive<HentAltSpiskammersetRequest>()

                logg
                    .sensitivt("fnr", request.personidentifikator)
                    .call(this.call)
                    .info()
                spleis.spiskammersetHentAlt(call)
            }

        }
    }
}

private fun ApplicationCall.personId() =
    request.headers[IdType.MASKERT_ID.header]?.let {
        Pair(IdType.MASKERT_ID, it)
    } ?: request.headers[IdType.FNR.header]?.let {
        Pair(IdType.FNR, it)
    } ?: Pair(IdType.AKTORID, request.header(IdType.AKTORID.header))

private val ApplicationCall.bearerToken: String? get() {
    val httpAuthHeader = request.parseAuthorizationHeader() ?: return null
    if (httpAuthHeader !is HttpAuthHeader.Single) return null
    return httpAuthHeader.blob
}
