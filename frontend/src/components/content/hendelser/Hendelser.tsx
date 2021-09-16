import React from "react";
import {AktivitetDto, AktivitetsloggDto, KontekstDto} from "../../../state/dto";
import {useAktivitetslogg} from "../../../state/contexts";
import {mapNotUndefined} from "../../../utils";
import styles from "./Henelser.module.css";
import {Hendelse} from "./Hendelse";

export const Hendelser = React.memo(({aktiviteter}: { aktiviteter: AktivitetDto[] }) => {
    const kontekstFinnesiAktiviteter = (kontekst: KontekstDto, index: number): boolean =>
        !!aktiviteter.find((aktivitet) => aktivitet.kontekster.includes(index))

    const erMeldingsKontekst = (kontekst: KontekstDto) =>
        !!kontekst.kontekstMap.meldingsreferanseId && kontekst.kontekstType != 'GjenopptaBehandling'

    const aktivitetslogg = useAktivitetslogg()

    const hendelseKontektster: [KontekstDto, number][] = mapNotUndefined(aktivitetslogg.kontekster, (kontekst, index) => {
        if (kontekstFinnesiAktiviteter(kontekst, index) && erMeldingsKontekst(kontekst)) {
            return [kontekst, index]
        } else return undefined
    })

    return (
        <div className={styles.AktivitetListeView}>
            {hendelseKontektster.map(([kontekst, index]) => {
                const kontekstAktiviter = aktiviteter.filter((aktivitet) => aktivitet.kontekster.includes(index))
                return (
                    <Hendelse
                        aktiviteter={kontekstAktiviter}
                        kontekst={kontekst}
                        key={kontekst.kontekstMap.meldingsreferanseId}
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