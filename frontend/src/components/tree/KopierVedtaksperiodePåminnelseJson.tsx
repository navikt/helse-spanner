import React from "react";
import {PersonDto, VedtakDto} from "../../state/dto";
import styles from "./PersonTree.module.css";

export default function KopierVedtaksperiodePåminnelseJson({ person, organisasjonsnummer, vedtak } : {
    person: PersonDto,
    organisasjonsnummer: string,
    vedtak: VedtakDto
}) {
    const håndterTrykk = () => {
        void navigator.clipboard.writeText(`{ 
    "@event_name": "påminnelse",
    "fødselsnummer": "${person.fødselsnummer}",
    "organisasjonsnummer": "${organisasjonsnummer}",
    "vedtaksperiodeId": "${vedtak.id}",
    "tilstand": "${vedtak.tilstand}",
    "påminnelsestidspunkt": "{{now}}",
    "nestePåminnelsestidspunkt": "{{now+1h}}",
    "tilstandsendringstidspunkt": "{{now-1h}}",
    "antallGangerPåminnet": 1,
    "flagg": ["!ønskerReberegning"]
}`)

    }
    return <button title="Putt vedtaksperiodepåminnelse på clipboardet" type="button" className={styles.PåminnelseKnapp} onClick={(e) => {
        e.stopPropagation()
        håndterTrykk()
    }}>⏰</button>
}
