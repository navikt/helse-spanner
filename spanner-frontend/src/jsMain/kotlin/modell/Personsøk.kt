package modell

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput

class Personsøk {
    private val client = SpannerClient()
    var feilmelding by mutableStateOf("")

    @Composable
    fun render(setPersonCallback: (json: JsonObject) -> Unit) {
        val coroutineScope = rememberCoroutineScope()
        var inputText by remember { mutableStateOf("") }

        return Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Row)
                alignItems(AlignItems.Center)
            }
        }) {
            Text("Søk etter person:")

            Div(attrs = { style { marginLeft(1.cssRem) } }) {
                TextInput(
                    value = inputText,
                    attrs = {
                        onInput {
                            inputText = it.value
                        }
                        placeholder("Fnr/AktørId")
                        onKeyUp {
                            if (it.getNormalizedKey() == "Enter") {
                                coroutineScope.launch {
                                    val personJson = hentPerson(inputText)
                                    setPersonCallback(personJson)
                                }
                            }
                        }
                    }
                )
                Button(attrs = {
                    onClick {
                        coroutineScope.launch {
                            val personJson = hentPerson(inputText)
                            setPersonCallback(personJson)
                        }
                    }
                }) {
                    Text("Søk")
                }
            }

            if (feilmelding.isNotEmpty()) {
                Div(attrs = { style { marginLeft(1.cssRem); color(Color.red) } }) {
                    Text(feilmelding)
                }
            }
        }
    }

    private suspend fun hentPerson(text: String): JsonObject {
        feilmelding = ""
        try {
            val personJson = when {
                text.length == 11 -> client.hentPersonMedFnr(text)
                text.length == 13 || text == "42" -> client.hentPersonMedAktørId(text)
                else -> JsonObject(emptyMap())
            }

            if (personJson.isEmpty()) {
                feilmelding = "Kunne ikke finne person \uD83D\uDE31"
            }

            return personJson
        } catch (e: Exception) {
            feilmelding = "Feil under henting av person \uD83D\uDE21"
            console.error("Feil under henting av person: ", e)
            return JsonObject(emptyMap())
        }
    }
}