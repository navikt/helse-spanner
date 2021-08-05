import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.json.simple.JSONObject

internal interface IRestClient {
    suspend fun hentPersonMedFnr(fnr: String, onBehalfOfToken: String): JSONObject
    suspend fun hentPersonMedAktørId(aktørId: String, onBehalfOfToken: String): JSONObject

    companion object {
        fun restClient(httpClient: HttpClient, isLocal: Boolean) =
            if (isLocal) LocalRestClient
            else RestClient(httpClient)
    }
}

internal object LocalRestClient : IRestClient {
    override suspend fun hentPersonMedFnr(fnr: String, onBehalfOfToken: String) = finnPerson(fnr)

    override suspend fun hentPersonMedAktørId(aktørId: String, onBehalfOfToken: String) = finnPerson(aktørId)

    private fun finnPerson(id: String): JSONObject {
        return when (id) {
            "42",
            "12020052345" -> lesJson("12020052345")
            else -> JSONObject()
        }
    }

    private fun lesJson(filnavn: String): JSONObject =
        objectMapper.readValue(
            LocalRestClient::class.java.getResource("personer/$filnavn.json")?.readText() ?: "{}"
        )
}

internal class RestClient(private val httpClient: HttpClient) : IRestClient {
    override suspend fun hentPersonMedFnr(fnr: String, onBehalfOfToken: String): JSONObject {
        return hentPerson(mapOf("fnr" to fnr), onBehalfOfToken)
    }

    override suspend fun hentPersonMedAktørId(aktørId: String, onBehalfOfToken: String): JSONObject {
        return hentPerson(mapOf("aktorId" to aktørId), onBehalfOfToken)
    }

    private suspend fun hentPerson(headers: Map<String, String>, onBehalfOfToken: String): JSONObject {
        return httpClient.get<HttpStatement>("http://spleis-api.tbd.svc.nais.local/api/person-json") {
            header("Authorization", "Bearer $onBehalfOfToken")
            headers.forEach { header(it.key, it.value) }
            accept(ContentType.Application.Json)
        }.receive()
    }
}
