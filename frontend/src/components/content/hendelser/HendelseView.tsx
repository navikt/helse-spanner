import React from 'react'
import {ContentView} from '../../../state/state'
import {Hendelser} from './Hendelser'
import {ContentCategory} from '../ContentCategory'
import {AktivitetsloggV2, Hendelsekontekst} from '../../../state/model'
import {
    ArbeidsgiverDto,
    FokastetVedtaksperiodeDto,
    KontekstMapV2Dto,
    PersonDto,
    UtbetalingDto,
    VedtakDto
} from '../../../state/dto'
import parseISO from 'date-fns/parseISO'
import compareAsc from 'date-fns/compareAsc'
import {hasValue} from '../../../utils'

const Person = (aktivitetslogg: AktivitetsloggV2) => {
    return ({ person }: { person: PersonDto}) => {
        const hendelser = hendelserAssosiertMedKontekst(aktivitetslogg, () => true)
        return <Hendelser hendelser={hendelser} />
    }
}

const Arbeidsgiver = (aktivitetslogg: AktivitetsloggV2) => {
    return ({ arbeidsgiver }: { arbeidsgiver: ArbeidsgiverDto }) => {
        const hendelser = hendelserAssosiertMedKontekst(aktivitetslogg, (kontekstType, kontekst) =>
            (!!kontekst.organisasjonsnummer &&
                kontekst.organisasjonsnummer === arbeidsgiver.organisasjonsnummer) ??
            false
        )
        return <Hendelser hendelser={hendelser} />
    }
}
Arbeidsgiver.displayName = 'HendelseView.Arbeidsgiver'

const Vedtaksperiode = (aktivitetslogg: AktivitetsloggV2) => {
    return ({ vedtaksperiode }: { vedtaksperiode: VedtakDto }) => {
        const hendelser = hendelserAssosiertMedKontekst(
            aktivitetslogg,
            (kontekstType, kontekst) =>
                (!!kontekst.vedtaksperiodeId && kontekst.vedtaksperiodeId === vedtaksperiode.id) ??
                false
        )

        return <Hendelser hendelser={hendelser}/>
    }
}
Vedtaksperiode.displayName = 'HendelseView.Vedtaksperiode'

const ForkastetVedtaksperiode = (aktivitetslogg: AktivitetsloggV2) => {
    return ({ vedtaksperiode }: { vedtaksperiode: FokastetVedtaksperiodeDto }) => {
        const hendelser = hendelserAssosiertMedKontekst(
            aktivitetslogg,
            (kontekstType, kontekst) =>
                (!!kontekst.vedtaksperiodeId && kontekst.vedtaksperiodeId === vedtaksperiode.id) ??
                false
        )

        return <Hendelser hendelser={hendelser} />
    }
}
ForkastetVedtaksperiode.displayName = 'HendelseView.ForkastetVedtaksperiode'

const Utbetaling = (aktivitetslogg: AktivitetsloggV2) => {
    return ({ utbetaling }: { utbetaling: UtbetalingDto }) => {
        const hendelser = hendelserAssosiertMedKontekst(
            aktivitetslogg,
            (kontekstType, kontekst) =>
                (!!kontekst.utbetalingId && kontekst.utbetalingId === utbetaling.id) ?? false
        )

        return <Hendelser hendelser={hendelser} />
    }
}

export const HendelseView = ({ person, valgteTing }: { person: PersonDto, valgteTing: string[] }) => {
    const aktivitetslogg: AktivitetsloggV2 = React.useMemo(() => aktivitetsloggFraPerson(person), [person])
    return (
        <ContentCategory
            displayName={ContentView.Hendelser}
            valgteTing={valgteTing}
            person={person}
            Person = { Person(aktivitetslogg) }
            Arbeidsgiver={ Arbeidsgiver(aktivitetslogg) }
            Vedtaksperiode={ Vedtaksperiode(aktivitetslogg) }
            ForkastetVedtaksperiode={ ForkastetVedtaksperiode(aktivitetslogg) }
            Utbetaling={ Utbetaling(aktivitetslogg) }
        />
    )
}

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
