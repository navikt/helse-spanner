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
    val exception: Throwable? = null,
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
        return åpent("httpMethod", call.request.httpMethod.value)
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

    fun exception(error: Throwable): Log {
        return copy(exception = error)
    }

    private fun doLog(level: LogLevel, message: String?) {
        val pair = "class" to (loggerName ?: loggerClass!!.toString())
        val applikasjonslLog = loggerName?.let { LoggerFactory.getLogger(it) } ?: LoggerFactory.getLogger(loggerClass!!)
        val messageStr = message?.let { "$message, " } ?: ""
        val tjenestekall = LoggerFactory.getLogger("tjenestekall")
        val applikasjonMessage = "$messageStr$context"
        val tjenestekallMessage = "$messageStr${sensitivContext + pair}"
        when (level) {
            INFO -> {
                exception?.let { applikasjonslLog.info(applikasjonMessage, exception) }
                    ?: applikasjonslLog.info(applikasjonMessage)
                exception?.let { tjenestekall.info(tjenestekallMessage, exception) }
                    ?: tjenestekall.info(tjenestekallMessage)
            }
            WARN -> {
                exception?.let { applikasjonslLog.warn(applikasjonMessage, exception) }
                    ?: applikasjonslLog.warn(applikasjonMessage)
                exception?.let { tjenestekall.warn(tjenestekallMessage, exception) }
                    ?: tjenestekall.warn(tjenestekallMessage)
            }
            ERROR -> {
                exception?.let { applikasjonslLog.error(applikasjonMessage, exception) }
                    ?: applikasjonslLog.error(applikasjonMessage)
                exception?.let { tjenestekall.error(tjenestekallMessage, exception) }
                    ?: tjenestekall.error(tjenestekallMessage)
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

    fun log(level: LogLevel, message: String? = null) {
        doLog(level, message)
    }


    companion object {
        fun logger(loggerName: String) =
            Log(loggerName = loggerName)

        fun logger(loggingClass: Class<out Any>) =
            Log(loggerClass = loggingClass)

        enum class LogLevel { INFO, WARN, ERROR }
    }
}
