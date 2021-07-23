package modell

import VedtaksperiodeDTO
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

class Vedtaksperiode private constructor(
    private val vedtaksperiodeId: String,
    aktivitetslogg: Aktivitetslogg
) : KontekstFilter, Renderable {
    val aktivitetslogg = aktivitetslogg.subset(this)
    @Composable
    override fun render(children: @Composable () -> Unit) {
        Div(attrs = {
            style {
                border(1.px, LineStyle.Solid, Color("black"))
            }
        }) {
            Div {
                Text(vedtaksperiodeId)
            }

            aktivitetslogg.render()
        }
    }

    override fun kontekst(): Pair<String, Map<String, String>> {
        return "Vedtaksperiode" to mapOf(
            "vedtaksperiodeId" to vedtaksperiodeId
        )
    }

    companion object {
        fun from(dto: VedtaksperiodeDTO, aktivitetslogg: Aktivitetslogg) =
            Vedtaksperiode(
                dto.id,
                aktivitetslogg
            )
    }
}
