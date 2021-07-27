import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal interface IRestClient {
    suspend fun hentPersonMedFnr(fnr: String, accessToken: String): String
    suspend fun hentPersonMedAktørId(aktørId: String, accessToken: String): String

    companion object {
        fun restClient(
            httpClient: HttpClient,
            env: Map<String, String>,
            isLocal: Boolean
        ) =
            if (isLocal) LocalRestClient
            else RestClient(httpClient, env.getValue("SPLEIS_CLIENT_ID"))
    }
}

internal object LocalRestClient: IRestClient {
    override suspend fun hentPersonMedFnr(fnr: String, accessToken: String) = finnPerson(fnr)

    override suspend fun hentPersonMedAktørId(aktørId: String, accessToken: String) = finnPerson(aktørId)

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
    override suspend fun hentPersonMedFnr(fnr: String, accessToken: String): String {
        return hentPerson(mapOf("fnr" to fnr), accessToken)
    }

    override suspend fun hentPersonMedAktørId(aktørId: String, accessToken: String): String {
        return hentPerson(mapOf("aktørId" to aktørId), accessToken)
    }

    private suspend fun hentPerson(headers: Map<String, String>, accessToken: String): String {
        return httpClient.get<HttpStatement>("http://spleis-api.tbd.svc.nais.local/api/person-jason") {
            header("Authorization", "Bearer $accessToken")
            headers.forEach { header(it.key, it.value) }
            accept(ContentType.Application.Json)
        }.receive()
    }
}
