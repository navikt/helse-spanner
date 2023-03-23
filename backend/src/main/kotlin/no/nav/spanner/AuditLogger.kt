package no.nav.spanner

import io.ktor.application.*
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.HttpMethod.Companion.Post
import io.ktor.http.HttpMethod.Companion.Put
import io.ktor.request.*
import no.nav.spanner.AuditLogger.Operasjon.*
import org.slf4j.LoggerFactory
import java.time.ZonedDateTime.now

internal class AuditLogger(private val brukerIdent: String) {

    private fun log(melding: String) {
        logger.info(melding)
    }

    fun log(call: ApplicationCall) {
        val operasjon = when (call.request.httpMethod) {
            Get -> LES
            Post, Put -> SKRIV
            else -> UKJENT
        }
        val message = call.request.path()
        val fnr = call.request.headers[IdType.FNR.header]
        val aktorId = call.request.headers[IdType.AKTORID.header]
        val melding = lagMelding(operasjon, fnr?.toLong(), aktorId?.toLong(), message)
        log(melding)
    }

    internal fun lagMelding(operasjon: Operasjon = LES,
                            fnr: Long?,
                            aktorId: Long?,
                            message: String = "Sporingslogg"): String {
        val now = now().toEpochSecond()
        val subject = fnr?.toString()?.padStart(9, '0') ?: aktorId
        val duidStr = subject?.let { " duid=$it" } ?: ""
        return "CEF:0|Spanner|auditLog|1.0|${operasjon.logString}|$message|INFO|end=$now suid=$brukerIdent$duidStr"
    }

    enum class Operasjon(val logString: String) {
        LES("audit:access"),
        SKRIV("audit:update"),
        UKJENT("audit:unknown")
    }

    companion object {
        private val logger = LoggerFactory.getLogger("auditLogger")
        fun SpannerSession.audit() =
            AuditLogger(idToken.asJwt().getClaim("NAVident").asString())
    }
}