package no.nav.spanner

import com.natpryce.konfig.*

data class Config (
    val development: Boolean,
    val port: Int,
    val host: String,
    val spleisUrl: String,
    val spleisScope: String,
    val sparsomUrl: String,
    val sparsomScope: String
) {
    companion object {
        fun from(configuration: Configuration) = Config(
            development = configuration.getOrElse(Key("DEVELOPMENT", booleanType), false),
            port = configuration.getOrElse(Key("HTTP_PORT", intType), 8080),
            host = configuration.getOrElse(Key("HTTP_HOST", stringType), "0.0.0.0"),
            spleisUrl = configuration[Key("SPLEIS_API_URL", stringType)],
            spleisScope = configuration[Key("SPLEIS_SCOPE", stringType)],
            sparsomUrl = configuration[Key("SPARSOM_API_URL", stringType)],
            sparsomScope = configuration[Key("SPARSOM_SCOPE", stringType)]
        )
    }
}

