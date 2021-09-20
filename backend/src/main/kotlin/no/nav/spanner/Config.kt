package no.nav.spanner

import com.natpryce.konfig.*

data class Config (
    val development: Boolean,
    val port: Int,
    val host: String,
    val spleisUrl: String,
) {
    companion object {
        fun from(configuration: Configuration) = Config(
            configuration.getOrElse(Key("DEVELOPMENT", booleanType), false),
            configuration.getOrElse(Key("HTTP_PORT", intType), 8080),
            configuration.getOrElse(Key("HTTP_HOST", stringType), "0.0.0.0"),
            configuration[Key("SPLEIS_API_URL", stringType)]
        )
    }
}

