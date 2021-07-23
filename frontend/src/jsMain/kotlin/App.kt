import modell.MainView
import modell.Person
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.selectors.CSSSelector
import org.jetbrains.compose.web.css.selectors.id
import org.jetbrains.compose.web.css.selectors.type
import org.jetbrains.compose.web.renderComposable

private object GlobalStyles: StyleSheet() {
    init {
        CSSSelector.Universal style {
            property("font-family", "Source Code Pro, monospace")
        }
        "html" style {
            height(100.vh)
        }
        "body" style {
            margin(0.px)
            padding(0.px)
            height(100.percent)
        }
        id("root") style {
            property("height", "inherit")
        }
        type("p") style {
            margin(0.px)
            padding(0.px)
        }
    }
}

fun main() {
    val mainView = MainView()
    val (dto, json) = PersonParser.person()
    val person = Person.from(dto, json, mainView)
    mainView.setView(person)

    renderComposable(rootElementId = "root") {
        Style(GlobalStyles)
        mainView.render(person)
    }
}
