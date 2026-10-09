package no.nav.spanner

import com.auth0.jwt.JWT
import tools.jackson.core.util.DefaultIndenter
import tools.jackson.core.util.DefaultPrettyPrinter
import tools.jackson.databind.JsonNode
import tools.jackson.databind.cfg.DateTimeFeature
import tools.jackson.module.kotlin.jacksonMapperBuilder
import java.time.ZoneId
import java.util.*

internal val objectMapper =
    jacksonMapperBuilder()
        .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
        .defaultPrettyPrinter(
            DefaultPrettyPrinter()
                .withArrayIndenter(DefaultPrettyPrinter.FixedSpaceIndenter.instance())
                .withObjectIndenter(DefaultIndenter("  ", "\n")),
        ).build()

internal fun Date.toLocalDateTime() = toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

internal fun JsonNode.isMissingOrNull() = isMissingNode || isNull

fun String.asJwt() = JWT.decode(this)
