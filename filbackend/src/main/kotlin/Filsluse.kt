import java.io.File
import java.util.UUID

object Filsluse {
    private fun String.innhold() = File(this).takeIf { it.exists() }?.readText()
    private fun spannerRoot() = "${System.getenv("HOME")}/.spanner"
    fun UUID.finnPerson() = "${spannerRoot()}/personer/$this.json".innhold()
    fun UUID.finnHendelse() = "${spannerRoot()}/hendelser/$this.json".innhold()
}