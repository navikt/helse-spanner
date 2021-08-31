import kotlinx.browser.document
import react.*
import react.dom.h2
import react.dom.render

external interface WelcomeProps : Props {
    operator fun component1(): Any

    var navn: String
}

private val welcome = fc<WelcomeProps> {props ->
    h2 {
        +"Hello, ${props.navn}"
    }
}

fun RBuilder.welcome(handler: WelcomeProps.() -> Unit) = child(welcome) {
    attrs {
        handler()
    }
}

fun main() {
    document.bgColor = "blue"

    render(document.getElementById("root")) {
        welcome  { navn = "Verden"}
    }
}