import kotlinx.browser.document
import react.RProps
import react.child
import react.dom.h2
import react.dom.render
import react.functionalComponent

external interface WelcomeProps : RProps {
    var name: String
}

private val welcome = functionalComponent<WelcomeProps> { props ->
    h2 {
        +"Hello, ${props.name}"
    }
}

fun main() {
    document.bgColor = "blue"

    render(document.getElementById("root")) {
        child(welcome) { attrs.name = "Noen" }
    }
}