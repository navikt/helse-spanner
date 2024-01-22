package no.nav.spanner

import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.util.*

class AzureAD(private val config: AzureADConfig) {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson {
                registerModule(JavaTimeModule())
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun hentOnBehalfOfToken(accessToken: DecodedJWT, clientId: String): String {
        val requestBody = listOf(
            "grant_type" to "urn:ietf:params:oauth:grant-type:jwt-bearer",
            "assertion" to accessToken.token,
            "assertion_type" to "urn:ietf:params:oauth:client-assertion-type:jwt-bearer",
            "requested_token_use" to "on_behalf_of"
        ).let { createOnBehalfOfRequestBody(clientId, it) }.formUrlEncode()
        val response = httpClient.post(config.tokenEndpoint) {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.FormUrlEncoded)
            body = requestBody
        }
        Log.logger(AzureAD::class.java)
            .response(response)
            .info("OBO token retrieved")
        return response
            .body<JsonNode>()
            .path("access_token")
            .takeUnless(JsonNode::isMissingOrNull)
            ?.asText()!!
    }

    private fun createOnBehalfOfRequestBody(clientId: String, list: List<Pair<String, String>>) =
        createTokenRequestBody(list, clientId)

    private fun createTokenRequestBody(list: List<Pair<String, String>>, scope: String) =
        list.toMutableList().apply {
            add("client_id" to config.clientId)
            add("client_secret" to config.clientSecret)
            add("scope" to scope)
        }.toList()

}

