package modell

import ArbeidsgiverDTO
import ForkastetVedtaksperiodeDTO
import VedtaksperiodeDTO
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.json.JsonObject
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


class Arbeidsgiver private constructor(
    private val orgnummer: String,
    private val vedtaksperioder: List<Vedtaksperiode>,
    private val mainView: MainView,
    aktivitetslogg: Aktivitetslogg,
    private val json: JsonObject
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

    @Composable
    override fun renderDetaljer() {
        aktivitetslogg.render()
    }

    override fun json(): String {
        return json.toString()
    }

    override fun kontekst(): Pair<String, Map<String, String>> {
        return "Arbeidsgiver" to mapOf(
            "organisasjonsnummer" to orgnummer
        )
    }

    companion object {
        fun from(dto: ArbeidsgiverDTO, json: JsonObject, aktivitetslogg: Aktivitetslogg, mainView: MainView): Arbeidsgiver {
            val vedtaksperioder = dto.vedtaksperioderMap()
            val forkastede = dto.forkastedeMap()
            return Arbeidsgiver(
                orgnummer = dto.organisasjonsnummer,
                vedtaksperioder = tilVedtaksperioder(vedtaksperioder, forkastede, mainView, aktivitetslogg),
                mainView = mainView,
                aktivitetslogg = aktivitetslogg,
                json = json
            )
        }

        private fun tilVedtaksperioder(
            vedtaksperioder: List<Pair<VedtaksperiodeDTO, JsonObject>>,
            forkastede: List<Pair<ForkastetVedtaksperiodeDTO, JsonObject>>,
            mainView: MainView,
            aktivitetslogg: Aktivitetslogg
        ): List<Vedtaksperiode> {
            val vedtaksperioder = vedtaksperioder.map { (dto, json) ->
                Vedtaksperiode.from(dto, json, mainView, aktivitetslogg, false)
            }
            val forkastede = forkastede.map { (dto, json) ->
                Vedtaksperiode.from(dto.vedtaksperiode, json, mainView, aktivitetslogg, true)
            }
            return (vedtaksperioder + forkastede).sorted()
        }
    }
}
