import React from 'react'
import {AktivitetDto, AktivitetV2Dto} from '../../../state/dto'
import styles from './Aktivitet.module.css'
import commonStyles from '../../Common.module.css'
import classNames from 'classnames'

type AktivitetViewProps = {
    aktivitet: AktivitetV2Dto
}
export const Aktivitet: React.FC<AktivitetViewProps> = React.memo(({ aktivitet }: { aktivitet: AktivitetV2Dto }) => {
    const isWarning = aktivitet.nivå == 'VARSEL'
    const isError = aktivitet.nivå == 'FUNKSJONELL_FEIL'
    console.log(`aktivitet interessant = ${aktivitet.interessant}: ${JSON.stringify(aktivitet)}`)
    return (
        <div className={styles.AktivitetView}>
            <div
                className={classNames(
                    styles.HeaderLinje,
                    isWarning && commonStyles.Warning,
                    isError && commonStyles.Error,
                    !aktivitet.interessant && styles.IkkeInteressant
                )}
            >
                <div className={styles.AktivitetViewAlvorlighetsgradLabel}>{aktivitet.nivå.replace("FUNKSJONELL_FEIL", "FEIL")}</div>
                <div className={styles.AktivitetViewMeldingText}>{aktivitet.tekst}</div>
            </div>
        </div>
    )
})

Aktivitet.displayName = 'Aktivitet'
