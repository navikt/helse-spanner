import ReactJson from "react-json-view";
import React from "react";
import {usePerson} from "./Person";

export const PersonVisning = () => {
    const person = usePerson()
    return <ReactJson src={person} collapsed={true}/>
}