package no.nav.spanner

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*

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
