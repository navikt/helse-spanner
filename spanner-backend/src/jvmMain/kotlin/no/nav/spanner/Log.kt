package no.nav.spanner

import io.ktor.application.*
import io.ktor.client.statement.*
import io.ktor.request.*
import no.nav.spanner.Log.Companion.LogLevel.*
import org.slf4j.LoggerFactory

data class Log(
    val loggerName: String? = null,
    val loggerClass: Class<out Any>? = null,
    val context: Map<String, String> = mapOf(),
    val sensitivContext: Map<String, String> = mapOf(),
) {
    private fun context(key: String, value: String, sensitivt: Boolean = false): Log {
        val pair = key to value
        val context = if (!sensitivt) context + pair else context
        val sensitivContext = sensitivContext + pair
        return copy(context = context, sensitivContext = sensitivContext)
    }

    fun åpent(key: String, value: Any?) = context(key, value.toString(), false)
    fun sensitivt(key: String, value: Any?) = context(key, value.toString(), true)


    fun call(call: ApplicationCall): Log {
        return åpent("httpMethod", call.request.httpMethod)
            .åpent("httpPath", call.request.path())
            .sensitivt("httpUrl", call.request.uri)
    }

    fun response(response: HttpResponse): Log {
        return åpent("requestMethod", response.request.method)
            .åpent("requestPath", response.request.url.encodedPath)
            .sensitivt("requestUrl", response.request.url)
            .åpent("responseStatus", response.status)
            .åpent("responseBodySize", response.content.availableForRead)

    }

    private fun doLog(level: LogLevel, message: String?) {
        val pair = "class" to (loggerName ?: loggerClass!!.toString())
        val applikasjonslLog = loggerName?.let { LoggerFactory.getLogger(it) } ?: LoggerFactory.getLogger(loggerClass!!)
        val messageStr = "${message?.let { "$message, " } ?: ""}, "
        val tjenestekall = LoggerFactory.getLogger("tjenestekall")
        val applikasjonMessage = "$messageStr$context"
        val tjenestekallMessage = "$messageStr${sensitivContext + pair}"
        when (level) {
            INFO -> {
                applikasjonslLog.info(applikasjonMessage)
                tjenestekall.info(tjenestekallMessage)
            }
            WARN -> {
                applikasjonslLog.warn(applikasjonMessage)
                tjenestekall.warn(tjenestekallMessage)
            }
            ERROR -> {
                applikasjonslLog.error(applikasjonMessage)
                tjenestekall.error(tjenestekallMessage)
            }
        }
    }

    fun info(message: String? = null) {
        doLog(INFO, message)
    }

    fun warn(message: String? = null) {
        doLog(WARN, message)
    }

    fun error(message: String? = null) {
        doLog(ERROR, message)
    }


    companion object {
        fun logger(loggerName: String) =
            Log(loggerName = loggerName)

        fun logger(loggingClass: Class<out Any>) =
            Log(loggerClass = loggingClass)

        private enum class LogLevel { INFO, WARN, ERROR }
    }
}
