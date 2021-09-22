import {ArbeidsgiverContext, ForkastetVedtaksperiodeContext, usePerson, VedtakContext} from '../../state/contexts'
import { ShowIfSelected } from './Content'
import React from 'react'

type CategoryComponents = {
    Person: React.FC | undefined
    Arbeidsgiver: React.FC | undefined
    Vedtaksperiode: React.FC | undefined
    ForkastetVedtaksperiode: React.FC | undefined
}

export const ContentCatgegoryHOC = React.memo<CategoryComponents>(components => {
    const person = usePerson()
    return (
        <>
            {components.Person && (
                <ShowIfSelected>
                    <components.Person />
                </ShowIfSelected>
            )}
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    {components.Arbeidsgiver && (
                        <ShowIfSelected>
                            <components.Arbeidsgiver />
                        </ShowIfSelected>
                    )}
                    {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                        <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                            {components.Vedtaksperiode && (
                                <ShowIfSelected>
                                    <components.Vedtaksperiode />
                                </ShowIfSelected>
                            )}
                        </VedtakContext.Provider>
                    ))}
                    {arbeidsgiver.forkastede.map((forkastet) => (
                        <ForkastetVedtaksperiodeContext.Provider value={forkastet.vedtaksperiode} key={forkastet.vedtaksperiode.id}>
                            {components.ForkastetVedtaksperiode && (
                                <ShowIfSelected>
                                    <components.ForkastetVedtaksperiode />
                                </ShowIfSelected>
                            )}
                        </ForkastetVedtaksperiodeContext.Provider>
                    ))}
                </ArbeidsgiverContext.Provider>
            ))}
        </>
    )
})
ContentCatgegoryHOC.displayName="ContentCatgegoryHOC"