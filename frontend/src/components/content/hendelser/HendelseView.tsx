import React from 'react'
import {
    AktivitetsloggContext,
    useAktivitetslogg,
    useArbeidsgiver,
    useForkastetVedtaksperiode,
    usePerson,
    useUtbetaling,
    useVedtak,
} from '../../../state/contexts'
import { ContentView } from '../../../state/state'
import { aktiviteterForKontekst, Hendelser } from './Hendelser'
import { ContentCategory } from '../ContentCategory'

const Person = React.memo(() => {
    const aktivitetslogg = useAktivitetslogg()
    return <Hendelser aktiviteter={aktivitetslogg.aktiviteter} />
})
Person.displayName = 'HendelseView.Person'

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
Arbeidsgiver.displayName = 'HendelseView.Arbeidsgiver'

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
Vedtaksperiode.displayName = 'HendelseView.Vedtaksperiode'

const ForkastetVedtaksperiode = React.memo(() => {
    const vedtaksperiode = useForkastetVedtaksperiode()
    const aktivitetslogg = useAktivitetslogg()

    const aktiviteter = aktiviteterForKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.vedtaksperiodeId && kontekst.kontekstMap.vedtaksperiodeId === vedtaksperiode.id) ??
            false
    )

    return <Hendelser aktiviteter={aktiviteter} />
})
Vedtaksperiode.displayName = 'HendelseView.ForkastetVedtaksperiode'

const Utbetaling = React.memo(() => {
    const utbetaling = useUtbetaling()
    const aktivitetslogg = useAktivitetslogg()

    const aktiviteter = aktiviteterForKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.utbetalingId && kontekst.kontekstMap.utbetalingId === utbetaling.id) ?? false
    )

    return <Hendelser aktiviteter={aktiviteter} />
})
Vedtaksperiode.displayName = 'HendelseView.Utbetaling'

export const HendelseView = React.memo(() => {
    const person = usePerson()
    return (
        <AktivitetsloggContext.Provider value={person.aktivitetslogg}>
            <ContentCategory
                displayName={ContentView.Hendelser}
                {...{ Person, Arbeidsgiver, Vedtaksperiode, ForkastetVedtaksperiode, Utbetaling }}
            />
        </AktivitetsloggContext.Provider>
    )
})

Hendelser.displayName = 'HendelseView'
