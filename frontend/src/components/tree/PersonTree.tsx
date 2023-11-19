import classNames from 'classnames'
import styles from './PersonTree.module.css'
import commonStyles from '../Common.module.css'
import React from 'react'
import {
    ArbeidsgiverContext,
    ForkastetVedtaksperiodeContext,
    idEqual,
    useArbeidsgiver,
    useForkastetVedtaksperiode,
    useId,
    useIsSelected,
    usePerson,
    useUtbetaling,
    useVedtak,
    UtbetalingContext,
    VedtakContext,
} from '../../state/contexts'
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil'
import { expandedArbeidsgivereState, hideForkastedeVedtakState, selectedState } from '../../state/state'
import { Next, Expand } from '@navikt/ds-icons'
import parseISO from 'date-fns/parseISO'
import compareAsc from 'date-fns/compareAsc'
import { somNorskDato } from '../i18n'
import { personSporingUrl, tilstandsmaskinSporingUrl } from './links'
import {ArbeidsgiverDto, PersonDto, VedtakDto} from "../../state/dto";

const SporingLenke: React.FC<{ url: string }> = ({ url }) => (
    <a href={url} target="_blank">
        🔎
    </a>
)
const KopierPersonPåminnelseJson: React.FC<{ person: PersonDto }> = ({ person }) => (
    <span onClick={() => { navigator.clipboard.writeText(`{ 
    "@event_name": "person_påminnelse",
    "fødselsnummer": "${person.fødselsnummer}",
    "aktørId": "${person.aktørId}"
}`) }}>
        ⏰
    </span>
)
const KopierVedtaksperiodePåminnelseJson: React.FC<{ person: PersonDto, arbeidsgiver: ArbeidsgiverDto, vedtak: VedtakDto }> = ({ person, arbeidsgiver, vedtak }) => (
    <span onClick={() => { navigator.clipboard.writeText(`{ 
    "@event_name": "påminnelse",
    "fødselsnummer": "${person.fødselsnummer}",
    "aktørId": "${person.aktørId}",
    "organisasjonsnummer": "${arbeidsgiver.organisasjonsnummer}",
    "vedtaksperiodeId": "${vedtak.id}",
    "tilstand": "${vedtak.tilstand}",
    "påminnelsestidspunkt": "{{now}}",
    "nestePåminnelsestidspunkt": "{{now+1h}}",
    "tilstandsendringstidspunkt": "{{now-1h}}",
    "antallGangerPåminnet": 1,
    "ønskerReberegning": false
}`) }}>
        ⏰
    </span>
)

export const PersonTree = () => {
    const person = usePerson()

    const setExpandedArbeidsgivere = useSetRecoilState(expandedArbeidsgivereState)
    const expandAllArbeidsgivere = () =>
        setExpandedArbeidsgivere((_) => person.arbeidsgivere.map((arb) => arb.organisasjonsnummer))
    const closeAllArbeidsgivere = () => setExpandedArbeidsgivere((_) => [])

    const [hideForkastedeVedtak, setHideForkastedeVedtak] = useRecoilState(hideForkastedeVedtakState)
    const toggleHideForkastedeVedtak = () => {
        setHideForkastedeVedtak((state) => !state)
    }

    return (
        <div className={classNames(styles.PersonTree)}>
            <button onClick={expandAllArbeidsgivere}>Åpne alle</button>
            <button onClick={closeAllArbeidsgivere}>Lukk alle</button>
            <button
                className={classNames(hideForkastedeVedtak && commonStyles.AktivKnapp)}
                onClick={toggleHideForkastedeVedtak}
            >
                Skjul forkastede
            </button>
            <SelectableTreeNode indent={0} className={styles.PersonNode}>
                <div>
                    <span>{person.aktørId}</span>
                    <div>
                        <SporingLenke url={personSporingUrl(person.aktørId)} />
                        <KopierPersonPåminnelseJson person={person} />
                    </div>
                </div>
            </SelectableTreeNode>
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <ArbeidsgiverNode />
                </ArbeidsgiverContext.Provider>
            ))}
        </div>
    )
}
PersonTree.displayName = 'PersonTree'

const useSelect = () => {
    const [selected, setSelected] = useRecoilState(selectedState)
    const isSelected = useIsSelected()
    const id = useId()
    const toggleSelect = () => {
        if (!isSelected) setSelected([...selected, id])
        else setSelected(selected.filter((it) => !idEqual(it, id)))
    }
    const setOnlySelected = () => {
        setSelected([id])
    }
    return React.useMemo(
        () => (e: React.MouseEvent) => {
            if (e.ctrlKey || e.metaKey) toggleSelect()
            else setOnlySelected()
            e.stopPropagation()
        },
        [setSelected, id, selected]
    )
}

interface SelectableTreeNodeProps extends React.HTMLAttributes<HTMLDivElement> {
    indent: number
}

const SelectableTreeNode = ({ className, indent = 0, children, ...rest }: SelectableTreeNodeProps) => {
    const selected = useIsSelected()
    const select = useSelect()
    return (
        <div
            style={{ marginLeft: `${indent * 0.9}em`, background: selected, cursor: 'pointer' }}
            className={classNames(styles.TreeNode, !!selected && styles.Highlighted, className)}
            onClick={select}
            {...rest}
        >
            {children}
        </div>
    )
}
SelectableTreeNode.displayName = 'SelectableTreeNode'

