import React from 'react'
import { backendFeil, finnesIkke, httpFeil } from '../external/feil'
import classNames from 'classnames'
import styles from './person/Person.module.css'

export const Feilmelding = ({ feil }: { feil: any }) => {
    let feiltekst, ikon

    if (feil instanceof httpFeil) {
        feiltekst = `Fikk ikke kontakt med server: ${feil.message}`
        ikon = '✂️🔌'
    } else if (feil instanceof finnesIkke) {
        feiltekst = `Ressursen finnes ikke i spleis.` + (!!feil.feilId ? ` FeilId: ${feil.feilId}` : '')
        ikon = '🤷'
    } else if (feil instanceof backendFeil) {
        feiltekst = `Feil fra backend. Status: ${feil.status}` + (!!feil.feilId ? ` FeilId: ${feil.feilId}` : '')
        ikon = '😢'
    } else {
        feiltekst = `Noe gikk galt: ${feil.message}`
        ikon = '☠️'
    }
    return (
        <div className={classNames(styles.FeilMelding)} data-testid="feil-melding">
            <span className={styles.FeilIkon}>{ikon}</span>
            {feiltekst}
        </div>
    )
}

Feilmelding.displayName = 'Feilmelding'
