import {useRecoilState} from "recoil";
import {expandedArbeidsgivereState} from "../../state/state";
import styles from "./PersonTree.module.css";
import SelectableTreeNode from "./SelectableTreeNode";
import React from "react";
import {ExpandToggle} from "./ExpandToggle";
import {ArbeidsgiverSummary} from "./ArbeidsgiverSummary";
import {Vedtaksperioder} from "./Vedtaksperioder";
import {Utbetalinger} from "./Utbetalinger";
import {ArbeidsgiverDto} from "../../state/dto";

interface ArbeidsgiverNodeProps {
    arbeidsgiver: ArbeidsgiverDto,
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
}

export const ArbeidsgiverNode = ({ arbeidsgiver, valgteTing, toggleValgtTing } : ArbeidsgiverNodeProps) => {
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
                    <ArbeidsgiverSummary arbeidsgiver={arbeidsgiver}/>
                </SelectableTreeNode>
            </div>
            {isExpanded && (
                <>
                    <Vedtaksperioder
                        arbeidsgiver={arbeidsgiver}
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                    />
                    <Utbetalinger
                        arbeidsgiver={arbeidsgiver}
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                    />
                </>
            )}
        </>
    )
}

ArbeidsgiverNode.displayName = 'ArbeidsgiverNode'
