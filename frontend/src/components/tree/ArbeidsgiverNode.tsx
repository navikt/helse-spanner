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

export const ArbeidsgiverNode = () => {
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
                <SelectableTreeNode indent={0}>
                    <ArbeidsgiverSummary/>
                </SelectableTreeNode>
            </div>
            {isExpanded && (
                <>
                    <Vedtaksperioder/>
                    <Utbetalinger/>
                </>
            )}
        </>
    )
}

ArbeidsgiverNode.displayName = 'ArbeidsgiverNode'
