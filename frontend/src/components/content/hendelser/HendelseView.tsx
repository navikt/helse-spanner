import React from 'react'
import {
    AktivitetsloggContext,
    useAktivitetslogg,
    useArbeidsgiver,
    usePerson,
    useVedtak,
} from '../../../state/contexts'
import { aktiviteterForKontekst, Hendelser } from './Hendelser'
import { ContentView } from '../ContentView'

const HendelserForPerson = React.memo(() => {
    const aktivitetslogg = useAktivitetslogg()
    return <Hendelser aktiviteter={aktivitetslogg.aktiviteter} />
})

const HendelserForArbeidsgiver = React.memo(() => {
    const aktivitetslogg = useAktivitetslogg()
    const arbeidsgiver = useArbeidsgiver()
    const aktiviteter = aktiviteterForKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.organisasjonsnummer &&
                kontekst.kontekstMap.organisasjonsnummer === arbeidsgiver.organisasjonsnummer) ??
            false
    )

    return <Hendelser aktiviteter={aktiviteter} />
})

const HendelserForVedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    const aktivitetslogg = useAktivitetslogg()
    const aktiviteter = aktiviteterForKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.vedtaksperiodeId && kontekst.kontekstMap.vedtaksperiodeId === vedtaksperiode.id) ??
            false
    )

    return <Hendelser aktiviteter={aktiviteter} />
})

export const HendelseView = React.memo(() => {
    const person = usePerson()

    return (
        <AktivitetsloggContext.Provider value={person.aktivitetslogg}>
            <ContentView
                person={HendelserForPerson}
                arbeidsgiver={HendelserForArbeidsgiver}
                vedtaksperiode={HendelserForVedtaksperiode}
            />
        </AktivitetsloggContext.Provider>
    )
})
