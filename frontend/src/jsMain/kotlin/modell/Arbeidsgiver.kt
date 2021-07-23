package modell

import ArbeidsgiverDTO
import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


class Arbeidsgiver private constructor(
    private val orgnummer: String,
    private val vedtaksperioder: List<Vedtaksperiode>,
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
                Text(orgnummer)
            }

            aktivitetslogg.render()

            vedtaksperioder.forEach { it.render() }
        }
    }

    override fun kontekst(): Pair<String, Map<String, String>> {
        return "Arbeidsgiver" to mapOf(
            "organisasjonsnummer" to orgnummer
        )
    }

    companion object {
        fun from(dto: ArbeidsgiverDTO, aktivitetslogg: Aktivitetslogg) =
            Arbeidsgiver(
                dto.organisasjonsnummer,
                dto.vedtaksperioder.map { Vedtaksperiode.from(it, aktivitetslogg) },
                aktivitetslogg
            )
    }
}
