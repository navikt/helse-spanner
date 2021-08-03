import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.json.simple.JSONObject

internal interface IRestClient {
    suspend fun hentPersonMedFnr(fnr: String, accessToken: AccessToken): JSONObject
    suspend fun hentPersonMedAktørId(aktørId: String, accessToken: AccessToken): JSONObject

    companion object {
        fun restClient(httpClient: HttpClient, clientId: String, isLocal: Boolean) =
            if (isLocal) LocalRestClient
            else RestClient(httpClient, clientId)
    }
}

internal object LocalRestClient : IRestClient {
    override suspend fun hentPersonMedFnr(fnr: String, accessToken: AccessToken) = finnPerson(fnr)

    override suspend fun hentPersonMedAktørId(aktørId: String, accessToken: AccessToken) = finnPerson(aktørId)

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

internal class RestClient(
    private val httpClient: HttpClient,
    private val spleisClientId: String
) : IRestClient {
    override suspend fun hentPersonMedFnr(fnr: String, accessToken: AccessToken): JSONObject {
        return hentPerson(mapOf("fnr" to fnr), accessToken)
    }

    override suspend fun hentPersonMedAktørId(aktørId: String, accessToken: AccessToken): JSONObject {
        return hentPerson(mapOf("aktørId" to aktørId), accessToken)
    }

    private suspend fun hentPerson(headers: Map<String, String>, accessToken: AccessToken): JSONObject {
        return httpClient.get<HttpStatement>("http://spleis-api.tbd.svc.nais.local/api/person-jason") {
            header("Authorization", "Bearer $accessToken")
            headers.forEach { header(it.key, it.value) }
            accept(ContentType.Application.Json)
        }.receive()
    }
}
