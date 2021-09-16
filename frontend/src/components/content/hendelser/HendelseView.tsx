import React, {PropsWithChildren} from 'react'
import {
    AktivitetsloggContext,
    ArbeidsgiverContext,
    useAktivitetslogg,
    useArbeidsgiver,
    useIsSelected,
    usePerson,
    useVedtak,
    VedtakContext,
} from '../../../state/contexts'
import {useRecoilValue} from "recoil";
import {ContentView, displayViewState} from "../../../state/state";
import {aktiviteterForKontekst, Hendelser} from "./Hendelser";

const HendelserForPerson = React.memo(() => {
    const isSelected = useIsSelected()
    const aktivitetslogg = useAktivitetslogg()
    if (!isSelected) return null

    return <Hendelser aktiviteter={aktivitetslogg.aktiviteter} />
})

const HendelserForArbeidsgiver = React.memo(() => {
    const isSelected = useIsSelected()
    const aktivitetslogg = useAktivitetslogg()
    const arbeidsgiver = useArbeidsgiver()
    if (!isSelected) return null

    const aktiviteter = aktiviteterForKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.organisasjonsnummer &&
                kontekst.kontekstMap.organisasjonsnummer === arbeidsgiver.organisasjonsnummer) ??
            false
    )

    return <Hendelser aktiviteter={aktiviteter} />
})

export const HendelseView = React.memo(() => {
    const person = usePerson()
    const useDisplayView = useRecoilValue(displayViewState)
    if(!useDisplayView.includes(ContentView.Hendelser)) return null

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

const HendelserForVedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    const aktivitetslogg = useAktivitetslogg()
    const isSelected = useIsSelected()
    if (!isSelected) return null

    const aktiviteter = aktiviteterForKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.vedtaksperiodeId && kontekst.kontekstMap.vedtaksperiodeId === vedtaksperiode.id) ??
            false
    )

    return <Hendelser aktiviteter={aktiviteter} />
})



const ShowIfSelected: React.FC<PropsWithChildren<any>> = React.memo(({ children }) => {
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return <>{children}</>
})

