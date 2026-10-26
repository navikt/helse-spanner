import java.io.File
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

sealed interface Lagringsmedium {
    fun lagreHendelse(
        uuid: UUID,
        hendelse: String,
    ): Boolean

    fun lagrePerson(
        personPseudoId: UUID,
        person: String,
    ): Boolean
}

interface Hentingsmedium {
    fun hentPerson(personPseudoId: UUID): String?

    fun hentHendelse(uuid: UUID): String?
}

object Fil : Hentingsmedium {
    private fun String.innhold() = File(this).takeIf { it.exists() }?.readText()

    private fun spannerRoot() = System.getenv("SPANNER_ROOT").takeUnless { it.isNullOrBlank() } ?: "${System.getenv("HOME")}/.spanner"

    override fun hentPerson(personPseudoId: UUID) = "${spannerRoot()}/personer/$personPseudoId.json".innhold()

    override fun hentHendelse(uuid: UUID) = "${spannerRoot()}/hendelser/$this.json".innhold()
}

object Minne : Hentingsmedium, Lagringsmedium {
    private data class Entry(
        val data: String,
        val timeout: LocalDateTime = LocalDateTime.now().plusWeeks(1),
    )

    private val personer = ConcurrentHashMap<UUID, Entry>()
    private val hendelser = ConcurrentHashMap<UUID, Entry>()

    private fun ConcurrentHashMap<UUID, Entry>.rydd(): String? {
        val nå = LocalDateTime.now()
        entries.removeIf { (_, entry) -> entry.timeout < nå }
        return null
    }

    override fun hentPerson(personPseudoId: UUID): String? {
        val person = personer[personPseudoId]?.data ?: return personer.rydd()
        personer.rydd()
        personer[personPseudoId] = Entry(person) // Oppdaterer timeouten
        return person
    }

    override fun hentHendelse(uuid: UUID): String? {
        val hendelse = hendelser[uuid]?.data ?: return hendelser.rydd()
        hendelser.rydd()
        hendelser[uuid] = Entry(hendelse) // Oppdaterer timeouten
        return hendelse
    }

    override fun lagreHendelse(
        uuid: UUID,
        hendelse: String,
    ): Boolean {
        hendelser.rydd()
        hendelser[uuid] = Entry(hendelse)
        return true
    }

    override fun lagrePerson(
        personPseudoId: UUID,
        person: String,
    ): Boolean {
        personer.rydd()
        personer[personPseudoId] = Entry(person)
        return true
    }
}

internal fun List<Hentingsmedium>.hentPerson(personPseudoId: UUID) = firstNotNullOfOrNull { it.hentPerson(personPseudoId) }

internal fun List<Hentingsmedium>.hentHendelse(uuid: UUID) = firstNotNullOfOrNull { it.hentHendelse(uuid) }

internal fun List<Lagringsmedium>.lagrePerson(
    personPseudoId: UUID,
    person: String,
) {
    firstOrNull { it.lagrePerson(personPseudoId, person) } ?: error("Ingen lagringsmedium lagret personen.")
}

internal fun List<Lagringsmedium>.lagreHendelse(
    uuid: UUID,
    hendelse: String,
) {
    firstOrNull { it.lagreHendelse(uuid, hendelse) } ?: error("Ingen lagringsmedium lagret hendelsen.")
}
