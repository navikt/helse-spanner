package no.nav.spanner

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NaisTest {

    @Test
    fun `liveness endpoint responds`() {
        testApplication {
            application { naisApi() }
            val response = client.get("/internal/isalive")
            assertEquals(OK, response.status)
        }
    }

    @Test
    fun `readiness endpoint responds`() {
        testApplication {
            application { naisApi() }
            val response = client.get("/internal/isready")
            assertEquals(OK, response.status)
        }
    }
}