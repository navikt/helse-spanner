import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.*
import io.ktor.auth.*
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
import org.apache.http.impl.conn.SystemDefaultRoutePlanner
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import java.net.ProxySelector
import java.util.*

class AppBuilder(env: Map<String, String>) {
    private val httpTraceLog = LoggerFactory.getLogger("tjenestekall")

    private val appConfig = AppConfig(env)

    private val azureAdClient = HttpClient(Apache) {
        engine {
            customizeClient {
                setRoutePlanner(SystemDefaultRoutePlanner(ProxySelector.getDefault()))
            }
        }
        install(JsonFeature) {
            serializer = JacksonSerializer {
                registerModule(JavaTimeModule())
            }
        }
    }

    private val spleisClient = HttpClient(Apache) {
        install(JsonFeature) { serializer = JacksonSerializer() }
        engine {
            socketTimeout = 30_000
            connectTimeout = 30_000
            connectionRequestTimeout = 40_000
        }
    }
    private val accessTokenClient = AccessTokenClient(
        aadAccessTokenUrl = env.getValue("AZURE_OPENID_CONFIG_TOKEN_ENDPOINT"),
        clientId = env.getValue("AZURE_APP_CLIENT_ID"),
        clientSecret = env.getValue("AZURE_APP_CLIENT_SECRET"),
        httpClient = azureAdClient
    )
    private val restClient = ProdRestClient(
        httpClient = spleisClient,
        accessTokenClient = accessTokenClient,
        spleisClientId = env.getValue("SPLEIS_CLIENT_ID")
    )

    internal fun build() =
        embeddedServer(Netty, applicationEngineEnvironment {
            appConfig.ktorConfig.configure(this)
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
                nais()
                azureAdAppAuthentication(appConfig.azureConfig)
                routing {
                    authenticate("oidc") {
                        get("/api/person-fnr") {
                            val fnr = call.request.header("fnr")!!
                            restClient.hentPersonMedFnr(fnr)
                        }
                    }
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