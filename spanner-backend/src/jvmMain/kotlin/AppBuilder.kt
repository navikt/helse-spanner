import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.features.CORS
import io.ktor.features.CallId
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.callIdMdc
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import java.util.*
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

class AppBuilder(env: Map<String, String>) {
    private val isLocal = env.getOrDefault("LOCAL", "false").toBoolean()
    private val host = if (isLocal) Localhost() else Deployed(env)
    private val httpTraceLog = LoggerFactory.getLogger("tjenestekall")

    private val azureAdConfig = IAzureAdConfig.config(
        clientId = env.getOrDefault("AZURE_APP_CLIENT_ID", "unknown"),
        clientSecret = env.getOrDefault("AZURE_APP_CLIENT_SECRET", "unknown"),
        spleisClientId = env.getOrDefault("SPLEIS_CLIENT_ID", "unknown"),
        configurationUrl = env.getOrDefault("AZURE_APP_WELL_KNOWN_URL", "unknown"),
        isLocal = isLocal
    )

    private val azureAdClient = IAzureAdClient.client(azureAdConfig, isLocal)

    private val spleisClient = HttpClient(Apache) {
        install(JsonFeature) { serializer = JacksonSerializer() }
        engine {
            socketTimeout = 30_000
            connectTimeout = 30_000
            connectionRequestTimeout = 40_000
        }
    }

    private val restClient = IRestClient.restClient(
        spleisClient,
        isLocal
    )

    internal fun build() =
        embeddedServer(Netty, port = host.port()) {
            module(azureAdClient, host) {
                install(CallLogging) {
                    logger = httpTraceLog
                    level = Level.INFO
                    callIdMdc("callId")
                }
                routing {
                    naisApi()
                    frontendRouting()
                    authApi(azureAdClient, isLocal)
                    api(restClient, azureAdClient)
                }
                if (isLocal) {
                    install(CORS) {
                        allowSameOrigin = true
                        anyHost()
                        header("fnr")
                        header("aktorid")
                    }
                }
            }
        }
}

internal fun Application.module(azureAdClient: IAzureAdClient, host: Host, additional: Application.() -> Unit) {
    install(CallId) {
        generate {
            UUID.randomUUID().toString()
        }
    }
    install(ContentNegotiation) { register(ContentType.Application.Json, JacksonConverter(objectMapper)) }
    install(Sessions) {
        cookie<SpannerSession>("spanner", storage = SessionStorageMemory()) {
            serializer =
                SpannerSessionSerializer(objectMapper.copy().setVisibility(PropertyAccessor.FIELD, Visibility.ANY))
        }
    }
    azureAdAppAuthentication(azureAdClient, host)
    additional()
}
