import React from "react";
import {PersonDto} from "../../state/dto";

export default function KopierPersonPåminnelseJson({ person }:  { person: PersonDto }) {
    const håndterTrykk = () => {
        navigator.clipboard.writeText(`{ 
    "@event_name": "person_påminnelse",
    "fødselsnummer": "${person.fødselsnummer}",
    "aktørId": "${person.aktørId}"
}`)

    }
    return <span onClick={(e) => {
        e.stopPropagation()
        håndterTrykk()
    }}>⏰</span>
}