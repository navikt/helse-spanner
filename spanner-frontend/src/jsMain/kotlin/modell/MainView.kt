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
    private var view by mutableStateOf<DetaljView?>(null)
    private var detaljerRenderer by mutableStateOf<@Composable () -> Unit>({ view?.renderDetaljer() })

    @Composable
    fun render(root: NavigationView) {
        Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Row)
                property("box-sizing", "border-box")
                height(100.percent)
                padding(2.cssRem)
            }
        }) {
            Container {
                root.renderNavigation()
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
                            detaljerRenderer = { view?.renderDetaljer() }
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
            view?.json()?.let {
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

    fun setView(newView: DetaljView) {
        view = newView
    }
}