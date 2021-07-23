import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

object PersonParser {
    private val parser = Json {
        ignoreUnknownKeys = true
    }

    val json = Data.json
    fun person(): PersonDTO = parser.decodeFromString(json)
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
    val vedtaksperioder: List<VedtaksperiodeDTO>,
    val forkastede: List<ForkastetVedtaksperiodeDTO>
)

@Serializable
data class ForkastetVedtaksperiodeDTO(
    val vedtaksperiode: VedtaksperiodeDTO
)

@Serializable
data class VedtaksperiodeDTO(
    val id: String,
    val fom: LocalDate,
    val tom: LocalDate,
    val skjæringstidspunkt: LocalDate,
    val tilstand: String
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
