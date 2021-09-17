import React from 'react'
import {useArbeidsgiver, usePerson, useVedtak} from '../../state/contexts'
import ReactJson from 'react-json-view'
import {ContentView} from "./ContentView";

const Arbeidsgiver = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()
    return (
        <div>
            <ReactJson src={arbeidsgiver} name={null} collapsed={1} />
        </div>
    )
})

const Person = React.memo(() => {
    const person = usePerson()
    return (
        <div>
            <ReactJson src={person} name={null} collapsed={1} />
        </div>
    )
})

const Vedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    return (
        <div>
            <ReactJson src={vedtaksperiode} name={null} collapsed={1} />
        </div>
    )
})

export const JsonView = React.memo(() => {
    return <ContentView person={Person} arbeidsgiver={Arbeidsgiver} vedtaksperiode={Vedtaksperiode} />
})
