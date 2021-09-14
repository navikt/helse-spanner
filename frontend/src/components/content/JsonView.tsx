import React from 'react'
import {
    ArbeidsgiverContext,
    useArbeidsgiver,
    useIsSelected,
    usePerson,
    useVedtak,
    VedtakContext,
} from '../../state/contexts'
import ReactJson from 'react-json-view'

const Arbeidsgiver = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()

    const isSelected = useIsSelected()
    if (!isSelected) return null
    return (
        <div>
            <ReactJson src={arbeidsgiver} name={null} collapsed={1} />
        </div>
    )
})

const Person = React.memo(() => {
    const person = usePerson()
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return (
        <div>
            <ReactJson src={person} name={null} collapsed={1} />
        </div>
    )
})

const Vedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return (
        <div>
            <ReactJson src={vedtaksperiode} name={null} collapsed={1} />
        </div>
    )
})

export const JsonView = React.memo(() => {
    const person = usePerson()
    return (
        <>
            <Person />
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <Arbeidsgiver />
                    {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                        <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                            <Vedtaksperiode />
                        </VedtakContext.Provider>
                    ))}
                </ArbeidsgiverContext.Provider>
            ))}
        </>
    )
})
