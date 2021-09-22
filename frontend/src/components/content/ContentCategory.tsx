import {
    ArbeidsgiverContext,
    ForkastetVedtaksperiodeContext,
    usePerson,
    UtbetalingContext,
    VedtakContext,
} from '../../state/contexts'
import {ShowIfSelected} from './Content'
import React from 'react'
import {useRecoilValue} from 'recoil'
import {ContentView, displayViewState} from '../../state/state'

type ContentCategoryProperties = {
    displayName: ContentView
    Person?: React.FC
    Arbeidsgiver?: React.FC
    Vedtaksperiode?: React.FC
    ForkastetVedtaksperiode?: React.FC
    Utbetaling?: React.FC
}

export const ContentCategory = React.memo<ContentCategoryProperties>(
    ({
         displayName,
         Person = undefined,
         Arbeidsgiver = undefined,
         Vedtaksperiode = undefined,
         ForkastetVedtaksperiode = undefined,
         Utbetaling = undefined,
     }) => {
        const person = usePerson()
        const useDisplayView = useRecoilValue(displayViewState)
        if (!useDisplayView.includes(displayName)) return null
        return (
            <>
                {Person && (
                    <ShowIfSelected>
                        <Person/>
                    </ShowIfSelected>
                )}
                {person.arbeidsgivere.map((arbeidsgiver) => (
                    <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                        {Arbeidsgiver && (
                            <ShowIfSelected>
                                <Arbeidsgiver/>
                            </ShowIfSelected>
                        )}
                        {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                            <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                                {Vedtaksperiode && (
                                    <ShowIfSelected>
                                        <Vedtaksperiode/>
                                    </ShowIfSelected>
                                )}
                            </VedtakContext.Provider>
                        ))}
                        {arbeidsgiver.forkastede.map((forkastet) => (
                            <ForkastetVedtaksperiodeContext.Provider
                                value={forkastet.vedtaksperiode}
                                key={forkastet.vedtaksperiode.id}
                            >
                                {ForkastetVedtaksperiode && (
                                    <ShowIfSelected>
                                        <ForkastetVedtaksperiode/>
                                    </ShowIfSelected>
                                )}
                            </ForkastetVedtaksperiodeContext.Provider>
                        ))}
                        {arbeidsgiver.utbetalinger.map((utbetaling) => (
                            <UtbetalingContext.Provider value={utbetaling} key={utbetaling.id}>
                                {Utbetaling && (
                                    <ShowIfSelected>
                                        <Utbetaling/>
                                    </ShowIfSelected>
                                )}
                            </UtbetalingContext.Provider>
                        ))}
                    </ArbeidsgiverContext.Provider>
                ))}
            </>
        )
    }
)
ContentCategory.displayName = 'ContentCategory'
