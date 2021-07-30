import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.time.LocalDateTime
import java.util.*

internal class SpannerSession(
    accessToken: AccessToken
) {
    var accessToken: AccessToken = accessToken // TODO: fiks plis evnt update accesstoken
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