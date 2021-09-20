package no.nav.spanner

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.natpryce.konfig.Configuration
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class AzureADConfig(
    val discoveryUrl: String,
    val clientId: String,
    val clientSecret: String,
    val spleisClientId: String,
    authorizationUrl: String? = null
) {
    private val discovered = discoveryUrl.discover()
    val tokenEndpoint = discovered["token_endpoint"]?.textValue()
        ?: throw RuntimeException("Unable to discover token endpoint")
    val authorizationEndpoint = authorizationUrl ?: discovered["authorization_endpoint"]?.textValue()
    ?: throw RuntimeException("Unable to discover authorization endpoint")

    companion object {
        fun fromEnv(config: Configuration) = AzureADConfig(
            discoveryUrl = config[Key("AZURE_APP_WELL_KNOWN_URL", stringType)],
            clientId = config[Key("AZURE_APP_CLIENT_ID", stringType)],
            clientSecret = config[Key("AZURE_APP_CLIENT_SECRET", stringType)],
            spleisClientId = config[Key("SPLEIS_CLIENT_ID", stringType)],
            authorizationUrl = config.getOrNull(Key("AUTHORIZATION_URL", stringType)),
        )
    }
}

private fun String.discover(): JsonNode {
    val (responseCode, responseBody) = this.fetchUrl()
    if (responseCode >= 300 || responseBody == null) throw IOException("got status $responseCode from ${this}.")
    return jacksonObjectMapper().readTree(responseBody)
}

private fun String.fetchUrl() = with(URL(this).openConnection() as HttpURLConnection) {
    requestMethod = "GET"
    val stream: InputStream? = if (responseCode < 300) this.inputStream else this.errorStream
    responseCode to stream?.bufferedReader()?.readText()
}