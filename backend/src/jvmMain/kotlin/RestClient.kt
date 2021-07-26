import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class RestClient(
    private val httpClient: HttpClient,
    private val accessTokenClient: AccessTokenClient,
    private val spleisClientId: String,
    private val retryInterval: Long = 5000L
) {
    internal suspend fun hentPersonMedFnr(fnr: String): String {
        return hentPerson(mapOf("fnr" to fnr))
    }

    internal suspend fun hentPersonMedAktørId(aktørId: String): String {
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
