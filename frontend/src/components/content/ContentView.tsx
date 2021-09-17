import React from "react";
import {ArbeidsgiverContext, usePerson, VedtakContext} from "../../state/contexts";
import {useRecoilValue} from "recoil";
import {displayViewState} from "../../state/state";
import {ShowIfSelected} from "./Content";

export type ContentViewProps = {
    person: React.FC
    arbeidsgiver: React.FC
    vedtaksperiode: React.FC
}

export enum ContentViewId {
    Json = "Json",
    Hendelser = "Hendelser",
}

export const ContentView = React.memo<ContentViewProps>(({person, arbeidsgiver, vedtaksperiode}) => {
    const personDto = usePerson()
    const useDisplayView = useRecoilValue(displayViewState)
    if (!useDisplayView.includes(ContentViewId.Json)) return null
    return (
        <>
            <ShowIfSelected>{person}</ShowIfSelected>
            {personDto.arbeidsgivere.map((arbeidsgiverDto) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiverDto} key={arbeidsgiverDto.id}>
                    <ShowIfSelected>{arbeidsgiver}</ShowIfSelected>
                    {arbeidsgiverDto.vedtaksperioder.map((vedtaksperiodeDto) => (
                        <VedtakContext.Provider value={vedtaksperiodeDto} key={vedtaksperiodeDto.id}>
                            <ShowIfSelected>{vedtaksperiode}</ShowIfSelected>
                        </VedtakContext.Provider>
                    ))}
                </ArbeidsgiverContext.Provider>
            ))}
        </>
    )
})