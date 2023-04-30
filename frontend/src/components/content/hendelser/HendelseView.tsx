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
import { AktivitetsloggV2, Hendelsekontekst } from '../../../state/model'
import { KontekstMapV2Dto, PersonDto } from '../../../state/dto'
import parseISO from 'date-fns/parseISO'
import compareAsc from 'date-fns/compareAsc'
import { hasValue } from '../../../utils'

const Person = () => {
    const aktivitetslogg = useAktivitetslogg()
    const hendelser = hendelserAssosiertMedKontekst(aktivitetslogg, () => true)
    return <Hendelser hendelser={hendelser} />
}
Person.displayName = 'HendelseView.Person'

const Arbeidsgiver = () => {
    const aktivitetslogg = useAktivitetslogg()
    const arbeidsgiver = useArbeidsgiver()
    const hendelser = hendelserAssosiertMedKontekst(aktivitetslogg, (kontekstType, kontekst) =>
        (!!kontekst.organisasjonsnummer &&
            kontekst.organisasjonsnummer === arbeidsgiver.organisasjonsnummer) ??
        false
    )
    return <Hendelser hendelser={hendelser} />
}
Arbeidsgiver.displayName = 'HendelseView.Arbeidsgiver'

const Vedtaksperiode = () => {
    const vedtaksperiode = useVedtak()
    const aktivitetslogg = useAktivitetslogg()
    const hendelser = hendelserAssosiertMedKontekst(
        aktivitetslogg,
        (kontekstType, kontekst) =>
            (!!kontekst.vedtaksperiodeId && kontekst.vedtaksperiodeId === vedtaksperiode.id) ??
            false
    )

    return <Hendelser hendelser={hendelser} />
}
Vedtaksperiode.displayName = 'HendelseView.Vedtaksperiode'

const ForkastetVedtaksperiode = () => {
    const vedtaksperiode = useForkastetVedtaksperiode()
    const aktivitetslogg = useAktivitetslogg()

    const hendelser = hendelserAssosiertMedKontekst(
        aktivitetslogg,
        (kontekstType, kontekst) =>
            (!!kontekst.vedtaksperiodeId && kontekst.vedtaksperiodeId === vedtaksperiode.id) ??
            false
    )

    return <Hendelser hendelser={hendelser} />
}
ForkastetVedtaksperiode.displayName = 'HendelseView.ForkastetVedtaksperiode'

const Utbetaling = () => {
    const utbetaling = useUtbetaling()
    const aktivitetslogg = useAktivitetslogg()

    const hendelser = hendelserAssosiertMedKontekst(
        aktivitetslogg,
        (kontekstType, kontekst) =>
            (!!kontekst.utbetalingId && kontekst.utbetalingId === utbetaling.id) ?? false
    )

    return <Hendelser hendelser={hendelser} />
}
Utbetaling.displayName = 'HendelseView.Utbetaling'

export const HendelseView = () => {
    const person = usePerson()
    const aktivitetslogg: AktivitetsloggV2 = React.useMemo(() => aktivitetsloggFraPerson(person), [person])
    return (
        <AktivitetsloggContext.Provider value={aktivitetslogg}>
            <ContentCategory
                displayName={ContentView.Hendelser}
                {...{ Person, Arbeidsgiver, Vedtaksperiode, ForkastetVedtaksperiode, Utbetaling }}
            />
        </AktivitetsloggContext.Provider>
    )
}
HendelseView.displayName = 'HendelseView'

function aktivitetsloggFraPerson(person: PersonDto): AktivitetsloggV2 {
    let alleAktiviteter = person.aktivitetsloggV2?.aktiviteter ?? [];
    let hendelsekontekster: { detaljer: KontekstMapV2Dto, kontekstType: string }[] = []
    alleAktiviteter
        .flatMap((it) => Object.keys(it.kontekster).map((kontekstType) => {
            return {
                "kontekstType": kontekstType,
                "detaljer": it.kontekster[kontekstType]
            }
        }))
        .filter((it) =>  !!it.detaljer.meldingsreferanseId)
        .forEach((kontekst) => {
            if (!hendelsekontekster.some((it) => it.detaljer.meldingsreferanseId == kontekst.detaljer.meldingsreferanseId)) {
                hendelsekontekster.push(kontekst)
            }
        })
    return {
        aktiviteter: alleAktiviteter,
        hendelsekontekster: hendelsekontekster.map((it, index) => {
            const aktiviteter = alleAktiviteter.filter((aktivitet) => aktivitet.kontekster.hasOwnProperty(it.kontekstType)
                && aktivitet.kontekster[it.kontekstType].meldingsreferanseId == it.detaljer.meldingsreferanseId)
            const opprettet =
                aktiviteter
                    .map((it) => parseISO(it.tidsstempel))
                    .sort(compareAsc)
                    .find(hasValue) ?? parseISO('1900-01-01')
            const harError = !!aktiviteter.find((it) => it.nivå == 'FUNKSJONELL_FEIL')
            const harWarning = !!aktiviteter.find((it) => it.nivå == 'VARSEL')
            return {
                kontekstType: it.kontekstType,
                kontekstMap: it.detaljer,
                aktiviteter,
                id: index,
                erHendelsekontekst: true,
                opprettet,
                harError,
                harWarning,
            }
        })
    }
}

const hendelserAssosiertMedKontekst = (
    aktivitetslogg: AktivitetsloggV2,
    kontekstErInteressant: (kontekstType: string, kontekst: KontekstMapV2Dto) => boolean
): Hendelsekontekst[] => {
    // alle aktiviteter som har en kontekst som matcher skal få 'interessant' = true

    return aktivitetslogg.hendelsekontekster
        .map((hendelsekontekst) => {
            return {
                kontekstType: hendelsekontekst.kontekstType,
                kontekstMap: hendelsekontekst.kontekstMap,
                aktiviteter: hendelsekontekst.aktiviteter.map((aktivitet) => {
                    const erInteressant = Object.keys(aktivitet.kontekster).some((kontekstType) => kontekstErInteressant(kontekstType, aktivitet.kontekster[kontekstType]))
                    return {
                        id: aktivitet.id,
                        nivå: aktivitet.nivå,
                        tekst: aktivitet.tekst,
                        tidsstempel: aktivitet.tidsstempel,
                        kontekster: aktivitet.kontekster,
                        interessant: erInteressant
                    }
                }),
                id: hendelsekontekst.id,
                erHendelsekontekst: hendelsekontekst.erHendelsekontekst,
                opprettet: hendelsekontekst.opprettet,
                harError: hendelsekontekst.harError,
                harWarning: hendelsekontekst.harWarning
            }
        })
        .filter((hendelsekontekst) => hendelsekontekst.aktiviteter.some((aktivitet) =>
            aktivitet.interessant && !aktivitet.tekst.match(/Forsøker å gjenoppta/)
        ))
}
