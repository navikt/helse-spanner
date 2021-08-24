package no.nav.spanner

import io.ktor.server.netty.*
import no.nav.spanner.Log.Companion.logger

fun main() {
    try {
        EngineMain.main(emptyArray())
    } catch (err: Throwable) {
        logger("App")
            .exception(err)
            .error("Feilet i oppstart")
    }
}
