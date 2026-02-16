package no.nav.spanner.requests

data class HentAltSpiskammersetRequest(
    val personidentifikator: String,
    val etterspurteOpplysninger: List<String>
)
