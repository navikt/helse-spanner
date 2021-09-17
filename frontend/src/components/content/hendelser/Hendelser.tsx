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
    opprettet: Date,
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
        const harError =
            !!aktiviteter
                .find(it => it.alvorlighetsgrad == "ERROR")
        const harWarning =
            !!aktiviteter
                .find(it => it.alvorlighetsgrad == "WARN")
        return {
            kontekst,
            aktiviteter,
            opprettet,
            harError,
            harWarning
        }
    })
}

export const Hendelser = React.memo(({ aktiviteter }: { aktiviteter: AktivitetDto[] }) => {
    const [visBareFeil, setVisBareFeil] = React.useState(false)
    const toggleVisBareFeil = () =>
        setVisBareFeil(!visBareFeil)

    const kontekstFinnesiAktiviteter = (kontekst: KontekstDto, index: number): boolean =>
        !!aktiviteter.find((aktivitet) => aktivitet.kontekster.includes(index))

    const erMeldingsKontekst = (kontekst: KontekstDto) =>
        !!kontekst.kontekstMap.meldingsreferanseId && kontekst.kontekstType != 'GjenopptaBehandling'

    const aktivitetslogg = useAktivitetslogg()

    const hendelseKontektster: [KontekstDto, number][] = mapNotUndefined(
        aktivitetslogg.kontekster,
        (kontekst, index) => {
            if (kontekstFinnesiAktiviteter(kontekst, index) && erMeldingsKontekst(kontekst)) {
                return [kontekst, index]
            } else return undefined
        }
    )
    const berikedeKontekster = berikKontekster(aktiviteter, hendelseKontektster).sort((a, b) =>
        compareAsc(a.opprettet, b.opprettet)
    )
    return (
        <div className={styles.Hendelser}>
            <button onClick={toggleVisBareFeil}>Vis bare feil</button>
            {berikedeKontekster.map((it) => {
                return (
                    (!visBareFeil || it.harError || it.harWarning) &&<Hendelse
                        aktiviteter={it.aktiviteter}
                        kontekst={it.kontekst}
                        key={it.kontekst.kontekstMap.meldingsreferanseId}
                    />
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
