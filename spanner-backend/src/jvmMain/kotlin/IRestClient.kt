import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal interface IRestClient {
    suspend fun hentPersonMedFnr(fnr: String): String
    suspend fun hentPersonMedAktørId(aktørId: String): String

    companion object {
        fun restClient(
            httpClient: HttpClient,
            accessTokenClient: IAccessTokenClient,
            env: Map<String, String>,
            isLocal: Boolean
        ) =
            if (isLocal) LocalRestClient
            else RestClient(httpClient, accessTokenClient, env.getValue("SPLEIS_CLIENT_ID"))
    }
}

internal object LocalRestClient: IRestClient {
    override suspend fun hentPersonMedFnr(fnr: String) = finnPerson(fnr)

    override suspend fun hentPersonMedAktørId(aktørId: String) = finnPerson(aktørId)

    private fun finnPerson(id: String): String {
        return when (id) {
            "42",
            "12020052345" -> LocalRestClient::class.java.getResource("personer/$id.json")?.readText()
            else -> "{}"
        } ?: "{}"
    }

}

internal class RestClient(
    private val httpClient: HttpClient,
    private val accessTokenClient: IAccessTokenClient,
    private val spleisClientId: String
): IRestClient {
    override suspend fun hentPersonMedFnr(fnr: String): String {
        return hentPerson(mapOf("fnr" to fnr))
    }

    override suspend fun hentPersonMedAktørId(aktørId: String): String {
        return hentPerson(mapOf("aktørId" to aktørId))
    }

    private suspend fun hentPerson(headers: Map<String, String>): String {
        val accessToken = accessTokenClient.hentAccessToken(spleisClientId)
        return httpClient.get<HttpStatement>("http://spleis-api.tbd.svc.nais.local/api/person-jason") {
            header("Authorization", "Bearer $accessToken")
            headers.forEach { header(it.key, it.value) }
            accept(ContentType.Application.Json)
        }.receive()
    }
}
