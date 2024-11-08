package no.nav.spanner.no.nav.spanner

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import no.nav.spanner.Personer

object LokaleKjenninger : Personer {
    override suspend fun person(call: ApplicationCall, fnr: String, aktørId: String) {
        val response = lesJson(fnr)
        call.respondText(response, ContentType.Application.Json, HttpStatusCode.OK)
    }

    override suspend fun hendelse(call: ApplicationCall, meldingsreferanse: String) {
        call.respondText("{}", ContentType.Application.Json, HttpStatusCode.OK)
    }

    override suspend fun speilperson(call: ApplicationCall, fnr: String) {
        call.respondText("{}", ContentType.Application.Json, HttpStatusCode.OK)
    }

    private fun lesJson(filnavn: String) =
        Personer::class.java.getResource("/personer/$filnavn.json")?.readText()!!
}