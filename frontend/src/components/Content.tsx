import React from 'react'
import ReactJson from "react-json-view";
import {useRecoilValue} from "recoil";
import {highligthState} from "../state";
import {usePerson} from "../contexts";

export const Content = React.memo(() => {
    const person = usePerson()
    const highlight = useRecoilValue(highligthState)


    return (
        <div>
            <ReactJson src={highlight == '' ? person : person.arbeidsgivere[0] } collapsed={true}/>
        </div>
    )
})