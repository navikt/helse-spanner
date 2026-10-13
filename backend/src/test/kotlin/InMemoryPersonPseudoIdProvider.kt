package no.nav.spanner

import com.github.navikt.tbd_libs.personpseudoid.Identitetsnummer
import com.github.navikt.tbd_libs.personpseudoid.PersonPseudoId
import com.github.navikt.tbd_libs.personpseudoid.PersonPseudoIdProvider
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class InMemoryPersonPseudoIdProvider : PersonPseudoIdProvider {
    private val lagret = ConcurrentHashMap<PersonPseudoId, Identitetsnummer>()

    override fun nyPersonPseudoId(identitetsnummer: Identitetsnummer): PersonPseudoId = PersonPseudoId(UUID.randomUUID()).also { lagret[it] = identitetsnummer }

    override fun finnIdentitetsnummer(personPseudoId: PersonPseudoId): Identitetsnummer? = lagret[personPseudoId]
}
