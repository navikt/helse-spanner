package no.nav.spanner

import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NaisTest {

    @Test
    fun `liveness endpoint responds`() {
        withTestApplication({
            naisApi()
        }) {
            handleRequest(HttpMethod.Get, "/internal/isalive").apply {
                assertEquals(OK, response.status())
            }
        }
    }

    @Test
    fun `readiness endpoint responds`() {
        withTestApplication({
            naisApi()
        }) {
            handleRequest(HttpMethod.Get, "/internal/isready").apply {
                assertEquals(OK, response.status())
            }
        }
    }
}