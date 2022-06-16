package no.nav.spanner

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import io.ktor.server.auth.Authentication
import io.ktor.server.plugins.forwardedheaders.XForwardedHeaders
import io.ktor.serialization.jackson.JacksonConverter
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.oauth
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.origin
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.header
import io.ktor.server.request.host
import io.ktor.server.request.path
import io.ktor.server.request.port
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.sessions.SessionSerializer
import io.ktor.server.sessions.SessionStorageMemory
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.util.pipeline.*
import no.nav.spanner.AuditLogger.Companion.audit
import no.nav.spanner.Log.Companion.LogLevel
import no.nav.spanner.Log.Companion.LogLevel.ERROR
import no.nav.spanner.Log.Companion.LogLevel.INFO
import org.slf4j.event.Level
import java.time.LocalDateTime
import java.util.*

enum class IdType(val header: String) {
    FNR("fnr"), AKTORID("aktorId")
}

private val logg = Log.logger("Spanner")

fun Application.spanner(spleis: Personer, config: AzureADConfig, development: Boolean) {

    install(CallId) {
        generate {
            UUID.randomUUID().toString()
        }
    }
    install(CallLogging) {
        level = Level.INFO
        filter { call -> !call.request.path().startsWith("/internal") }
    }
    install(XForwardedHeaders)

    install(StatusPages) {
        suspend fun respondToException(
            status: HttpStatusCode,
            call: ApplicationCall,
            cause: Throwable,
            level: LogLevel
        ) {
            val errorId = UUID.randomUUID()
            logg
                .åpent("errorId", errorId)
                .åpent("status", status)
                .call(call)
                .exception(cause)
                .log(level)
            call.respond(status, FeilRespons(errorId.toString(), cause.message))
        }
        exception<NotFoundException> { call, cause ->
            respondToException(HttpStatusCode.NotFound, call, cause, INFO)
        }
        exception<BadRequestException> { call, cause ->
            respondToException(HttpStatusCode.BadRequest, call, cause, INFO)
        }
        exception<InvalidSession> { call, cause ->
            call.sessions.clear<SpannerSession>()
            respondToException(HttpStatusCode.Unauthorized, call, cause, INFO)
        }
        exception<Throwable> { call, cause ->
            respondToException(HttpStatusCode.InternalServerError, call, cause, ERROR)
        }
    }

    install(ContentNegotiation) { register(ContentType.Application.Json, JacksonConverter(objectMapper)) }
    install(Sessions) {
        cookie<SpannerSession>("spanner", storage = SessionStorageMemory()) {
            this.cookie.secure = !development
            cookie.extensions["SameSite"] = "strict"
            serializer = object : SessionSerializer<SpannerSession> {
                override fun deserialize(text: String): SpannerSession = objectMapper.readValue(text)
                override fun serialize(session: SpannerSession) = objectMapper.writeValueAsString(session)
            }
        }
    }

    val httpClient = HttpClient(CIO) {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            register(ContentType.Application.Json, JacksonConverter(ObjectMapper().registerModule(JavaTimeModule())))
        }
    }

    install(Authentication) {
        oauth("oauth") {
            urlProvider = { redirectUrl("/oauth2/callback", development) }
            skipWhen { call -> call.sessions.get<SpannerSession>() != null }

            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "AAD",
                    authorizeUrl = config.authorizationEndpoint,
                    accessTokenUrl = config.tokenEndpoint,
                    requestMethod = HttpMethod.Post,
                    clientId = config.clientId,
                    clientSecret = config.clientSecret,
                    defaultScopes = listOf("openid", "${config.clientId}/.default")
                )
            }
            client = httpClient
        }
    }

    naisApi()

    routing {
        authenticate("oauth") {
            frontendRouting()
            oidc()
            get("/api/person/") {
                checkIfsessionValid()
                sesjon().audit().log(call)
                val (idType, idValue) = call.personId()
                logg
                    .åpent("idType", idType)
                    .sensitivt("idValue", idValue)
                    .call(this.call)
                    .info()
                if (idValue.isNullOrBlank()) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "${IdType.AKTORID.header} or ${IdType.FNR.header} must be set"
                    )
                } else {
                    val accessToken = call.sessions.get<SpannerSession>()?.accessToken.toString()
                    val person = spleis.person(idValue, idType, accessToken)
                    call.respondText(person, ContentType.Application.Json, HttpStatusCode.OK)
                }
            }

            get("/api/hendelse/{meldingsreferanse}") {
                checkIfsessionValid()
                sesjon().audit().log(call)
                val meldingsreferanse =
                    call.parameters["meldingsreferanse"] ?: throw BadRequestException("Mangler meldingsreferanse")
                logg
                    .åpent("meldingsreferanse", meldingsreferanse)
                    .call(this.call)
                    .info()

                val accessToken = call.sessions.get<SpannerSession>()?.accessToken.toString()
                val hendelse = spleis.hendelse(meldingsreferanse, accessToken)
                call.respondText(hendelse, ContentType.Application.Json, HttpStatusCode.OK)
            }
        }
    }
}

private fun ApplicationCall.personId() =
    request.headers[IdType.FNR.header]?.let {
        Pair(IdType.FNR, request.header(IdType.FNR.header))
    } ?: Pair(IdType.AKTORID, request.header(IdType.AKTORID.header))


private fun ApplicationCall.redirectUrl(path: String, development: Boolean): String {
    val protocol = if (!development) "https" else request.origin.scheme
    val defaultPort = if (protocol == "http") 80 else 443
    val host = if (!development) request.host() else "localhost"

    val hostPort = host + request.port().let { port ->
        if (port == defaultPort || !development) "" else ":$port"
    }
    return "$protocol://$hostPort$path"
}

private fun PipelineContext<Unit, ApplicationCall>.checkIfsessionValid() {
    if (sesjon().validBefore < LocalDateTime.now()) {
        throw InvalidSession("access token utgått: validBefore=${sesjon().validBefore} now=${LocalDateTime.now()}")
    }
}

class InvalidSession(message: String) : RuntimeException(message)