import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.sessions.*
import java.util.*

internal class SpannerSession(
    accessToken: AccessToken
) {
    var accessToken: AccessToken = accessToken
        private set

    fun update(accessToken: AccessToken) {
        this.accessToken = accessToken
    }
}

fun createLocalToken(): String {
    return JWT.create()
        .withAudience("alle")
        .withClaim("name", "S. A. Ksbehandler")
        .withClaim("email", "dev@nav.no")
        .withClaim("NAVident", "dev-ident")
        .withClaim("oid", UUID.randomUUID().toString())
        .sign(Algorithm.HMAC256("hemmelighet"))
}

internal class SpannerSessionSerializer(objectMapper: ObjectMapper) : SessionSerializer<SpannerSession> {
    override fun serialize(session: SpannerSession): String {
        return objectMapper.writeValueAsString(session.accessToken)
    }

    override fun deserialize(text: String): SpannerSession {
        return SpannerSession(objectMapper.readValue(text))
    }

}