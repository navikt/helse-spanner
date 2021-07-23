package modell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

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
        Div()
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