import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

object PersonParser {
    internal val parser = Json {
        ignoreUnknownKeys = true
    }

    fun person(json: JsonObject) = parser.decodeFromJsonElement<PersonDTO>(json) to json
}

@Serializable
data class PersonDTO(
    val fødselsnummer: String,
    val aktivitetslogg: AktivitetsloggDTO,
    val arbeidsgivere: List<JsonObject>
) {
    fun arbeidsgivereMap() = this.arbeidsgivere.map { PersonParser.parser.decodeFromJsonElement<ArbeidsgiverDTO>(it) to it }
}

@Serializable
data class ArbeidsgiverDTO(
    val organisasjonsnummer: String,
    val vedtaksperioder: List<JsonObject>,
    val forkastede: List<JsonObject>
) {
    fun vedtaksperioderMap() = this.vedtaksperioder.map { PersonParser.parser.decodeFromJsonElement<VedtaksperiodeDTO>(it) to it }
    fun forkastedeMap() = this.forkastede.map { PersonParser.parser.decodeFromJsonElement<ForkastetVedtaksperiodeDTO>(it) to it }
}

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
