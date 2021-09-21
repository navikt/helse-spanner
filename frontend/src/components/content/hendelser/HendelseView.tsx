import React from 'react'
import {
    AktivitetsloggContext,
    useAktivitetslogg,
    useArbeidsgiver,
    usePerson,
    useVedtak,
} from '../../../state/contexts'
import { useRecoilValue } from 'recoil'
import { ContentView, displayViewState } from '../../../state/state'
import { aktiviteterForKontekst, Hendelser } from './Hendelser'
import {ContentCatgegoryHOC,} from '../ContentCatgegoryHOC'

const Person = React.memo(() => {
    const aktivitetslogg = useAktivitetslogg()
    return <Hendelser aktiviteter={aktivitetslogg.aktiviteter} />
})
Person.displayName="HendelseView.Person"

const Arbeidsgiver = React.memo(() => {
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
Arbeidsgiver.displayName="HendelseView.Arbeidsgiver"

const Vedtaksperiode = React.memo(() => {
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
Vedtaksperiode.displayName="HendelseView.Vedtaksperiode"

export const HendelseView = React.memo(() => {
    const person = usePerson()
    const useDisplayView = useRecoilValue(displayViewState)
    if (!useDisplayView.includes(ContentView.Hendelser)) return null
    return (
        <AktivitetsloggContext.Provider value={person.aktivitetslogg}>
            <ContentCatgegoryHOC {...{ Person, Arbeidsgiver, Vedtaksperiode }}/>
        </AktivitetsloggContext.Provider>
    )
})

Hendelser.displayName="HendelseView"
