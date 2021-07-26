package modell

import AktivitetDTO
import AktivitetsloggDTO
import KontekstDTO
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import components.Grid
import kotlinx.datetime.LocalDateTime
import modell.Aktivitet.Companion.grupperEtterHendelse
import modell.Kontekst.Companion.hendelseskontekst
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLSelectElement

class Aktivitetslogg private constructor(
    private val aktiviteter: List<Aktivitet>
) {
    var visAktiviteter by mutableStateOf(false)

    private val filters = mapOf<String, (aktivitet: Aktivitet) -> Boolean> (
        "Alle" to { true },
        "BEHOV" to { it.alvorlighetsgrad == "BEHOV" },
        "INFO" to { it.alvorlighetsgrad == "INFO" || it.alvorlighetsgrad == "WARN" || it.alvorlighetsgrad == "ERROR" },
        "WARN" to { it.alvorlighetsgrad == "WARN" || it.alvorlighetsgrad == "ERROR" },
        "ERROR" to { it.alvorlighetsgrad == "ERROR" }
    )
    var filter = filters.values.first()
    var aktiviteterMedKontekster by mutableStateOf(aktiviteter())

    private fun aktiviteter() = aktiviteter.grupperEtterHendelse().mapValues { (_, values) -> values.filter(filter) }

    @Composable
    fun render() {
        Button(attrs = {

            onClick {
                visAktiviteter = !visAktiviteter
                if (!visAktiviteter) {
                    aktiviteterMedKontekster.keys.forEach { it.visAktiviteter = false }
                }
            }
        }) {
            Text(if (visAktiviteter) "Skjul hendelser" else "Vis hendelser")
        }
        Button(attrs = {
            onClick {
                visAktiviteter = true
                aktiviteterMedKontekster.keys.forEach { it.visAktiviteter = true }
            }
        }) {
            Text("Vis alle aktiviteter")
        }

        Select(attrs = {
            onChange {
                val selectedElement = it.nativeEvent.target as HTMLSelectElement
                filter = filters[selectedElement.value]!!
                aktiviteterMedKontekster = aktiviteter()
            }
        }) {
            Option("Alle") { Text("Alle") }
            Option("INFO") { Text("INFO") }
            Option("BEHOV") { Text("BEHOV") }
            Option("WARN") { Text("WARN") }
            Option("ERROR") { Text("ERROR") }
        }

        if (visAktiviteter) {
            Div {
                aktiviteterMedKontekster
                    .forEach { (kontekst, aktiviteter) ->
                    kontekst.render {
                        aktiviteter.forEach { it.render() }
                    }
                }
            }
        }
    }

    companion object {
        fun from(dto: AktivitetsloggDTO): Aktivitetslogg {
            val kontekster = dto.kontekster.map(Kontekst::from)
            return Aktivitetslogg(dto.aktiviteter.map { Aktivitet.from(it, kontekster) })
        }
    }

    fun subset(kontekstFilter: KontekstFilter): Aktivitetslogg {
        return Aktivitetslogg(aktiviteter.filter { it.validFor(kontekstFilter) })
    }
}

class Kontekst private constructor(
    private val kontekstType: String,
    private val kontekstMap: Map<String, String>,
) {
    override fun toString(): String = "$kontekstType = $kontekstMap"
    var visAktiviteter by mutableStateOf(false)

    @Composable
    fun render(children: @Composable () -> Unit) {
        Div {
            Button(attrs = {
                onClick {
                    visAktiviteter = !visAktiviteter
                }
            }) { Text("$kontekstType (${kontekstMap["meldingsreferanseId"]})") }
            Div(attrs = {
                style {
                    marginLeft(2.cssRem)
                }
            }) {
                if (visAktiviteter) {
                    Grid(3, style = {
                        width(70.percent)
                    }) {
                        children()
                    }
                }
            }
        }
    }

    fun validFor(kontekstFilter: KontekstFilter): Boolean {
        val (kontekstType, kontekstMap) = kontekstFilter.kontekst()
        return this.kontekstType == kontekstType && kontekstMap.all { (key, value) -> kontekstMap[key] == value }
    }

    companion object {
        fun from(dto: KontekstDTO): Kontekst = Kontekst(dto.kontekstType, dto.kontekstMap)

        fun List<Kontekst>.hendelseskontekst() = first { it.kontekstMap.containsKey("meldingsreferanseId") }
    }
}

class Aktivitet private constructor(
    private val melding: String,
    val alvorlighetsgrad: String,
    private val tidsstempel: LocalDateTime,
    private val kontekster: List<Kontekst>
) {
    override fun toString(): String = "[$tidsstempel] $alvorlighetsgrad: $melding"

    fun validFor(kontekstFilter: KontekstFilter) = kontekster.any { it.validFor(kontekstFilter) }

    @Composable
    fun LogElement(tekst: String) {
        P(attrs = {
            style {
                margin(0.px)
                fontSize(0.8.cssRem)
            }
        }) {
            Text(tekst)
        }
    }

    @Composable
    fun render() {
        LogElement(alvorlighetsgrad)
        LogElement(tidsstempel.toString())
        LogElement(melding)
    }

    companion object {
        fun from(dto: AktivitetDTO, kontekster: List<Kontekst>) =
            Aktivitet(dto.melding, dto.alvorlighetsgrad, dto.tidsstempel, dto.kontekster.map(kontekster::get))

        fun List<Aktivitet>.grupperEtterHendelse() = groupBy { it.kontekster.hendelseskontekst() }
    }
}

fun interface KontekstFilter {
    fun kontekst(): Pair<String, Map<String, String>>
}
