package modell

import PersonDTO
import androidx.compose.runtime.Composable

class Person private constructor(
    private val fødselsnummer: String,
    private val arbeidsgivere: List<Arbeidsgiver>,
    private val aktivitetslogg: Aktivitetslogg
) : Renderable {
    @Composable
    override fun render(children: @Composable () -> Unit) {
        aktivitetslogg.render()
        arbeidsgivere.forEach { it.render() }
    }

    companion object {
        fun from(dto: PersonDTO): Person {
            val aktivitetslogg = Aktivitetslogg.from(dto.aktivitetslogg)
            return Person(
                dto.fødselsnummer,
                dto.arbeidsgivere.map { Arbeidsgiver.from(it, aktivitetslogg) },
                aktivitetslogg
            )
        }
    }
}
