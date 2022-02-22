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
import { Hendelser } from './Hendelser'
import { ContentCategory } from '../ContentCategory'
import { Aktivitetslogg, Kontekst } from '../../../state/model'
import { AktivitetDto, PersonDto } from '../../../state/dto'
import parseISO from 'date-fns/parseISO'
import compareAsc from 'date-fns/compareAsc'
import { hasValue } from '../../../utils'

const Person = React.memo(() => {
    const aktivitetslogg = useAktivitetslogg()
    return <Hendelser hendelser={aktivitetslogg.kontekster.filter((it) => it.erHendelsekontekst)} />
})
Person.displayName = 'HendelseView.Person'

const Arbeidsgiver = React.memo(() => {
    const aktivitetslogg = useAktivitetslogg()
    const arbeidsgiver = useArbeidsgiver()
    const hendelser = hendelserAssosiertMedKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.organisasjonsnummer &&
                kontekst.kontekstMap.organisasjonsnummer === arbeidsgiver.organisasjonsnummer) ??
            false
    )

    return <Hendelser hendelser={hendelser} />
})
Arbeidsgiver.displayName = 'HendelseView.Arbeidsgiver'

const Vedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    const aktivitetslogg = useAktivitetslogg()
    const hendelser = hendelserAssosiertMedKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.vedtaksperiodeId && kontekst.kontekstMap.vedtaksperiodeId === vedtaksperiode.id) ??
            false
    )

    return <Hendelser hendelser={hendelser} />
})
Vedtaksperiode.displayName = 'HendelseView.Vedtaksperiode'

const ForkastetVedtaksperiode = React.memo(() => {
    const vedtaksperiode = useForkastetVedtaksperiode()
    const aktivitetslogg = useAktivitetslogg()

    const hendelser = hendelserAssosiertMedKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.vedtaksperiodeId && kontekst.kontekstMap.vedtaksperiodeId === vedtaksperiode.id) ??
            false
    )

    return <Hendelser hendelser={hendelser} />
})
Vedtaksperiode.displayName = 'HendelseView.ForkastetVedtaksperiode'

const Utbetaling = React.memo(() => {
    const utbetaling = useUtbetaling()
    const aktivitetslogg = useAktivitetslogg()

    const hendelser = hendelserAssosiertMedKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.utbetalingId && kontekst.kontekstMap.utbetalingId === utbetaling.id) ?? false
    )

    return <Hendelser hendelser={hendelser} />
})
Vedtaksperiode.displayName = 'HendelseView.Utbetaling'

export const HendelseView = React.memo(() => {
    const person = usePerson()
    const aktivitetslogg: Aktivitetslogg = React.useMemo(() => aktivitetsloggFraPerson(person), [person])
    return (
        <AktivitetsloggContext.Provider value={aktivitetslogg}>
            <ContentCategory
                displayName={ContentView.Hendelser}
                {...{ Person, Arbeidsgiver, Vedtaksperiode, ForkastetVedtaksperiode, Utbetaling }}
            />
        </AktivitetsloggContext.Provider>
    )
})

Hendelser.displayName = 'HendelseView'

function aktivitetsloggFraPerson(person: PersonDto): Aktivitetslogg {
    const alleAktiviteter = person.aktivitetslogg.aktiviteter
    const kontekstDtoer = person.aktivitetslogg.kontekster

    const kontekster = kontekstDtoer.map((kontekstDto, index): Kontekst => {
        const aktiviteter = alleAktiviteter.filter((aktivitet) => aktivitet.kontekster.includes(index))
        const opprettet =
            aktiviteter
                .map((it) => parseISO(it.tidsstempel))
                .sort(compareAsc)
                .find(hasValue) ?? parseISO('1900-01-01')
        const harError = !!aktiviteter.find((it) => it.alvorlighetsgrad == 'ERROR')
        const harWarning = !!aktiviteter.find((it) => it.alvorlighetsgrad == 'WARN')
        return {
            kontekstType: kontekstDto.kontekstType,
            kontekstMap: kontekstDto.kontekstMap,
            aktiviteter,
            id: index,
            erHendelsekontekst: !!kontekstDto.kontekstMap.meldingsreferanseId,
            opprettet,
            harError,
            harWarning,
        }
    })

    return {
        aktiviteter: alleAktiviteter,
        kontekster,
    }
}

const hendelserAssosiertMedKontekst = (
    aktivitetslogg: Aktivitetslogg,
    kontekstErInteressant: (kontekst: Kontekst) => boolean
): Kontekst[] => {
    const interessanteKontekster = aktivitetslogg.kontekster.filter(kontekstErInteressant).map((it) => it.id)

    const harInteressantKontekst = (aktivitet: AktivitetDto) =>
        aktivitet.kontekster.some((id) => interessanteKontekster.includes(id))

    return aktivitetslogg.kontekster
        .filter((kontekst) => kontekst.erHendelsekontekst)
        .filter((hendelseKontekst) => hendelseKontekst.aktiviteter.some(harInteressantKontekst))
}
