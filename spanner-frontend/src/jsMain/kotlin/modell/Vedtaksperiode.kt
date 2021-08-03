package modell

import VedtaksperiodeDTO
import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.JsonObject
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

class Vedtaksperiode private constructor(
    aktivitetslogg: Aktivitetslogg,
    private val vedtaksperiodeId: String,
    private val fom: LocalDate,
    private val tom: LocalDate,
    private val skjæringstidspunkt: LocalDate,
    private val tilstand: String,
    private val forkastet: Boolean,
    private val mainView: MainView,
    private val json: JsonObject
) : KontekstFilter, NavigationView, DetaljView, Comparable<Vedtaksperiode> {
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
                onClick {
                    mainView.setDetaljView(this@Vedtaksperiode)
                }
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

    @Composable
    override fun renderDetaljer() {
        aktivitetslogg.render()
    }

    override fun json() = json.toString()

    override fun kontekst(): Pair<String, Map<String, String>> {
        return "Vedtaksperiode" to mapOf(
            "vedtaksperiodeId" to vedtaksperiodeId
        )
    }

    override fun compareTo(other: Vedtaksperiode) = fom.compareTo(other.fom)

    companion object {
        fun from(dto: VedtaksperiodeDTO, json: JsonObject, mainView: MainView, aktivitetslogg: Aktivitetslogg, forkastet: Boolean) =
            Vedtaksperiode(
                aktivitetslogg = aktivitetslogg,
                vedtaksperiodeId = dto.id,
                fom = dto.fom,
                tom = dto.tom,
                skjæringstidspunkt = dto.skjæringstidspunkt,
                tilstand = dto.tilstand,
                forkastet = forkastet,
                json = json,
                mainView = mainView
            )
    }
}
