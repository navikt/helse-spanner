package modell

import PersonParser
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.json.JsonObject
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

class OuterViewContainer() {
    private val mainView = MainView()
    private var mainViewRenderer by mutableStateOf<@Composable () -> Unit>({ mainView.render(null) })
    private val søk = Personsøk()

    @Composable
    fun render() {
        return Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                padding(2.cssRem)
            }
        }) {
            søk.render {
                setPersonFraJson(it)
            }
            mainViewRenderer()
        }
    }

    private fun setPersonFraJson(personJson: JsonObject) {
        var nyPerson: Person? = null

        try {
            val (dto, json) = PersonParser.person(personJson)
            nyPerson = Person.from(dto, json, mainView)
            mainView.setDetaljView(nyPerson)
        } catch (e: Exception) {
            console.error("Feil ved parsing av respons til Person - Respons: ", personJson.toString())
        }

        mainViewRenderer = { mainView.render(nyPerson) }
    }
}
