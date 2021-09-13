import classNames from "classnames";
import styles from "./PersonTree.module.css";
import React from "react";
import {ArbeidsgiverContext, useArbeidsgiver, usePerson, useVedtak, VedtakContext} from "../contexts";
import {useRecoilState} from "recoil";
import {highligthState} from "../state";


export const PersonTree = React.memo(() => {
    const person = usePerson()
    return (
        <div className={classNames(styles.PersonTree)} >
            {
                person.arbeidsgivere.map(arbeidsgiver => (
                    <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.organisasjonsnummer}>
                        <ArbeidsgiverNode />
                    </ArbeidsgiverContext.Provider>
                ))
            }
        </div>
    )
})

const ArbeidsgiverNode = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()
    const [ignore, setHighlight] = useRecoilState(highligthState)

    const onClick = () => {
        setHighlight(arbeidsgiver.organisasjonsnummer);
    };

    return (
        <div className={classNames(styles.ArbeidsgiverNode)}  onClick={onClick}>
            {arbeidsgiver.organisasjonsnummer}
            {
                arbeidsgiver.vedtaksperioder.map(vedtak => (
                    <VedtakContext.Provider value={vedtak} key={vedtak.id}>
                        <VedtaksNode />
                    </VedtakContext.Provider>
                ))
            }
        </div>
    )
})

const VedtaksNode = React.memo(() => {
    const vedtak = useVedtak()
    return (
        <div className={classNames(styles.ArbeidsgiverNode)} >
            {vedtak.fom} - {vedtak.tom}
        </div>
    )
})