import React from "react";
import styles from "./PersonTree.module.css";
import {Expand, Next} from "@navikt/ds-icons";

export const ExpandToggle = (props: React.PropsWithoutRef<{ onClick: () => void; isExpanded: boolean }>) => {
    return (
        <button onClick={props.onClick} className={styles.ExpandArbeidsgiverButton}>
            {props.isExpanded ? <Expand/> : <Next/>}
        </button>
    )
}
