internal abstract class Host {
    abstract fun hostname(): String
    abstract fun port(): Int
    abstract fun protocol(): String
}

internal class Localhost(private val port: Int = 9000): Host() {
    override fun hostname() = "localhost"
    override fun port() = port
    override fun protocol() = "http"
    override fun toString() = "${protocol()}://${hostname()}:${port()}"
}

internal class Deployed(private val env: Map<String, String>): Host() {
    override fun hostname() = env.getValue("HOST")
    override fun port() = env.getValue("HTTP_PORT").toInt()
    override fun protocol() = "https"
    override fun toString() = "${protocol()}://${hostname()}"
}