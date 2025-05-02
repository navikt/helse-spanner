import React from "react";
import {PersonDto, VedtakDto} from "../../state/dto";
import styles from "./PersonTree.module.css";

export default function KopierVedtaksperiodePåminnelseJson({ person, yrkesaktivitetstype, organisasjonsnummer, vedtak } : {
    person: PersonDto,
    yrkesaktivitetstype: string,
    organisasjonsnummer: string,
    vedtak: VedtakDto
}) {
    const håndterTrykk = () => {
        void navigator.clipboard.writeText(`{ 
    "@event_name": "påminnelse",
    "fødselsnummer": "${person.fødselsnummer}",
    "organisasjonsnummer": "${organisasjonsnummer}",
    "yrkesaktivitetstype": "${yrkesaktivitetstype}",
    "vedtaksperiodeId": "${vedtak.id}",
    "tilstand": "${vedtak.tilstand}",
    "påminnelsestidspunkt": "{{now}}",
    "nestePåminnelsestidspunkt": "{{now+1h}}",
    "tilstandsendringstidspunkt": "{{now-1h}}",
    "antallGangerPåminnet": 1,
    "flagg": [
        "!ønskerReberegning",
        "!ønskerInntektFraAOrdningen",
        "!trengerReplay",
        "!forkastOverlappendeSykmeldingsperioderAndreArbeidsgivere",
        "!nullstillEgenmeldingsdager"
    ]
}`)

    }
    return <button title="Putt vedtaksperiodepåminnelse på clipboardet" type="button" className={styles.PåminnelseKnapp} onClick={(e) => {
        e.stopPropagation()
        håndterTrykk()
    }}>⏰</button>
}
