import React from 'react'
import { AktivitetDto, AktivitetsloggDto, KontekstDto } from '../../../state/dto'
import { useAktivitetslogg } from '../../../state/contexts'
import { hasValue, mapNotUndefined } from '../../../utils'
import styles from './Hendelser.module.css'
import { Hendelse } from './Hendelse'
import parseISO from 'date-fns/parseISO'
import compareAsc from 'date-fns/compareAsc'

type BeriketKontekst = {
    kontekst: KontekstDto
    aktiviteter: AktivitetDto[]
    opprettet: Date
    harError: boolean
    harWarning: boolean
}
const berikKontekster = (
    alleAktiviteter: AktivitetDto[],
    hendelseKontektster: [KontekstDto, number][]
): BeriketKontekst[] => {
    return hendelseKontektster.map(([kontekst, index]: [KontekstDto, number]) => {
        const aktiviteter = alleAktiviteter.filter((aktivitet) => aktivitet.kontekster.includes(index))
        const opprettet =
            aktiviteter
                .map((it) => parseISO(it.tidsstempel))
                .sort(compareAsc)
                .find(hasValue) ?? parseISO('1900-01-01')
        const harError = !!aktiviteter.find((it) => it.alvorlighetsgrad == 'ERROR')
        const harWarning = !!aktiviteter.find((it) => it.alvorlighetsgrad == 'WARN')
        return {
            kontekst,
            aktiviteter,
            opprettet,
            harError,
            harWarning,
        }
    })
}

export const Hendelser = React.memo(({ aktiviteter }: { aktiviteter: AktivitetDto[] }) => {
    const [visBareFeil, setVisBareFeil] = React.useState(false)
    const toggleVisBareFeil = () => setVisBareFeil(!visBareFeil)

    const kontekstFinnesiAktiviteter = (kontekst: KontekstDto, index: number): boolean =>
        !!aktiviteter.find((aktivitet) => aktivitet.kontekster.includes(index))

    const erMeldingsKontekst = (kontekst: KontekstDto) =>
        !!kontekst.kontekstMap.meldingsreferanseId && kontekst.kontekstType != 'GjenopptaBehandling'

    const aktivitetslogg = useAktivitetslogg()

    const hendelseKontektster = mapNotUndefined(
        aktivitetslogg.kontekster,
        (kontekst, index): [KontekstDto, number] | undefined => {
            if (kontekstFinnesiAktiviteter(kontekst, index)) {
                return [kontekst, index]
            } else return undefined
        }
    ).filter(([kontekst]) => erMeldingsKontekst(kontekst))


    //Dette er ikke helt bra, vi kan miste aktiviteter som bare er tilknyttet en melding. Det ville være bedre å hennte
    // aktiviteter for alle kontekstene assosiert med meldingsreferanse.
    const hendelseKontektsterUtenDuplikater = hendelseKontektster.filter(([tilDedup, dedupOriginalIndex]) => {
        const [konflikterendeKontekst] = hendelseKontektster
            .slice(dedupOriginalIndex + 1)
            .find(([it]) => it.kontekstMap.meldingsreferanseId === tilDedup.kontekstMap.meldingsreferanseId)
        ?? [undefined]
        if (!!konflikterendeKontekst) {
            console.warn(`Fant flere kontekster med samme referanseid (${tilDedup.kontekstMap.meldingsreferanseId}). Konteksttyper: ${tilDedup.kontekstType}, ${konflikterendeKontekst.kontekstType}`)
            return false
        }
        return true
    })

    const berikedeKontekster = berikKontekster(aktiviteter, hendelseKontektsterUtenDuplikater).sort((a, b) =>
        compareAsc(a.opprettet, b.opprettet)
    )
    return (
        <div className={styles.Hendelser}>
            <button onClick={toggleVisBareFeil}>Vis bare feil</button>
            {berikedeKontekster.map((it) => {
                return (
                    (!visBareFeil || it.harError || it.harWarning) && (
                        <Hendelse
                            aktiviteter={it.aktiviteter}
                            kontekst={it.kontekst}
                            key={it.kontekst.kontekstMap.meldingsreferanseId}
                        />
                    )
                )
            })}
        </div>
    )
})
export const aktiviteterForKontekst = (
    aktivitetslogg: AktivitetsloggDto,
    filter: (kontekst: KontekstDto) => boolean
): AktivitetDto[] => {
    const mapping = (y: KontekstDto, index: number) => filter(y) && index
    const konteksterIndex = mapNotUndefined(aktivitetslogg.kontekster, mapping)
    return aktivitetslogg.aktiviteter.filter((aktivitet) =>
        aktivitet.kontekster.find((kontekstI) => konteksterIndex.includes(kontekstI))
    )
}
