import React from "react";
import {PersonDto} from "./external/dto";
import ReactJson from "react-json-view";

export type PersonProps = {
    person: PersonDto
}

export const Person = React.memo((props: PersonProps) => {
    return (
        <ReactJson src={props.person} collapsed={true}/>
    )
})