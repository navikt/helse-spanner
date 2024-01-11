import React from "react";
import {ArbeidsgiverDto, PersonDto, VedtakDto} from "../../state/dto";

export default function KopierVedtaksperiodePåminnelseJson({ person, organisasjonsnummer, vedtak } : {
    person: PersonDto,
    organisasjonsnummer: string,
    vedtak: VedtakDto
}) {
    const håndterTrykk = () => {
        navigator.clipboard.writeText(`{ 
    "@event_name": "påminnelse",
    "fødselsnummer": "${person.fødselsnummer}",
    "aktørId": "${person.aktørId}",
    "organisasjonsnummer": "${organisasjonsnummer}",
    "vedtaksperiodeId": "${vedtak.id}",
    "tilstand": "${vedtak.tilstand}",
    "påminnelsestidspunkt": "{{now}}",
    "nestePåminnelsestidspunkt": "{{now+1h}}",
    "tilstandsendringstidspunkt": "{{now-1h}}",
    "antallGangerPåminnet": 1,
    "ønskerReberegning": false
}`)

    }
    return <button title={"Putt vedtaksperiodepåminnelse på clipboardet"} type={"button"} style={{background:"#00000000", border: "0"}} onClick={(e) => {
        e.stopPropagation()
        håndterTrykk()
    }}>⏰</button>
}