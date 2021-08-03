package modell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import externals.JSONFormatter
import kotlinx.dom.clear
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

class MainView() {
    private var detaljView by mutableStateOf<DetaljView?>(null)
    private var detaljerRenderer by mutableStateOf<@Composable () -> Unit>({ detaljView?.renderDetaljer() })

    @Composable
    fun render(personView: NavigationView?) {
        if (personView == null) return

        return Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Row)
                property("box-sizing", "border-box")
                height(100.percent)
                marginTop(2.cssRem)
            }
        }) {
            Container {
                personView.renderNavigation()
            }
            Container {
                Div(attrs = {
                    style {
                        display(DisplayStyle.Flex)
                        flexDirection(FlexDirection.Row)
                    }
                }) {
                    Button(attrs = {
                        onClick {
                            detaljerRenderer = { detaljView?.renderDetaljer() }
                        }
                    }) {
                        Text("Detaljer")
                    }
                    Button(attrs = {
                        onClick {
                            detaljerRenderer = { jsonContainer() }
                        }
                    }) {
                        Text("JSON")
                    }
                }
                detaljerRenderer()
            }
        }
    }

    @Composable
    private fun jsonContainer() {
        Div {
            detaljView?.json()?.let {
                jsonRenderer(JSONFormatter(js("JSON.parse(it)")))
            }
        }
    }

    @Composable
    private fun ElementScope<HTMLElement>.jsonRenderer(jsonFormatter: JSONFormatter) {
        DomSideEffect { htmlElement ->
            htmlElement.clear()
            htmlElement.appendChild(jsonFormatter.render())
        }
    }

    @Composable
    private fun Container(children: @Composable () -> Unit) {
        Div(attrs = {
            style {
                width(50.percent)
                property("height", "fit-content")
                property("max-height", 100.percent)
                property("overflow-y", "auto")
                border(1.px, LineStyle.Solid, Color("black"))
            }
        }) {
            children()
        }
    }

    fun setDetaljView(newView: DetaljView) {
        detaljView = newView
    }
}
