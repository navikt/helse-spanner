import modell.Person
import org.jetbrains.compose.web.renderComposable

fun main() {
    val person = Person.from(Testerino.person())
    renderComposable(rootElementId = "root") {
        person.render()
    }
}
