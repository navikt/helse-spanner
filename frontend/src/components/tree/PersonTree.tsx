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
import { useSetRecoilState } from 'recoil'
import { selectedState } from '../../state/state'

export const PersonTree = React.memo(() => {
    const person = usePerson()
    return (
        <div className={classNames(styles.PersonTree)}>
            <SelectableTreeNode indent={0}>
                {person.aktørId}
            </SelectableTreeNode>
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
        () => (e:React.MouseEvent) => {
            setSelected(id)
            e.stopPropagation()
        },
        [setSelected, id]
    )
}

const SelectableTreeNode = React.memo<React.PropsWithChildren<{indent: number }>>(({ indent = 0, children }) => {
    const isSelected = useIsSelected()
    const select = useSelect()
    return (
        <div
            style={{ marginLeft: `${indent * 0.9}em` }}
            className={classNames(styles.TreeNode, isSelected && styles.Highlighted)}
            onClick={select}
        >
            {children}
        </div>
    )
})

const ArbeidsgiverNode = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()

    return (
        <>
        <SelectableTreeNode indent={1}>
                {arbeidsgiver.organisasjonsnummer}
        </SelectableTreeNode>
            {arbeidsgiver.vedtaksperioder.map((vedtak) => (
                <VedtakContext.Provider value={vedtak} key={vedtak.id}>
                    <VedtaksNode />
                </VedtakContext.Provider>
            ))}
        </>
    )
})

const VedtaksNode = React.memo(() => {
    const vedtak = useVedtak()
    return (
        <SelectableTreeNode indent={2}>
            {vedtak.fom} - {vedtak.tom}
            <br />
            {vedtak.tilstand}
        </SelectableTreeNode>

    )
})
