import classNames from "classnames";
import styles from "./PersonTree.module.css";
import React from "react";
import {ArbeidsgiverContext, useArbeidsgiver, usePerson, useVedtak, VedtakContext} from "../contexts";


export function PersonTree() {
    const person = usePerson()
    return (
        <div className={classNames(styles.PersonTree)} >
            {
                person.arbeidsgivere.map(arbeidsgiver => (
                    <ArbeidsgiverContext.Provider value={arbeidsgiver}>
                        <ArbeidsgiverNode />
                    </ArbeidsgiverContext.Provider>
                ))
            }
        </div>
    )
}

function ArbeidsgiverNode() {
    const arbeidsgiver = useArbeidsgiver()
    return (
        <div className={classNames(styles.ArbeidsgiverNode)} >
            {arbeidsgiver.organisasjonsnummer}
            {
                arbeidsgiver.vedtaksperioder.map(vedtak => (
                    <VedtakContext.Provider value={vedtak}>
                        <VedtaksNode />
                    </VedtakContext.Provider>
                ))
            }
        </div>
    )
}

function VedtaksNode() {
    const vedtak = useVedtak()
    return (
        <div className={classNames(styles.ArbeidsgiverNode)} >
            {vedtak.fom} - {vedtak.tom}
        </div>
    )
}