const ArbeidsgiverNode = () => {
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
                <SelectableTreeNode indent={0}>
                    <ArbeidsgiverSummary />
                </SelectableTreeNode>
            </div>
            {isExpanded && (
                <>
                    <Vedtaksperioder />
                    <Utbetalinger />
                </>
            )}
        </>
    )
}
ArbeidsgiverNode.displayName = 'ArbeidsgiverNode'

const ArbeidsgiverSummary = (): JSX.Element => {
    const arbeidsgiver = useArbeidsgiver()

    const antallVedtaksperioder = arbeidsgiver.vedtaksperioder.length
    const antallForkastedeVedtaksperioder = arbeidsgiver.forkastede.length
    const antallUtbetalinger = arbeidsgiver.utbetalinger.length

    return (
        <div className={styles.ArbeidsgiverSummary}>
            {arbeidsgiver.organisasjonsnummer}
            <span className={styles.tall}>
                <span className={styles.antallAktive}>{antallVedtaksperioder}</span>
                &nbsp;-&nbsp;<span className={styles.antallForkastede}>{antallForkastedeVedtaksperioder}</span>
                &nbsp;|&nbsp;<span className={styles.antallUtbetalinger}>{antallUtbetalinger}</span>
            </span>
        </div>
    )
}

const Vedtaksperioder = () => {
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
    return <>{sorterteVedtak.map(([vedtak]) => vedtak)}</>
}
Vedtaksperioder.displayName = 'Vedtaksperioder'

const ExpandToggle = (props: React.PropsWithoutRef<{ onClick: () => void; isExpanded: boolean }>) => {
    return (
        <button onClick={props.onClick} className={styles.ExpandArbeidsgiverButton}>
            {props.isExpanded ? <Expand /> : <Next />}
        </button>
    )
}
ExpandToggle.displayName = 'ExpandToggle'

const VedtaksNode = () => {
    const vedtak = useVedtak()
    const [fom, tom] = [vedtak.fom, vedtak.tom].map(somNorskDato)
    return (
        <SelectableTreeNode indent={1.2} className={styles.LøvNode}>
            <div className={styles.VedtakNodeHeader}>
                <span>
                    {fom} - {tom}
                </span>
                <SporingLenke url={tilstandsmaskinSporingUrl(vedtak.id)} />
                <KopierVedtaksperiodePåminnelseJson person={usePerson()} arbeidsgiver={useArbeidsgiver()} vedtak={vedtak} />
            </div>
            <span className={styles.TilstandText}>{vedtak.tilstand}</span>
        </SelectableTreeNode>
    )
}
VedtaksNode.displayName = 'VedtaksNode'

const ForkastetVedtaksNode = () => {
    const hideForkastedeVedtak = useRecoilValue(hideForkastedeVedtakState)
    if (hideForkastedeVedtak) {
        return null
    }
    const vedtak = useForkastetVedtaksperiode()
    const [fom, tom] = [vedtak.fom, vedtak.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.Forkastet, styles.LøvNode)} indent={1.2}>
            <div className={styles.ForkastetLabel}>
                {fom} - {tom}
            </div>
            <span className={styles.TilstandText}>{vedtak.tilstand}</span>
        </SelectableTreeNode>
    )
}
ForkastetVedtaksNode.displayName = 'ForkastetVedtaksNode'

const Utbetalinger = () => {
    const arbeidsgiver = useArbeidsgiver()
    let utbetalinger: [JSX.Element, Date][] = arbeidsgiver.utbetalinger.map((utbetaling) => [
        <UtbetalingContext.Provider value={utbetaling} key={utbetaling.id}>
            {utbetaling.status == 'FORKASTET' ? <ForkastetUtbetalingNode /> : <UtbetalingsNode />}
        </UtbetalingContext.Provider>,
        parseISO(utbetaling.fom),
    ])

    const sorterteUtbetalinger = [...utbetalinger].sort(([_, a], [ignore, b]) => compareAsc(a, b))
    return <>{sorterteUtbetalinger.map(([utbetaling]) => utbetaling)}</>
}
Utbetalinger.displayName = 'Utbetalinger'

const UtbetalingsNode = () => {
    const utbetaling = useUtbetaling()
    const [fom, tom] = [utbetaling.fom, utbetaling.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.LøvNode, styles.Utbetaling)} indent={1.2}>
            <div>
                <i>Utbetaling</i> 💰
            </div>
            <div>
                {fom} - {tom}
            </div>
            <span className={styles.TilstandText}>{utbetaling.status}</span>
        </SelectableTreeNode>
    )
}

const ForkastetUtbetalingNode = () => {
    const utbetaling = useUtbetaling()
    const [fom, tom] = [utbetaling.fom, utbetaling.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.LøvNode, styles.ForkastetUtbetaling)} indent={1.2}>
            <div className={styles.ForkastetLabel}>
                <i>Utbetaling</i> 💰
            </div>
            <div>
                {fom} - {tom}
            </div>
            <span className={styles.TilstandText}>{utbetaling.status}</span>
        </SelectableTreeNode>
    )
}
