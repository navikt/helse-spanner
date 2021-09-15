import React from 'react'
import {
    AktivitetsloggContext,
    ArbeidsgiverContext,
    useAktivitetslogg,
    useIsSelected,
    usePerson,
    useVedtak,
    VedtakContext,
} from '../../state/contexts'
import {Aktivitet, Aktivitetslogg, Kontekst} from '../../state/dto'
import {mapNotUndefined} from '../../utils'


const Person = React.memo(() => {
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return <div></div>
})

const Arbeidsgiver = React.memo(() => {
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return <div></div>
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
            <AktivitetListeView aktiviteter={aktiviteter} />
        </div>
    )
})

const AktivitetListeView = React.memo(({ aktiviteter }: { aktiviteter: Aktivitet[] }) => {
    const kontekstFinnesiAktiviteter = (kontekst: Kontekst, index: number): boolean =>
        !!aktiviteter.find((aktivitet) => aktivitet.kontekster.includes(index))

    const erMeldingsKontekst = (kontekst: Kontekst) => !!kontekst.kontekstMap.meldingsreferanseId && kontekst.kontekstType != 'GjenopptaBehandling'

    const aktivitetslogg = useAktivitetslogg()

    const hendelseKontektster: [Kontekst, number][] = mapNotUndefined(aktivitetslogg.kontekster, (kontekst, index) => {
        if (kontekstFinnesiAktiviteter(kontekst, index) && erMeldingsKontekst(kontekst)) {
            return [kontekst, index]
        } else return undefined
    })

    return (
        <div>
            {hendelseKontektster.map(([kontekst, index]) => {
                const kontekstAktiviter = aktiviteter.filter((aktivitet) => aktivitet.kontekster.includes(index))
                return <HendelseView aktiviteter={kontekstAktiviter} kontekst={kontekst} key={kontekst.kontekstMap.meldingsreferanseId} />
            })}
        </div>
    )
})

const HendelseView = React.memo(({ aktiviteter, kontekst }: { aktiviteter: Aktivitet[], kontekst: Kontekst}) => {
    return (
        <div>
            <p>{kontekst.kontekstType}</p>
            {aktiviteter.map((it, index) => (
                <AktivitetView key={index} aktivitet={it} />
            ))}
        </div>
    )
})

const AktivitetView = React.memo(({ aktivitet }: { aktivitet: Aktivitet }) => <p>{ "------ " + aktivitet.melding}</p>)

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
