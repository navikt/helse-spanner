package modell

import VedtaksperiodeDTO
import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

class Vedtaksperiode private constructor(
    aktivitetslogg: Aktivitetslogg,
    private val vedtaksperiodeId: String,
    private val fom: LocalDate,
    private val tom: LocalDate,
    private val skjæringstidspunkt: LocalDate,
    private val tilstand: String,
    private val forkastet: Boolean
) : KontekstFilter, NavigationView, Comparable<Vedtaksperiode> {
    val aktivitetslogg = aktivitetslogg.subset(this)

    @Composable
    override fun renderNavigation() {
        Div(attrs = {
            style {
                border(1.px, LineStyle.Solid, Color("black"))
                padding(0.5.cssRem)
            }
        }) {
            Div(attrs = {
                style {
                    display(DisplayStyle.Flex)
                    flexDirection(FlexDirection.Row)
                    fontSize(0.8.cssRem)
                }
            }) {
                Text("Periode: $fom - $tom")
                Separator()
                Text("Skjæringstidspunkt: $skjæringstidspunkt")
                Separator()
                Text(vedtaksperiodeId)
                Separator()
                Text(tilstand)
                if (forkastet) {
                    Separator()
                    Text("forkastet")
                }
            }
        }
    }

    @Composable
    private fun Separator() {
        Div(attrs = {
            style {
                height(auto)
                width(1.px)
                property("margin", "0 1rem")
                backgroundColor("black")
            }
        })
    }

    override fun kontekst(): Pair<String, Map<String, String>> {
        return "Vedtaksperiode" to mapOf(
            "vedtaksperiodeId" to vedtaksperiodeId
        )
    }

    companion object {
        fun from(dto: VedtaksperiodeDTO, aktivitetslogg: Aktivitetslogg, forkastet: Boolean) =
            Vedtaksperiode(
                aktivitetslogg = aktivitetslogg,
                vedtaksperiodeId = dto.id,
                fom = dto.fom,
                tom = dto.tom,
                skjæringstidspunkt = dto.skjæringstidspunkt,
                tilstand = dto.tilstand,
                forkastet = forkastet
            )
    }

    override fun compareTo(other: Vedtaksperiode) = fom.compareTo(other.fom)
}
