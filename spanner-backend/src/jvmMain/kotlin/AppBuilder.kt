import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import java.util.*

class AppBuilder(private val env: Map<String, String>) {
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
                    filter { call -> call.request.path().startsWith("/api/") }
                }
                routing {
                    naisApi()
                    authApi(azureAdClient, isLocal)
                    api(restClient, azureAdClient, isLocal)
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
