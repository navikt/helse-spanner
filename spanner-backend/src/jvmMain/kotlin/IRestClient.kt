import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal interface IRestClient {
    suspend fun hentPersonMedFnr(fnr: String, accessToken: AccessToken): String
    suspend fun hentPersonMedAktørId(aktørId: String, accessToken: AccessToken): String

    companion object {
        fun restClient(httpClient: HttpClient, clientId: String, isLocal: Boolean) =
            if (isLocal) LocalRestClient
            else RestClient(httpClient, clientId)
    }
}

internal object LocalRestClient: IRestClient {
    override suspend fun hentPersonMedFnr(fnr: String, accessToken: AccessToken) = finnPerson(fnr)

    override suspend fun hentPersonMedAktørId(aktørId: String, accessToken: AccessToken) = finnPerson(aktørId)

    private fun finnPerson(id: String): String {
        return when (id) {
            "42",
            "12020052345" -> lesJson("12020052345")
            else -> "{}"
        } ?: "{}"
    }

    private fun lesJson(filnavn: String) =
        LocalRestClient::class.java.getResource("personer/$filnavn.json")?.readText()
}

internal class RestClient(
    private val httpClient: HttpClient,
    private val spleisClientId: String
): IRestClient {
    override suspend fun hentPersonMedFnr(fnr: String, accessToken: AccessToken): String {
        return hentPerson(mapOf("fnr" to fnr), accessToken)
    }

    override suspend fun hentPersonMedAktørId(aktørId: String, accessToken: AccessToken): String {
        return hentPerson(mapOf("aktørId" to aktørId), accessToken)
    }

    private suspend fun hentPerson(headers: Map<String, String>, accessToken: AccessToken): String {
        return httpClient.get<HttpStatement>("http://spleis-api.tbd.svc.nais.local/api/person-jason") {
            header("Authorization", "Bearer $accessToken")
            headers.forEach { header(it.key, it.value) }
            accept(ContentType.Application.Json)
        }.receive()
    }
}
