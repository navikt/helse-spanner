package components

import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.StyleBuilder

fun StyleBuilder.gridGap(value: CSSNumeric) {
    property("grid-gap", value)
}
