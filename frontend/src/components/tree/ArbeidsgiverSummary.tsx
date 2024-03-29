import styles from "./PersonTree.module.css";
import React from "react";
import {ArbeidsgiverDto} from "../../state/dto";

export const ArbeidsgiverSummary = ({ arbeidsgiver }: { arbeidsgiver: ArbeidsgiverDto }) => {
    const antallVedtaksperioder = arbeidsgiver.vedtaksperioder.length
    const antallForkastedeVedtaksperioder = arbeidsgiver.forkastede.length
    const antallUtbetalinger = arbeidsgiver.utbetalinger.length

    return (
        <div className={styles.ArbeidsgiverSummary}>
            {arbeidsgiver.organisasjonsnummer}
            <span className={styles.tall}>
                <span className={styles.antallAktive}>{antallVedtaksperioder}</span>
                &nbsp;-&nbsp;<span className={styles.antallForkastede}>{antallForkastedeVedtaksperioder}</span>
                &nbsp;|&nbsp;<span className={styles.antallUtbetalinger}>{antallUtbetalinger}</span>
            </span>
        </div>
    )
}