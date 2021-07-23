package modell

import ArbeidsgiverDTO
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


class Arbeidsgiver private constructor(
    private val orgnummer: String,
    private val vedtaksperioder: List<Vedtaksperiode>,
    private val mainView: MainView,
    aktivitetslogg: Aktivitetslogg
) : KontekstFilter, NavigationView, DetaljView {
    val aktivitetslogg = aktivitetslogg.subset(this)
    var erAktiv by mutableStateOf(true)

    @Composable
    override fun renderNavigation() {
        Div(attrs = {
            style {
                padding(0.5.cssRem)
            }
        }) {
            Div(attrs = {
                onClick {
                    mainView.setView(this@Arbeidsgiver)
                }
            }) {
                Text(orgnummer)
                Button(attrs = {
                    onClick {
                        erAktiv = !erAktiv
                    }
                }) {
                    Text(if (erAktiv) "-" else "+")
                }
            }

            if (erAktiv) vedtaksperioder.forEach { it.renderNavigation() }
        }
    }

    override fun kontekst(): Pair<String, Map<String, String>> {
        return "Arbeidsgiver" to mapOf(
            "organisasjonsnummer" to orgnummer
        )
    }

    companion object {
        fun from(dto: ArbeidsgiverDTO, aktivitetslogg: Aktivitetslogg, mainView: MainView) =
            Arbeidsgiver(
                dto.organisasjonsnummer,
(                dto.vedtaksperioder.map { Vedtaksperiode.from(it, aktivitetslogg, false) }
                        + dto.forkastede.map { Vedtaksperiode.from(it.vedtaksperiode, aktivitetslogg, true) }).sorted(),
                mainView,
                aktivitetslogg
            )
    }

    @Composable
    override fun renderDetaljer() {
        aktivitetslogg.render()
    }
}
