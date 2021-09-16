import React from 'react'
import { ArbeidsgiverContext, useArbeidsgiver, usePerson, useVedtak, VedtakContext } from '../../state/contexts'
import ReactJson from 'react-json-view'
import { useRecoilValue } from 'recoil'
import { ContentView, displayViewState } from '../../state/state'
import { ShowIfSelected } from './Content'

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
    const person = usePerson()
    const useDisplayView = useRecoilValue(displayViewState)
    if (!useDisplayView.includes(ContentView.Json)) return null
    return (
        <>
            <ShowIfSelected>
                <Person />
            </ShowIfSelected>
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <ShowIfSelected>
                        <Arbeidsgiver />
                    </ShowIfSelected>
                    {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                        <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                            <ShowIfSelected>
                                <Vedtaksperiode />
                            </ShowIfSelected>
                        </VedtakContext.Provider>
                    ))}
                </ArbeidsgiverContext.Provider>
            ))}
        </>
    )
})
