import {useArbeidsgiver} from "../../state/contexts";
import {useRecoilState} from "recoil";
import {expandedArbeidsgivereState} from "../../state/state";
import styles from "./PersonTree.module.css";
import SelectableTreeNode from "./SelectableTreeNode";
import React from "react";
import {ExpandToggle} from "./ExpandToggle";
import {ArbeidsgiverSummary} from "./ArbeidsgiverSummary";
import {Vedtaksperioder} from "./Vedtaksperioder";
import {Utbetalinger} from "./Utbetalinger";
import {ArbeidsgiverDto, FokastetVedtaksperiodeDto, UtbetalingDto, VedtakDto} from "../../state/dto";

interface ArbeidsgiverNodeProps {
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
}

export const ArbeidsgiverNode = ({valgteTing, toggleValgtTing} : ArbeidsgiverNodeProps) => {
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
                <ExpandToggle isExpanded={isExpanded} onClick={() => toggleExpandArbeidsgiver()}/>
                <SelectableTreeNode indent={0} valgteTing={valgteTing} ting={arbeidsgiver.id} vedValg={toggleValgtTing}>
                    <ArbeidsgiverSummary/>
                </SelectableTreeNode>
            </div>
            {isExpanded && (
                <>
                    <Vedtaksperioder
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                    />
                    <Utbetalinger
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                    />
                </>
            )}
        </>
    )
}

ArbeidsgiverNode.displayName = 'ArbeidsgiverNode'
