package no.nav.spanner

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.slf4j.event.Level
import java.util.*

enum class IdType(val header: String) {
    FNR("fnr"), AKTORID("aktorId")
}

enum class EnvType {
    LOCAL, PROD;

    companion object {
        fun fromString(env: String) = when (env) {
            "prod" -> PROD
            "local" -> LOCAL
            else -> throw IllegalArgumentException("Illegal environment - env: $env")
        }
    }
}

fun Application.mainModule() {
    val config = AzureADConfig.fromEnv()
    val azureAD = AzureAD(config)
    val env = EnvType.fromString(this.environment.config.property("ktor.environment").getString())
    log.info("Application environment = $env")
    val spleis = if (env == EnvType.LOCAL) LokaleKjenninger else Spleis(azureAD)
    return configuredModule(spleis, config, env)
}


fun Application.configuredModule(spleis: Personer, config: AzureADConfig, env: EnvType) {
    install(CallId) {
        generate {
            UUID.randomUUID().toString()
        }
    }
    install(CallLogging) {
        level = Level.INFO
    }

    install(ContentNegotiation) { register(ContentType.Application.Json, JacksonConverter(objectMapper)) }
    install(Sessions) {
        cookie<SpannerSession>("spanner", storage = SessionStorageMemory())
    }

    val httpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    install(Authentication) {
        oauth("oauth") {
            //skipWhen { call -> call.sessions.get<UserSession>() != null }
            urlProvider = { redirectUrl("/oauth2/callback", env) }
            skipWhen { call -> call.sessions.get<SpannerSession>() != null }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "AAD",
                    authorizeUrl = config.authorizationEndpoint,
                    accessTokenUrl = config.tokenEndpoint,
                    requestMethod = HttpMethod.Post,
                    clientId = config.clientId,
                    clientSecret = config.clientSecret,
                    defaultScopes = listOf("openid", "profile", "email")
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
                val (idType, idValue) = call.personId()
                Log.logger("Application")
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
        }
    }
}

private fun ApplicationCall.personId() =
    request.headers[IdType.FNR.header]?.let {
        Pair(IdType.FNR, request.header(IdType.FNR.header))
    } ?: Pair(IdType.AKTORID, request.header(IdType.AKTORID.header))


private fun ApplicationCall.redirectUrl(path: String, env: EnvType): String {
    val defaultPort = if (request.origin.scheme == "http") 80 else 443
    val protocol = request.origin.scheme
    val hostPort = request.host() + request.port().let {
            port -> if (port == defaultPort || env == EnvType.PROD) "" else ":$port"
    }
    return "$protocol://$hostPort$path"
}