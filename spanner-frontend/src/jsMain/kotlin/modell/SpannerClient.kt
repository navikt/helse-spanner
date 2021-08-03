package modell

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject


class SpannerClient {
    private val host = Localhost()
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun hentPersonMedFnr(fnr: String = "12020052345"): JsonObject {
        return client.request("$host/api/person-fnr") {
            method = HttpMethod.Get
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("fnr", fnr)
        }
    }

    suspend fun hentPersonMedAktørId(aktørId: String = "42"): JsonObject {
        return client.request("$host/api/person-aktorid") {
            method = HttpMethod.Get
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("aktorId", aktørId)
        }
    }
}


internal abstract class Host {
    abstract fun hostname(): String
    abstract fun port(): Int
    abstract fun protocol(): String
    override fun toString() = "${protocol()}://${this.toShortString()}"
    fun toShortString() = "${hostname()}:${port()}"
}

internal class Localhost(private val port: Int = 9000) : Host() {
    override fun hostname() = "localhost"
    override fun port() = port
    override fun protocol() = "http"
    override fun toString() = "${protocol()}://${hostname()}:${port()}"
}

internal class Deployed(private val env: Map<String, String>) : Host() {
    override fun hostname() = env.getValue("HOST")
    override fun port() = env.getValue("HTTP_PORT").toInt()
    override fun protocol() = "https"
}
