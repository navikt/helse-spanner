import {useArbeidsgiver, useUtbetaling, UtbetalingContext} from "../../state/contexts";
import parseISO from "date-fns/parseISO";
import compareAsc from "date-fns/compareAsc";
import React from "react";
import {somNorskDato} from "../i18n";
import SelectableTreeNode from "./SelectableTreeNode";
import classNames from "classnames";
import styles from "./PersonTree.module.css";

export const Utbetalinger = () => {
    const arbeidsgiver = useArbeidsgiver()
    let utbetalinger: [JSX.Element, Date][] = arbeidsgiver.utbetalinger.map((utbetaling) => [
        <UtbetalingContext.Provider value={utbetaling} key={utbetaling.id}>
            {utbetaling.status == 'FORKASTET' ? <ForkastetUtbetalingNode/> : <UtbetalingsNode/>}
        </UtbetalingContext.Provider>,
        parseISO(utbetaling.fom),
    ])

    const sorterteUtbetalinger = [...utbetalinger].sort(([_, a], [ignore, b]) => compareAsc(a, b))
    return <>{sorterteUtbetalinger.map(([utbetaling]) => utbetaling)}</>
}

Utbetalinger.displayName = 'Utbetalinger'

const UtbetalingsNode = () => {
    const utbetaling = useUtbetaling()
    const [fom, tom] = [utbetaling.fom, utbetaling.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.LøvNode, styles.Utbetaling)} indent={1.2}>
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

const ForkastetUtbetalingNode = () => {
    const utbetaling = useUtbetaling()
    const [fom, tom] = [utbetaling.fom, utbetaling.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.LøvNode, styles.ForkastetUtbetaling)} indent={1.2}>
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