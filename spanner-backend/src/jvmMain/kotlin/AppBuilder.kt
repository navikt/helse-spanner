import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
    private val host = env.getOrDefault("HOST", "localhost:9000").toString()
    private val httpTraceLog = LoggerFactory.getLogger("tjenestekall")

    private val azureAdClient = IAzureAdClient.client(env, isLocal)

    private val spleisClient = HttpClient(Apache) {
        install(JsonFeature) { serializer = JacksonSerializer() }
        engine {
            socketTimeout = 30_000
            connectTimeout = 30_000
            connectionRequestTimeout = 40_000
        }
    }

    private val restClient = IRestClient.restClient(spleisClient, env, isLocal)

    internal fun build() =
        embeddedServer(Netty, applicationEngineEnvironment {
            connector { port = env["HTTP_PORT"]?.toInt() ?: 9000 }
            module {
                install(CallId) {
                    generate {
                        UUID.randomUUID().toString()
                    }
                }
                install(CallLogging) {
                    logger = httpTraceLog
                    level = Level.INFO
                    callIdMdc("callId")
                    filter { call -> call.request.path().startsWith("/api/") }
                }
                install(ContentNegotiation) { register(ContentType.Application.Json, JacksonConverter(objectMapper)) }
                install(Sessions) { cookie<SpannerSession>("spanner", storage = SessionStorageMemory()) }
                nais()
                azureAdAppAuthentication(azureAdClient, host)
                routing {
                    api(restClient, isLocal, azureAdClient)
                }
            }
        })
}

private val objectMapper = jacksonObjectMapper()
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .registerModule(JavaTimeModule())
    .setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
        indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
        indentObjectsWith(DefaultIndenter("  ", "\n"))
    })