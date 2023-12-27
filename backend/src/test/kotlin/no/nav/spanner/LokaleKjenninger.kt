package no.nav.spanner.no.nav.spanner

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import no.nav.spanner.IdType
import no.nav.spanner.Personer
import no.nav.spanner.logger

object LokaleKjenninger : Personer {
    override suspend fun person(call: ApplicationCall, id: String, idType: IdType) {
        val response = when (id) {
            "113eb3df-102d-4a07-9270-2caa648c62f4" -> lesJson("12020052345")
            "48bfef57-3080-4f19-98eb-5cc72d9d16c5" -> lesJson("2392363031327")
            else -> throw NotFoundException("no person with identifier: ${id}")
        }.also {
            logger.trace("person fetched = ${it}")
        }
        call.respondText(response, ContentType.Application.Json, HttpStatusCode.OK)
    }

    override suspend fun maskerPerson(call: ApplicationCall, id: String, idType: IdType) {
        val response = when (id) {
            "42",
            "12020052345" -> """ { "id": "113eb3df-102d-4a07-9270-2caa648c62f4" } """

            "2392363031327" -> """ { "id": "48bfef57-3080-4f19-98eb-5cc72d9d16c5" } """
            else -> throw NotFoundException("no person with identifier: ${id}")
        }.also {
            logger.trace("person fetched = ${it}")
        }
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