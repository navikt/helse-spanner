import React from 'react'
import { useArbeidsgiver, useForkastetVedtaksperiode, usePerson, useUtbetaling, useVedtak } from '../../state/contexts'
import ReactJson from 'react-json-view'
import { ContentView } from '../../state/state'
import { ContentCategory } from './ContentCategory'

const Arbeidsgiver = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()
    return (
        <div>
            <ReactJson src={arbeidsgiver} name={null} collapsed={1} />
        </div>
    )
})
Arbeidsgiver.displayName = 'JsonView.Arbeidsgiver'

const Person = React.memo(() => {
    const person = usePerson()
    return (
        <div>
            <ReactJson src={person} name={null} collapsed={1} />
        </div>
    )
})
Person.displayName = 'JsonView.Person'

const Vedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    return (
        <div>
            <ReactJson src={vedtaksperiode} name={null} collapsed={1} />
        </div>
    )
})
Vedtaksperiode.displayName = 'JsonView.Vedtaksperiode'

const ForkastetVedtaksperiode = React.memo(() => {
    const vedtaksperiode = useForkastetVedtaksperiode()
    return (
        <div>
            <ReactJson src={vedtaksperiode} name={null} collapsed={1} />
        </div>
    )
})
Vedtaksperiode.displayName = 'JsonView.Vedtaksperiode'

const Utbetaling = React.memo(() => {
    const utbetaling = useUtbetaling()
    return (
        <div>
            <ReactJson src={utbetaling} name={null} collapsed={1} />
        </div>
    )
})
Vedtaksperiode.displayName = 'JsonView.Utbetaling'

export const JsonView = React.memo(() => {
    return (
        <ContentCategory
            displayName={ContentView.Json}
            {...{ Person, Arbeidsgiver, Vedtaksperiode, ForkastetVedtaksperiode, Utbetaling }}
        />
    )
})
JsonView.displayName = 'JsonView'
