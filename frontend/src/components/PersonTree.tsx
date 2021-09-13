import classNames from "classnames";
import styles from "./PersonTree.module.css";
import React from "react";
import {ArbeidsgiverContext, useArbeidsgiver, usePerson, useVedtak, VedtakContext} from "../contexts";
import {useRecoilState} from "recoil";
import {highligthState} from "../state";


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
    const [_, setHighlight] = useRecoilState(highligthState)

    const onClick = () => {
        setHighlight(arbeidsgiver.organisasjonsnummer);
    };

    return (
        <div className={classNames(styles.ArbeidsgiverNode)}  onClick={onClick}>
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