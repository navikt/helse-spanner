package no.nav.spanner

import io.ktor.client.request.get
import io.ktor.http.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NaisTest {

    @Test
    fun `liveness endpoint responds`() {
        e2e({ naisApi() }) { client ->
            client.get("/internal/isalive").apply {
                assertTrue(status.isSuccess())
            }
        }
    }

    @Test
    fun `readiness endpoint responds`() {
        e2e({ naisApi() }) { client ->
            client.get("/internal/isready").apply {
                assertTrue(status.isSuccess())
            }
        }
    }
}