import classNames from 'classnames'
import styles from './PersonTree.module.css'
import React from 'react'
import {
    ArbeidsgiverContext,
    useArbeidsgiver,
    useId,
    useIsSelected,
    usePerson,
    useVedtak,
    VedtakContext,
} from '../../state/contexts'
import { useRecoilState, useSetRecoilState } from 'recoil'
import { expandedArbeidsgivereState, selectedState } from '../../state/state'
import { Next, Expand } from '@navikt/ds-icons'

export const PersonTree = React.memo(() => {
    const person = usePerson()

    const setExpandedArbeidsgivere = useSetRecoilState(expandedArbeidsgivereState)
    const expandAllArbeidsgivere = () =>
        setExpandedArbeidsgivere((_) => person.arbeidsgivere.map((arb) => arb.organisasjonsnummer))
    const closeAllArbeidsgivere = () => setExpandedArbeidsgivere((_) => [])

    return (
        <div className={classNames(styles.PersonTree)}>
            <button onClick={expandAllArbeidsgivere}>Åpne alle</button>
            <button onClick={closeAllArbeidsgivere}>Lukk alle</button>
            <SelectableTreeNode indent={0}>{person.aktørId}</SelectableTreeNode>
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <ArbeidsgiverNode />
                </ArbeidsgiverContext.Provider>
            ))}
        </div>
    )
})

const useSelect = () => {
    const setSelected = useSetRecoilState(selectedState)
    const id = useId()
    return React.useMemo(
        () => (e: React.MouseEvent) => {
            setSelected(id)
            e.stopPropagation()
        },
        [setSelected, id]
    )
}

const SelectableTreeNode = React.memo<React.PropsWithChildren<{ indent: number }>>(({ indent = 0, children }) => {
    const isSelected = useIsSelected()
    const select = useSelect()
    return (
        <div
            style={{ marginLeft: `${indent * 0.9}em`, cursor: 'pointer' }}
            className={classNames(styles.TreeNode, isSelected && styles.Highlighted)}
            onClick={select}
        >
            {children}
        </div>
    )
})

const ArbeidsgiverNode = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()

    const [expandedArbeidsgivere, setExpandedArbeidsgivere] = useRecoilState(expandedArbeidsgivereState)
    const isExpanded = expandedArbeidsgivere.includes(arbeidsgiver.organisasjonsnummer)

    const toggleExpandArbeidsgiver = () => {
        const isExpanded = expandedArbeidsgivere.includes(arbeidsgiver.organisasjonsnummer)
        if (isExpanded) {
            setExpandedArbeidsgivere((expandedArbeidsgivere) =>
                expandedArbeidsgivere.filter(
                    (expandedOrgnummer) => expandedOrgnummer != arbeidsgiver.organisasjonsnummer
                )
            )
        } else {
            setExpandedArbeidsgivere((expandedArbeidsgivere) => [
                ...expandedArbeidsgivere,
                arbeidsgiver.organisasjonsnummer,
            ])
        }
    }

    return (
        <>
            <div className={styles.ArbeidsgiverNode}>
                <ExpandToggle isExpanded={isExpanded} onClick={() => toggleExpandArbeidsgiver()} />
                <SelectableTreeNode indent={0}>{arbeidsgiver.organisasjonsnummer}</SelectableTreeNode>
            </div>
            {isExpanded &&
                arbeidsgiver.vedtaksperioder.map((vedtak) => (
                    <VedtakContext.Provider value={vedtak} key={vedtak.id}>
                        <VedtaksNode />
                    </VedtakContext.Provider>
                ))}
        </>
    )
})

const ExpandToggle = React.memo<React.PropsWithoutRef<{ onClick: () => void; isExpanded: boolean }>>((props) => {
    return (
        <button onClick={props.onClick} className={styles.ExpandArbeidsgiverButton}>
            {props.isExpanded ? <Expand /> : <Next />}
        </button>
    )
})

const VedtaksNode = React.memo(() => {
    const vedtak = useVedtak()
    return (
        <SelectableTreeNode indent={3}>
            {vedtak.fom} - {vedtak.tom}
            <br />
            {vedtak.tilstand}
        </SelectableTreeNode>
    )
})
