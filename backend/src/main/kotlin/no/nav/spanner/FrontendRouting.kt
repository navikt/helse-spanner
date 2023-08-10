package no.nav.spanner

import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Route.frontendRouting() {
    get("/") {
        call.respondText(
            this::class.java.classLoader.getResource("static/index.html")!!.readText(),
            ContentType.Text.Html
        )
    }
    get("/person/*") {
        call.respondText(
            this::class.java.classLoader.getResource("static/index.html")!!.readText(),
            ContentType.Text.Html
        )
    }
    static("/") {
        resources("static/")
    }
}
