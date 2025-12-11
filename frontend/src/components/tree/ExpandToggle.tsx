import React from "react";
import styles from "./PersonTree.module.css";
import { ChevronDownIcon, ChevronRightIcon } from '@navikt/aksel-icons'

export const ExpandToggle = (props: React.PropsWithoutRef<{ onClick: () => void; isExpanded: boolean }>) => {
    return (
        <button onClick={props.onClick} className={styles.ExpandArbeidsgiverButton}>
            {props.isExpanded ? <ChevronDownIcon/> : <ChevronRightIcon/>}
        </button>
    )
}
