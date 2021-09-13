import classNames from 'classnames'
import styles from './PersonTree.module.css'
import React from 'react'
import { ArbeidsgiverContext, idEqual, useArbeidsgiver, useId, usePerson, useVedtak, VedtakContext } from '../contexts'
import { useRecoilValue, useSetRecoilState } from 'recoil'
import { highligthState } from '../state'

export const PersonTree = React.memo(() => {
    const person = usePerson()
    return (
        <div className={classNames(styles.PersonTree)}>
            {person.arbeidsgivere.map(arbeidsgiver => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <ArbeidsgiverNode />
                </ArbeidsgiverContext.Provider>
            ))}
        </div>
    )
})

const useIsHighlighted = () => {
    const highlighted = useRecoilValue(highligthState)
    const id = useId()
    return highlighted && idEqual(id, highlighted)
}

const useToggleHighlighted = () => {
    const setHighlight = useSetRecoilState(highligthState)
    const isHighlighted = useIsHighlighted()
    const id = useId()
    return React.useMemo(
        () => () => {
            if (isHighlighted) setHighlight(undefined)
            else setHighlight(id)
        },
        [setHighlight, id, isHighlighted]
    )
}

const stopPropagation = (e: React.MouseEvent) => {
    e.stopPropagation()
}

const ArbeidsgiverNode = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()
    const setHighlight = useToggleHighlighted()
    const isHighlighted = useIsHighlighted()

    return (
        <div className={classNames(styles.ArbeidsgiverNode)} onClick={stopPropagation}>
            <p className={classNames(isHighlighted && styles.Highlighted)} onClick={setHighlight}>
                {arbeidsgiver.organisasjonsnummer}
            </p>
            {arbeidsgiver.vedtaksperioder.map(vedtak => (
                <VedtakContext.Provider value={vedtak} key={vedtak.id}>
                    <VedtaksNode />
                </VedtakContext.Provider>
            ))}
        </div>
    )
})

const VedtaksNode = React.memo(() => {
    const vedtak = useVedtak()
    const setHighlight = useToggleHighlighted()
    const isHighlighted = useIsHighlighted()
    return (
        <div className={classNames(styles.ArbeidsgiverNode)} onClick={stopPropagation}>
            <p className={classNames(isHighlighted && styles.Highlighted)} onClick={setHighlight}>
                {vedtak.fom} - {vedtak.tom}
            </p>
        </div>
    )
})
