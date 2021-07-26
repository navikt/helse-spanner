import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal interface RestClient {
    suspend fun hentPersonMedFnr(fnr: String): String
    suspend fun hentPersonMedAktørId(aktørId: String): String
}

internal class ProdRestClient(
    private val httpClient: HttpClient,
    private val accessTokenClient: AccessTokenClient,
    private val spleisClientId: String,
    private val retryInterval: Long = 5000L
): RestClient {
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
