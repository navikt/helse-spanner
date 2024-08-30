import java.io.File
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

sealed interface Lagringsmedium {
    fun lagreHendelse(uuid: UUID, hendelse: String): Boolean
    fun lagrePerson(uuid: UUID, person: String): Boolean
}

interface Hentingsmedium {
    fun hentPerson(uuid: UUID): String?
    fun hentHendelse(uuid: UUID): String?
}

object Fil: Hentingsmedium {
    private fun String.innhold() = File(this).takeIf { it.exists() }?.readText()
    private fun spannerRoot() = System.getenv("SPANNER_ROOT").takeUnless { it.isNullOrBlank() } ?: "${System.getenv("HOME")}/.spanner"
    override fun hentPerson(uuid: UUID) = "${spannerRoot()}/personer/$uuid.json".innhold()
    override fun hentHendelse(uuid: UUID) = "${spannerRoot()}/hendelser/$this.json".innhold()
}

object Minne: Hentingsmedium, Lagringsmedium {
    private data class Entry(val data: String, val timeout: LocalDateTime = LocalDateTime.now().plusWeeks(1))

    private val personer = ConcurrentHashMap<UUID, Entry>()
    private val hendelser = ConcurrentHashMap<UUID, Entry>()

    private fun ConcurrentHashMap<UUID, Entry>.rydd(): String? {
        val nå = LocalDateTime.now()
        entries.removeIf { (_, entry) -> entry.timeout < nå }
        return null
    }

    override fun hentPerson(uuid: UUID): String? {
        val person = personer[uuid]?.data ?: return personer.rydd()
        personer.rydd()
        personer[uuid] = Entry(person) // Oppdaterer timeouten
        return person
    }

    override fun hentHendelse(uuid: UUID): String? {
        val hendelse = hendelser[uuid]?.data ?: return hendelser.rydd()
        hendelser.rydd()
        hendelser[uuid] = Entry(hendelse) // Oppdaterer timeouten
        return hendelse
    }

    override fun lagreHendelse(uuid: UUID, hendelse: String): Boolean {
        hendelser.rydd()
        hendelser[uuid] = Entry(hendelse)
        return true
    }

    override fun lagrePerson(uuid: UUID, person: String): Boolean {
        personer.rydd()
        personer[uuid] = Entry(person)
        return true
    }
}

internal fun List<Hentingsmedium>.hentPerson(uuid: UUID) = firstNotNullOfOrNull { it.hentPerson(uuid) }
internal fun List<Hentingsmedium>.hentHendelse(uuid: UUID) = firstNotNullOfOrNull { it.hentHendelse(uuid) }
internal fun List<Lagringsmedium>.lagrePerson(uuid: UUID, person: String) { firstOrNull { it.lagrePerson(uuid, person) } ?: error("Ingen lagringsmedium lagret personen.") }
internal fun List<Lagringsmedium>.lagreHendelse(uuid: UUID, hendelse: String) { firstOrNull { it.lagreHendelse(uuid, hendelse) } ?: error("Ingen lagringsmedium lagret hendelsen.") }
