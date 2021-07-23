import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

object Testerino {
    private val parser = Json {
        ignoreUnknownKeys = true
    }

    fun person(): PersonDTO = parser.decodeFromString(Data.json)
}

@Serializable
data class PersonDTO(
    val fødselsnummer: String,
    val aktivitetslogg: AktivitetsloggDTO,
    val arbeidsgivere: List<ArbeidsgiverDTO>
)

@Serializable
data class ArbeidsgiverDTO(
    val organisasjonsnummer: String,
    val vedtaksperioder: List<VedtaksperiodeDTO>
)

@Serializable
data class VedtaksperiodeDTO(
    val id: String
)

@Serializable
data class AktivitetsloggDTO(
    val aktiviteter: List<AktivitetDTO>,
    val kontekster: List<KontekstDTO>
)

@Serializable
data class AktivitetDTO(
    val melding: String,
    val alvorlighetsgrad: String,
    val tidsstempel: LocalDateTime,
    val kontekster: List<Int>
)

@Serializable
data class KontekstDTO(
    val kontekstType: String,
    val kontekstMap: Map<String, String>
)
