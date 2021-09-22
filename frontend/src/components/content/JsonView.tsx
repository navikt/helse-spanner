import React from 'react'
import {useArbeidsgiver, useForkastetVedtaksperiode, usePerson, useVedtak} from '../../state/contexts'
import ReactJson from 'react-json-view'
import {ContentView, displayViewState} from '../../state/state'
import {ContentCatgegoryHOC} from "./ContentCatgegoryHOC";
import {useRecoilValue} from "recoil";

const Arbeidsgiver = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()
    return (
        <div>
            <ReactJson src={arbeidsgiver} name={null} collapsed={1} />
        </div>
    )
})
Arbeidsgiver.displayName="JsonView.Arbeidsgiver"

const Person = React.memo(() => {
    const person = usePerson()
    return (
        <div>
            <ReactJson src={person} name={null} collapsed={1} />
        </div>
    )
})
Person.displayName="JsonView.Person"

const Vedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    return (
        <div>
            <ReactJson src={vedtaksperiode} name={null} collapsed={1} />
        </div>
    )
})
Vedtaksperiode.displayName="JsonView.Vedtaksperiode"

const ForkastetVedtaksperiode = React.memo(() => {
    const vedtaksperiode = useForkastetVedtaksperiode()
    return (
        <div>
            <ReactJson src={vedtaksperiode} name={null} collapsed={1} />
        </div>
    )
})
Vedtaksperiode.displayName="JsonView.Vedtaksperiode"

export const JsonView = React.memo(() => {
    let displayName = ContentView.Json
    const useDisplayView = useRecoilValue(displayViewState)
    if (!useDisplayView.includes(displayName)) return null
    return <ContentCatgegoryHOC {...{ Person, Arbeidsgiver, Vedtaksperiode, ForkastetVedtaksperiode }}/>
})
JsonView.displayName="JsonView"
