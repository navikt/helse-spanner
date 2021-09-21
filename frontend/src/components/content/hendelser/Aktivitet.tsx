import React from "react";
import {AktivitetDto} from "../../../state/dto";
import styles from "./Aktivitet.module.css";
import commonStyles from "../../Common.module.css"
import classNames from "classnames";

type AktivitetViewProps = {
    aktivitet: AktivitetDto
}
export const Aktivitet: React.FC<AktivitetViewProps> = React.memo(({aktivitet}: { aktivitet: AktivitetDto }) => {
    const isWarning = aktivitet.alvorlighetsgrad == "WARN"
    const isError = aktivitet.alvorlighetsgrad == "ERROR"
    return <div className={styles.AktivitetView}>
        <div className={classNames(styles.HeaderLinje, isWarning && commonStyles.Warning, isError && commonStyles.Error)}>
            <div className={styles.AktivitetViewAlvorlighetsgradLabel}>{aktivitet.alvorlighetsgrad}</div>
            <div className={styles.AktivitetViewMeldingText}>{aktivitet.melding}</div>
        </div>
    </div>
})

Aktivitet.displayName="Aktivitet"