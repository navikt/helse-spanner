import parseISO from "date-fns/parseISO";
import compareAsc from "date-fns/compareAsc";
import React from "react";
import {somNorskDato} from "../i18n";
import SelectableTreeNode from "./SelectableTreeNode";
import classNames from "classnames";
import styles from "./PersonTree.module.css";
import {ArbeidsgiverDto, UtbetalingDto} from "../../state/dto";

interface UtbetalingerProps {
    arbeidsgiver: ArbeidsgiverDto,
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
}

export const Utbetalinger = ({ arbeidsgiver, valgteTing, toggleValgtTing } : UtbetalingerProps) => {
    let utbetalinger: [JSX.Element, Date][] = arbeidsgiver.utbetalinger.map((utbetaling) => [
        <div key={utbetaling.id}>
            {utbetaling.status == 'FORKASTET' ? <ForkastetUtbetalingNode utbetaling={utbetaling} valgteTing={valgteTing} vedValg={ toggleValgtTing } /> : <UtbetalingsNode utbetaling={utbetaling} valgteTing={valgteTing} vedValg={ toggleValgtTing }/>}
        </div>,
        parseISO(utbetaling.fom),
    ])

    const sorterteUtbetalinger = [...utbetalinger].sort(([_, a], [ignore, b]) => compareAsc(a, b))
    return <>{sorterteUtbetalinger.map(([utbetaling]) => utbetaling)}</>
}

Utbetalinger.displayName = 'Utbetalinger'

const UtbetalingsNode = ({ utbetaling, valgteTing, vedValg }: { utbetaling: UtbetalingDto, valgteTing: string[], vedValg: (e: React.MouseEvent, ting: string) => void }) => {
    const [fom, tom] = [utbetaling.fom, utbetaling.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.LøvNode, styles.Utbetaling)} valgteTing={valgteTing} ting={utbetaling.id} indent={1.2} vedValg={vedValg }>
            <div>
                <i>Utbetaling</i> 💰
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
        <SelectableTreeNode className={classNames(styles.LøvNode, styles.ForkastetUtbetaling)} valgteTing={valgteTing} ting={utbetaling.id} indent={1.2} vedValg={vedValg}>
            <div className={styles.ForkastetLabel}>
                <i>Utbetaling</i> 💰
            </div>
            <div>
                {fom} - {tom}
            </div>
            <span className={styles.TilstandText}>{utbetaling.status}</span>
        </SelectableTreeNode>
    )
}