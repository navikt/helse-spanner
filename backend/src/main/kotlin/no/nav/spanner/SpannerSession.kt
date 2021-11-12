package no.nav.spanner

import java.time.LocalDateTime

internal class SpannerSession(
    val accessToken: String,
    val idToken: String,
    expiresIn: Long,
) {
    val validBefore: LocalDateTime = LocalDateTime.now().plusSeconds(expiresIn)
}