import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.ZoneId
import java.util.*

internal val objectMapper = jacksonObjectMapper()
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .registerModule(JavaTimeModule())
    .setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
        indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
        indentObjectsWith(DefaultIndenter("  ", "\n"))
    })

internal fun String.getJson(): JsonNode {
    val (responseCode, responseBody) = this.fetchUrl()
    if (responseCode >= 300 || responseBody == null) throw RuntimeException("got status $responseCode from ${this}.")
    return jacksonObjectMapper().readTree(responseBody)
}

internal fun String.fetchUrl() = with(URL(this).openConnection() as HttpURLConnection) {
    requestMethod = "GET"
    val stream: InputStream? = if (responseCode < 300) this.inputStream else this.errorStream
    responseCode to stream?.bufferedReader()?.readText()
}

internal fun Date.toLocalDateTime() = toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

internal fun JsonNode.isMissingOrNull() = isMissingNode || isNull
