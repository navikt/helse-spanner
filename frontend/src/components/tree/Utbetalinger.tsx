import parseISO from "date-fns/parseISO";
import React, {useState} from "react";
import {somNorskDato} from "../i18n";
import SelectableTreeNode from "./SelectableTreeNode";
import classNames from "classnames";
import styles from "./PersonTree.module.css";
import {ArbeidsgiverDto, UtbetalingDto} from "../../state/dto";
import {ExpandToggle} from "./ExpandToggle";

interface UtbetalingerProps {
    arbeidsgiver: ArbeidsgiverDto,
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
}

interface GruppertUtbetaling {
    korrelasjonsId: string,
    utbetalinger: UtbetalingDto[]
}

function grupperUtbetalinger(arbeidsgiver: ArbeidsgiverDto) {
    let grupperteUtbetalinger = [] as GruppertUtbetaling[]
    arbeidsgiver.utbetalinger.forEach((utbetaling) => {
        const idx = grupperteUtbetalinger.findIndex((it) => it.korrelasjonsId === utbetaling.korrelasjonsId)
        if (idx === -1) return grupperteUtbetalinger = [...grupperteUtbetalinger, {
            korrelasjonsId: utbetaling.korrelasjonsId,
            utbetalinger: [utbetaling]
        }]
        grupperteUtbetalinger[idx].utbetalinger = [...grupperteUtbetalinger[idx].utbetalinger, utbetaling]
    })
    return grupperteUtbetalinger
}

export const Utbetalinger = ({ arbeidsgiver, valgteTing, toggleValgtTing } : UtbetalingerProps) => {
    const grupperteUtbetalinger = grupperUtbetalinger(arbeidsgiver)
    let utbetalinger = grupperteUtbetalinger.map((gruppe) => {
        return <GruppertUtbetaling key={gruppe.korrelasjonsId} gruppe={gruppe} valgteTing={valgteTing} toggleValgtTing={toggleValgtTing} />
    })
    return <>{utbetalinger}</>
}

Utbetalinger.displayName = 'Utbetalinger'

function GruppertUtbetaling({ gruppe, valgteTing, toggleValgtTing }: { gruppe: GruppertUtbetaling, valgteTing: string[], toggleValgtTing: (e: React.MouseEvent, ting: string) => void }) {
    const [isExpanded, setIsExpanded] = useState(true)
    const utbetalinger = gruppe.utbetalinger
        .sort((a, b) => {
            return parseISO(b.tidsstempel).getTime() - parseISO(a.tidsstempel).getTime()
        })
        .map((utbetaling) =>
            utbetaling.status == 'FORKASTET' ?
                <ForkastetUtbetalingNode key={utbetaling.id} utbetaling={utbetaling} valgteTing={valgteTing} vedValg={toggleValgtTing}/>
            :
                <UtbetalingsNode key={utbetaling.id} utbetaling={utbetaling} valgteTing={valgteTing} vedValg={toggleValgtTing}/>
        )
    const [fom, tom] = periodeForUtbetalinger(gruppe.utbetalinger)
    return <>
        <SelectableTreeNode indent={1.2} valgteTing={valgteTing} ting={gruppe.korrelasjonsId} vedValg={toggleValgtTing}>
            <ExpandToggle isExpanded={isExpanded} onClick={() => setIsExpanded((previous) => !previous) }/>
            <i>💰 {fom} - {tom}</i>
        </SelectableTreeNode>
        { isExpanded && utbetalinger }
    </>
}

function periodeForUtbetalinger(utbetalinger: UtbetalingDto[]) {
    let fom: Date, tom: Date
    utbetalinger.forEach((it) => {
        const f = parseISO(it.fom)
        const t = parseISO(it.tom)
        if (!fom || f < fom) fom = f
        if (!tom || t > tom) tom = t
    })
    return [fom!.toLocaleDateString("nb-NO"), tom!.toLocaleDateString("nb-NO")]
}

const UtbetalingsNode = ({utbetaling, valgteTing, vedValg}: {
    utbetaling: UtbetalingDto,
    valgteTing: string[],
    vedValg: (e: React.MouseEvent, ting: string) => void
}) => {
    const [fom, tom] = [utbetaling.fom, utbetaling.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.LøvNode, styles.Utbetaling)} valgteTing={valgteTing} ting={utbetaling.id} indent={1.6} vedValg={vedValg }>
            <div>
                <i>{utbetaling.type}</i> 💰
            </div>
            <div>
                {fom} - {tom}
            </div>
            <span className={styles.TilstandText}>{utbetaling.status}</span>
        </SelectableTreeNode>
    )
}

const ForkastetUtbetalingNode = ({ utbetaling, valgteTing, vedValg }: { utbetaling: UtbetalingDto, valgteTing: string[], vedValg: (e: React.MouseEvent, ting: string) => void }) => {
    const [fom, tom] = [utbetaling.fom, utbetaling.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.LøvNode, styles.ForkastetUtbetaling)} valgteTing={valgteTing} ting={utbetaling.id} indent={1.6} vedValg={vedValg}>
            <div className={styles.ForkastetLabel}>
                <i>{utbetaling.type}</i> 💰
            </div>
            <div>
                {fom} - {tom}
            </div>
            <span className={styles.TilstandText}>{utbetaling.status}</span>
        </SelectableTreeNode>
    )
}