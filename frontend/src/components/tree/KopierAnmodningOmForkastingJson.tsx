import React from "react";
import {PersonDto, VedtakDto} from "../../state/dto";
import styles from "./PersonTree.module.css";

export default function KopierAnmodningOmForkastingJson({ person, organisasjonsnummer, vedtak, yrkesaktivitetstype } : {
    person: PersonDto,
    organisasjonsnummer: string,
    vedtak: VedtakDto,
    yrkesaktivitetstype: string
}) {
    const håndterTrykk = () => {
        void navigator.clipboard.writeText(`{ 
    "@event_name": "anmodning_om_forkasting",
    "yrkesaktivitetstype": "${yrkesaktivitetstype}",
    "fødselsnummer": "${person.fødselsnummer}",
    "organisasjonsnummer": "${organisasjonsnummer}",
    "vedtaksperiodeId": "${vedtak.id}"
}`)

    }
    return <button title="Putt anmodning om forkasting på clipboardet" type="button" className={styles.PåminnelseKnapp} onClick={(e) => {
        e.stopPropagation()
        håndterTrykk()
    }}>☠️</button>
}
