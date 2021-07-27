import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

data class SpannerSession(
    val accessToken: String,
    val refreshToken: String?,
    val expiresIn: Long,
    val idToken: String,
    val bruker: String
)

fun createLocalToken(): String {
    return JWT.create()
        .withAudience("alle")
        .withClaim("name", "S. A. Ksbehandler")
        .withClaim("email", "dev@nav.no")
        .withClaim("NAVident", "dev-ident")
        .withClaim("oid", UUID.randomUUID().toString())
        .sign(Algorithm.HMAC256("hemmelighet"))
}