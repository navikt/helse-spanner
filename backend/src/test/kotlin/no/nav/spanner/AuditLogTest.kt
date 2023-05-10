package no.nav.spanner.no.nav.spanner

import no.nav.spanner.AuditLogger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime.now
import java.util.*

class AuditLogTest {

    @Test
    fun auditLogVerifiedFnr() {
        val nå = now()
        val end = nå.toInstant().toEpochMilli()
        val expected = "CEF:0|Spanner|auditLog|1.0|audit:access|Sporingslogg|INFO|end=$end duid=01010012345 suid=X000000 request=/api/person"
        val actual = AuditLogger("X000000").lagMelding(AuditLogger.Operasjon.LES, 1010012345L, null, "/api/person", nå)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun auditLogVerifiedAktørId() {
        val meldingsreferanse = UUID.randomUUID().toString()
        val nå = now()
        val end = nå.toInstant().toEpochMilli()
        val expected = "CEF:0|Spanner|auditLog|1.0|audit:update|Sporingslogg|INFO|end=$end duid=1000000000000 suid=X000000 request=/api/hendelse/$meldingsreferanse"
        val actual = AuditLogger("X000000").lagMelding(AuditLogger.Operasjon.SKRIV, null, 1000000000000L, "/api/hendelse/$meldingsreferanse", nå)

        Assertions.assertEquals(expected, actual)
    }
    @Test
    fun `om både fnr og aktørId er oppgitt, logger vi fnr`() {
        val nå = now()
        val end = nå.toInstant().toEpochMilli()
        val expected = "CEF:0|Spanner|auditLog|1.0|audit:access|Sporingslogg|INFO|end=$end duid=01010012345 suid=X000000 request=/api/person"
        val actual = AuditLogger("X000000").lagMelding(AuditLogger.Operasjon.LES, 1010012345L, 1000000000000L, "/api/person", nå)

        Assertions.assertEquals(expected, actual)
    }
}