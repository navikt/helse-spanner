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
    toggleUtvidet: () => void
    erUtvidet: boolean
    visForkastede: boolean
    visBehandlinger: boolean
}

export const ArbeidsgiverNode = ({ arbeidsgiver, erUtvidet, toggleUtvidet, visForkastede, visBehandlinger, valgteTing, toggleValgtTing } : ArbeidsgiverNodeProps) => {
    return (
        <>
            <div className={styles.ArbeidsgiverNode}>
                <ExpandToggle isExpanded={erUtvidet} onClick={toggleUtvidet}/>
                <SelectableTreeNode indent={0} valgteTing={valgteTing} ting={arbeidsgiver.id} vedValg={toggleValgtTing}>
                    <ArbeidsgiverSummary arbeidsgiver={arbeidsgiver}/>
                </SelectableTreeNode>
            </div>
            {erUtvidet && (
                <>
                    <Vedtaksperioder
                        arbeidsgiver={arbeidsgiver}
                        valgteTing={valgteTing}
                        visForkastede={visForkastede}
                        visBehandlinger={visBehandlinger}
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
