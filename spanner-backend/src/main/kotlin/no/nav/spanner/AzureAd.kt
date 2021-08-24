package no.nav.spanner

import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AzureAD(private val config: AzureADConfig) {
    private val httpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer {
                registerModule(JavaTimeModule())
            }
        }
    }

    suspend fun hentOnBehalfOfToken(accessToken: DecodedJWT): String {
        val requestBody = listOf(
            "grant_type" to "urn:ietf:params:oauth:grant-type:jwt-bearer",
            "assertion" to accessToken.token,
            "assertion_type" to "urn:ietf:params:oauth:client-assertion-type:jwt-bearer",
            "requested_token_use" to "on_behalf_of"
        ).let { createOnBehalfOfRequestBody(it) }.formUrlEncode()
        val response = httpClient.post<HttpResponse>(config.tokenEndpoint) {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.FormUrlEncoded)
            body = requestBody
        }
        Log.logger(AzureAD::class.java)
            .response(response)
            .info("Retreiving on behalf of token")
        return response
            .receive<JsonNode>()
            .path("access_token")
            .takeUnless(JsonNode::isMissingOrNull)
            ?.asText()!!
    }

    private fun createOnBehalfOfRequestBody(list: List<Pair<String, String>>) =
        createTokenRequestBody(list, config.spleisClientId)

    private fun createTokenRequestBody(list: List<Pair<String, String>>, scope: String) =
        list.toMutableList().apply {
            add("client_id" to config.clientId)
            add("client_secret" to config.clientSecret)
            add("scope" to scope)
        }.toList()

}

