package no.nav.spanner

import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.callloging.*
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
import java.util.*

enum class IdType(val header: String) {
    FNR("fnr"), AKTORID("aktorId"), MASKERT_ID("maskertId")
}

private val logg = Log.logger("Spanner")

fun Application.spanner(spleis: Personer, config: AzureADConfig, development: Boolean) {

    install(CallId) {
        generate {
            UUID.randomUUID().toString()
        }
    }
    install(CallLogging) {
        disableDefaultColors()
        logger = LoggerFactory.getLogger("CallLogging")
        level = Level.INFO
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

    install(ContentNegotiation) { register(ContentType.Application.Json, JacksonConverter(objectMapper)) }

    install(Authentication) {
        config.konfigurerJwtAuth(this)
    }

    naisApi()

    routing {
        authenticate(optional = development) {
            frontendRouting()
            get("/api/person/") {
                audit()
                val (idType, idValue) = call.personId()
                logg
                    .åpent("idType", idType)
                    .sensitivt("idValue", idValue)
                    .call(this.call)
                    .info()
                if (idValue.isNullOrBlank())
                    return@get call.respond(HttpStatusCode.BadRequest, "${IdType.AKTORID.header} or ${IdType.FNR.header} must be set")
                spleis.person(call, idValue, idType)
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
                    return@post call.respond(HttpStatusCode.BadRequest, "${IdType.AKTORID.header} or ${IdType.FNR.header} must be set")
                spleis.maskerPerson(call, idValue, idType)
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
                    return@get call.respond(HttpStatusCode.BadRequest, "${IdType.FNR.header} must be set")
                spleis.speilperson(call, idValue)
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
        }
    }
}

private fun ApplicationCall.personId() =
    request.headers[IdType.MASKERT_ID.header]?.let {
        Pair(IdType.MASKERT_ID, it)
    } ?: request.headers[IdType.FNR.header]?.let {
        Pair(IdType.FNR, it)
    } ?: Pair(IdType.AKTORID, request.header(IdType.AKTORID.header))
