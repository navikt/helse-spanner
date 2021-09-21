import classNames from 'classnames'
import styles from './PersonTree.module.css'
import React from 'react'
import {
    ArbeidsgiverContext,
    ForkastetVedtaksperiodeContext,
    useArbeidsgiver,
    useForkastetVedtaksperiode,
    useId,
    useIsSelected,
    usePerson,
    useVedtak,
    VedtakContext,
} from '../../state/contexts'
import { useRecoilState, useSetRecoilState } from 'recoil'
import { expandedArbeidsgivereState, selectedState } from '../../state/state'
import { Next, Expand } from '@navikt/ds-icons'
import parseISO from 'date-fns/parseISO'
import compareAsc from 'date-fns/compareAsc'

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

PersonTree.displayName = "PersonTree"

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

interface SelectableTreeNodeProps extends React.HTMLAttributes<HTMLDivElement> {indent: number}

// export const Card: React.FC<CardProps> = ({ className, children, ...rest }) => (

const SelectableTreeNode= React.memo<SelectableTreeNodeProps>(({ className, indent = 0, children, ...rest }) => {
    const isSelected = useIsSelected()
    const select = useSelect()
    return (
        <div
            style={{ marginLeft: `${indent * 0.9}em`, cursor: 'pointer' }}
            className={classNames(styles.TreeNode, isSelected && styles.Highlighted, className)}
            onClick={select}
            {...rest}
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
            {isExpanded && <Vedtaksperioder />}
        </>
    )
})

const Vedtaksperioder = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()
    let vedtaksperioder: [JSX.Element, Date][] = arbeidsgiver.vedtaksperioder.map((vedtak) => [
        <VedtakContext.Provider value={vedtak} key={vedtak.id}>
            <VedtaksNode />
        </VedtakContext.Provider>,
        parseISO(vedtak.fom),
    ])
    let forkastedeVedtaksperioder: [JSX.Element, Date][] = arbeidsgiver.forkastede.map((forkastelse) => [
        <ForkastetVedtaksperiodeContext.Provider value={forkastelse.vedtaksperiode} key={forkastelse.vedtaksperiode.id}>
            <ForkastetVedtaksNode />
        </ForkastetVedtaksperiodeContext.Provider>,
        parseISO(forkastelse.vedtaksperiode.fom),
    ])
    const sorterteVedtak = [...vedtaksperioder, ...forkastedeVedtaksperioder].sort(([_, a], [ignore, b]) =>
        compareAsc(a, b)
    )
    return (
        <>
            {sorterteVedtak.map(([vedtak]) => vedtak)}
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
        <SelectableTreeNode indent={1.2}>
            {vedtak.fom} - {vedtak.tom}
            <br />
            {vedtak.tilstand}
        </SelectableTreeNode>
    )
})

const ForkastetVedtaksNode = React.memo(() => {
    const vedtak = useForkastetVedtaksperiode()
    return (
        <SelectableTreeNode className={styles.Forkastet} indent={1.2}>
            <div className={classNames(styles.ForkastetLabel)}>Forkastet</div>
            {" "}
            {vedtak.fom} - {vedtak.tom}
            <br />
            {vedtak.tilstand}
        </SelectableTreeNode>
    )
})
