package no.nav.spanner

import io.ktor.server.application.*
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.HttpMethod.Companion.Post
import io.ktor.http.HttpMethod.Companion.Put
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.RoutingContext
import no.nav.spanner.AuditLogger.Operasjon.*
import org.slf4j.LoggerFactory
import java.time.ZonedDateTime
import java.time.ZonedDateTime.now

internal class AuditLogger(private val brukerIdent: String) {

    fun log(call: ApplicationCall) {
        val operasjon = when (call.request.httpMethod) {
            Get -> LES
            Post, Put -> SKRIV
            else -> UKJENT
        }
        val message = call.request.path()
        val fnr = call.request.headers[IdType.FNR.header]
        val aktorId = call.request.headers[IdType.AKTORID.header]
        val melding = lagMelding(operasjon, fnr?.toLong(), aktorId?.toLong(), message, now())
        logger.info(melding)
    }

    internal fun lagMelding(operasjon: Operasjon = LES,
                            fnr: Long?,
                            aktorId: Long?,
                            path: String,
                            tidspunkt: ZonedDateTime
    ): String {
        val now = tidspunkt.toInstant().toEpochMilli()
        val subject = fnr?.toString()?.padStart(11, '0') ?: aktorId
        val duidStr = subject?.let { " duid=$it" } ?: ""
        return "CEF:0|Vedtaksløsning for sykepenger|Spanner|1.0|${operasjon.logString}|Sporingslogg|INFO|end=$now$duidStr suid=$brukerIdent request=$path"
    }

    enum class Operasjon(val logString: String) {
        LES("audit:access"),
        SKRIV("audit:update"),
        UKJENT("audit:unknown")
    }

    companion object {
        private val logger = LoggerFactory.getLogger("auditLogger")
        fun RoutingContext.audit() =
            call.principal<JWTPrincipal>()?.audit()?.log(call)

        fun JWTPrincipal.audit(): AuditLogger {
            val ident = requireNotNull(this["NAVident"]) { "NAVident mangler i tokenet" }
            return AuditLogger(ident)
        }
    }
}
