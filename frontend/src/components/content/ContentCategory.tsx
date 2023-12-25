import {
    ArbeidsgiverContext,
    ForkastetVedtaksperiodeContext,
    usePerson,
    UtbetalingContext,
    VedtakContext,
} from '../../state/contexts'
import React, {ReactNode} from 'react'
import {ContentView} from '../../state/state'
import {Card} from "../Card";

type ContentCategoryProperties = {
    displayName: ContentView
    Person?: React.FC
    Arbeidsgiver?: React.FC
    Vedtaksperiode?: React.FC
    ForkastetVedtaksperiode?: React.FC
    Utbetaling?: React.FC,
    valgteTing: string[]
}

export const ContentCategory = React.memo<ContentCategoryProperties>(
    ({
        Person = undefined,
        Arbeidsgiver = undefined,
        Vedtaksperiode = undefined,
        ForkastetVedtaksperiode = undefined,
        Utbetaling = undefined,
        valgteTing
    }) => {
        const person = usePerson()
        return (
            <>
                {Person && <Ramme valgteTing={valgteTing} ting={person.aktørId}><Person /></Ramme> }
                {Arbeidsgiver && person.arbeidsgivere.map((arbeidsgiver) => (
                    <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                        {<Ramme valgteTing={valgteTing} ting={arbeidsgiver.id}><Arbeidsgiver /></Ramme> }
                        {Vedtaksperiode && arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                            <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                                <Ramme valgteTing={valgteTing} ting={vedtaksperiode.id}><Vedtaksperiode /></Ramme>
                            </VedtakContext.Provider>
                        ))}
                        {ForkastetVedtaksperiode && arbeidsgiver.forkastede.map((forkastet) => (
                            <ForkastetVedtaksperiodeContext.Provider
                                value={forkastet.vedtaksperiode}
                                key={forkastet.vedtaksperiode.id}
                            >
                                <Ramme valgteTing={valgteTing} ting={forkastet.vedtaksperiode.id}><ForkastetVedtaksperiode /></Ramme>
                            </ForkastetVedtaksperiodeContext.Provider>
                        ))}
                        {Utbetaling && arbeidsgiver.utbetalinger.map((utbetaling) => (
                            <UtbetalingContext.Provider value={utbetaling} key={utbetaling.id}>
                                <Ramme valgteTing={valgteTing} ting={utbetaling.id}><Utbetaling /></Ramme>
                            </UtbetalingContext.Provider>
                        ))}
                    </ArbeidsgiverContext.Provider>
                ))}
            </>
        )
    }
)
ContentCategory.displayName = 'ContentCategory'

export function fargeForTing(valgteTing: string[], ting: string) {
    const index = valgteTing.findIndex((it) => it === ting)
    if (index == -1) return undefined
    const selectColors = ['LightBlue', 'DarkGray', 'DarkOrange', 'Turquoise', 'DarkSeaGreen', 'Orchid', 'Gold', 'LawnGreen']
    return selectColors[index % selectColors.length]
}

function Ramme({ valgteTing, ting, children }: { valgteTing: string[], ting: string, children: ReactNode }) {
    const color = fargeForTing(valgteTing, ting)
    if (!color) return null
    return <Card style={{ borderStyle: valgteTing.length > 1 ? `solid` : 'none', borderWidth: '7px', borderColor: color }}>
        {children}
    </Card>
}
