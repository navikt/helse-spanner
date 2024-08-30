import java.io.File
import java.util.UUID

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
    private val personer = mutableMapOf<UUID, String>()
    private val hendelser = mutableMapOf<UUID, String>()
    override fun hentPerson(uuid: UUID) = personer[uuid]
    override fun hentHendelse(uuid: UUID) = hendelser[uuid]
    override fun lagreHendelse(uuid: UUID, hendelse: String): Boolean {
        hendelser[uuid] = hendelse
        return true
    }
    override fun lagrePerson(uuid: UUID, person: String): Boolean {
        personer[uuid] = person
        return true
    }
}

internal fun List<Hentingsmedium>.hentPerson(uuid: UUID) = firstNotNullOfOrNull { it.hentPerson(uuid) }
internal fun List<Hentingsmedium>.hentHendelse(uuid: UUID) = firstNotNullOfOrNull { it.hentHendelse(uuid) }
internal fun List<Lagringsmedium>.lagrePerson(uuid: UUID, person: String) { firstOrNull { it.lagrePerson(uuid, person) } ?: error("Ingen lagringsmedium lagret personen.") }
internal fun List<Lagringsmedium>.lagreHendelse(uuid: UUID, hendelse: String) { firstOrNull { it.lagreHendelse(uuid, hendelse) } ?: error("Ingen lagringsmedium lagret hendelsen.") }