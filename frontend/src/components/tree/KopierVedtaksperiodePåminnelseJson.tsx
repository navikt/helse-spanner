import React from "react";
import {ArbeidsgiverDto, PersonDto, VedtakDto} from "../../state/dto";

export default function KopierVedtaksperiodePåminnelseJson({ person, arbeidsgiver, vedtak } : {
    person: PersonDto,
    arbeidsgiver: ArbeidsgiverDto,
    vedtak: VedtakDto
}) {
    const håndterTrykk = () => {
        navigator.clipboard.writeText(`{ 
    "@event_name": "påminnelse",
    "fødselsnummer": "${person.fødselsnummer}",
    "aktørId": "${person.aktørId}",
    "organisasjonsnummer": "${arbeidsgiver.organisasjonsnummer}",
    "vedtaksperiodeId": "${vedtak.id}",
    "tilstand": "${vedtak.tilstand}",
    "påminnelsestidspunkt": "{{now}}",
    "nestePåminnelsestidspunkt": "{{now+1h}}",
    "tilstandsendringstidspunkt": "{{now-1h}}",
    "antallGangerPåminnet": 1,
    "ønskerReberegning": false
}`)

    }
    return <span onClick={håndterTrykk}>⏰</span>
}