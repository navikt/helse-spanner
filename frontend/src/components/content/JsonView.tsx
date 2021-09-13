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

export const ArbeidsgiverJson = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()

    const isSelected = useIsSelected()
    if (!isSelected) return null
    return (
        <div>
            <ReactJson src={arbeidsgiver} name={null} collapsed={1} />
        </div>
    )
})

export const PersonJson = React.memo(() => {
    const person = usePerson()
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return (
        <div>
            <ReactJson src={person} name={null} collapsed={1} />
        </div>
    )
})

export const VedtaksperiodeJson = React.memo(() => {
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
            <PersonJson />
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <ArbeidsgiverJson />
                    {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                        <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                            <VedtaksperiodeJson />
                        </VedtakContext.Provider>
                    ))}
                </ArbeidsgiverContext.Provider>
            ))}
        </>
    )
})
