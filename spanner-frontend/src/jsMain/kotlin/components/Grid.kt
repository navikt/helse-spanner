package components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Composable
internal fun Grid(columns: Int, style: StyleBuilder.() -> Unit = {}, children: @Composable () -> Unit) {
    Div(attrs = {
        style {
            style()
            display(DisplayStyle.Grid)
            gridGap(0.5.cssRem)
            property("grid-template-columns", "80px 220px auto")
        }
    }) {
        children()
    }
}
