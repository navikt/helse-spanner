import React from "react";
import {PersonDto} from "../../state/dto";
import styles from "./PersonTree.module.css";

export default function KopierPersonPåminnelseJson({ person }:  { person: PersonDto }) {
    const håndterTrykk = () => {
        void navigator.clipboard.writeText(`{ 
    "@event_name": "person_påminnelse",
    "fødselsnummer": "${person.fødselsnummer}"
}`)

    }
    return <button title="Putt personpåminnelse på clipboardet" type="button" className={styles.PåminnelseKnapp} onClick={(e) => {
        e.stopPropagation()
        håndterTrykk()
    }}>⏰</button>
}
