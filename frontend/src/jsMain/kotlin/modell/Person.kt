package modell

import PersonDTO
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

class Person private constructor(
    private val fødselsnummer: String,
    private val arbeidsgivere: List<Arbeidsgiver>,
    private val aktivitetslogg: Aktivitetslogg,
    private val mainView: MainView
) : NavigationView, DetaljView {
    var erAktiv by mutableStateOf(true)

    @Composable
    override fun renderNavigation() {
        Div(attrs = {
            onClick {
                mainView.setView(this@Person)
            }
        }) {
            Text(fødselsnummer)
            Button(attrs = {
                onClick {
                    erAktiv = !erAktiv
                }
            }) {
                Text(if (erAktiv) "-" else "+")
            }
        }
        if (erAktiv) arbeidsgivere.forEach { it.renderNavigation() }
    }

    @Composable
    override fun renderDetaljer() {
        aktivitetslogg.render()
    }

    companion object {
        fun from(dto: PersonDTO, mainView: MainView): Person {
            val aktivitetslogg = Aktivitetslogg.from(dto.aktivitetslogg)
            return Person(
                dto.fødselsnummer,
                dto.arbeidsgivere.map { Arbeidsgiver.from(it, aktivitetslogg, mainView) },
                aktivitetslogg,
                mainView
            )
        }
    }
}
