package no.nav.spanner

import java.time.LocalDateTime

internal class SpannerSession(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String,
    expiresIn: Long,
) {
    val validUntil: LocalDateTime = LocalDateTime.now().plusSeconds(expiresIn)
}