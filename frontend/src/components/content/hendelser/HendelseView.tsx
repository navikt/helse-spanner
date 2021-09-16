import React from 'react'
import {
    AktivitetsloggContext,
    ArbeidsgiverContext,
    useAktivitetslogg,
    useArbeidsgiver,
    usePerson,
    useVedtak,
    VedtakContext,
} from '../../../state/contexts'
import { useRecoilValue } from 'recoil'
import { ContentView, displayViewState } from '../../../state/state'
import { aktiviteterForKontekst, Hendelser } from './Hendelser'
import { ShowIfSelected } from '../Content'

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
    const useDisplayView = useRecoilValue(displayViewState)
    if (!useDisplayView.includes(ContentView.Hendelser)) return null

    return (
        <AktivitetsloggContext.Provider value={person.aktivitetslogg}>
            <ShowIfSelected>
                <HendelserForPerson />
            </ShowIfSelected>
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <ShowIfSelected>
                        <HendelserForArbeidsgiver />
                    </ShowIfSelected>
                    {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                        <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                            <ShowIfSelected>
                                <HendelserForVedtaksperiode />
                            </ShowIfSelected>
                        </VedtakContext.Provider>
                    ))}
                </ArbeidsgiverContext.Provider>
            ))}
        </AktivitetsloggContext.Provider>
    )
})
