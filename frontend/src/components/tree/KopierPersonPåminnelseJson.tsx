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
    return <button title={"Putt personpåminnelse på clipboardet"} type={"button"} style={{background:"#00000000", border: "0"}} onClick={(e) => {
        e.stopPropagation()
        håndterTrykk()
    }}>⏰</button>
}