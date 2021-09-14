import React from 'react'
import {
    ArbeidsgiverContext,
    createContext,
    useArbeidsgiver,
    useContext,
    useIsSelected,
    usePerson,
    useVedtak,
    VedtakContext,
} from '../../state/contexts'
import ReactJson from 'react-json-view'
import {Aktivitet, Aktivitetslogg, Kontekst} from '../../state/dto'
import {mapNotUndefined} from "../../utils";


const aktiviteterForKontekst = (aktivitetslogg: Aktivitetslogg, filter: (kontekst: Kontekst) => boolean) : Aktivitet[] => {
    const mapping = (y : Kontekst, index: number) => filter(y) && index
    const konteksterIndex = mapNotUndefined(aktivitetslogg.kontekster, mapping)
    return aktivitetslogg.aktiviteter.filter(aktivitet => aktivitet.kontekster.find(kontekstI => konteksterIndex.includes(kontekstI)))
}

export const AktivitetsloggContext = createContext<Aktivitetslogg>()

export const useAktivitetslogg = () => useContext(AktivitetsloggContext)

const Arbeidsgiver = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()

    const isSelected = useIsSelected()
    if (!isSelected) return null
    return (
        <div>
            <ReactJson src={arbeidsgiver} name={null} collapsed={1} />
        </div>
    )
})

const Person = React.memo(() => {
    const person = usePerson()
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return (
        <div>
            <ReactJson src={person} name={null} collapsed={1} />
        </div>
    )
})

const Vedtaksperiode = React.memo(() => {
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

    return (
        <div>
            {aktiviteter.map((it, index) => <p key={index}>{it.melding}</p>)}
        </div>
    )
})

export const AktivitetsloggView = React.memo(() => {
    const person = usePerson()
    return (
        <AktivitetsloggContext.Provider value={person.aktivitetslogg}>
            <Person />
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <Arbeidsgiver />
                    {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                        <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                            <Vedtaksperiode />
                        </VedtakContext.Provider>
                    ))}
                </ArbeidsgiverContext.Provider>
            ))}
        </AktivitetsloggContext.Provider>
    )
})
