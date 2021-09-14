import React from 'react'
import {
    ArbeidsgiverContext,
    createContext,
    useContext,
    useIsSelected,
    usePerson,
    useVedtak,
    VedtakContext,
} from '../../state/contexts'
import { Aktivitet, Aktivitetslogg, Kontekst } from '../../state/dto'
import { mapNotUndefined } from '../../utils'

const aktiviteterForKontekst = (
    aktivitetslogg: Aktivitetslogg,
    filter: (kontekst: Kontekst) => boolean
): Aktivitet[] => {
    const mapping = (y: Kontekst, index: number) => filter(y) && index
    const konteksterIndex = mapNotUndefined(aktivitetslogg.kontekster, mapping)
    return aktivitetslogg.aktiviteter.filter((aktivitet) =>
        aktivitet.kontekster.find((kontekstI) => konteksterIndex.includes(kontekstI))
    )
}

export const AktivitetsloggContext = createContext<Aktivitetslogg>()

export const useAktivitetslogg = () => useContext(AktivitetsloggContext)

const Arbeidsgiver = React.memo(() => {
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return (
        <div>
        </div>
    )
})

const Person = React.memo(() => {
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return (
        <div>
        </div>
    )
})

const AktivitetView = React.memo(({ aktivitet }: { aktivitet: Aktivitet }) => <p>{aktivitet.melding}</p>)

const AktivitetsView = React.memo(({ aktiviteter }: { aktiviteter: Aktivitet[] }) => (
    <div>
        {aktiviteter.map((it, index) => (
            <AktivitetView key={index} aktivitet={it} />
        ))}
    </div>
))

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
            <AktivitetsView aktiviteter={aktiviteter}/>
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
