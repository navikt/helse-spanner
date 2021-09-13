import {PersonDto, ArbeidsgiverDto, VedtakDto} from "./external/dto";
import React from "react";

//For å slippe defaultvalue til context.
//https://kentcdodds.com/blog/how-to-use-react-context-effectively
// @ts-ignore


export const createContext = <T>() => React.createContext<T | undefined>(undefined)

export const PersonContext = createContext<PersonDto>()
export const ArbeidsgiverContext = createContext<ArbeidsgiverDto>()
export const VedtakContext = createContext<VedtakDto>()

export const usePerson = () => useContext(PersonContext)
export const useArbeidsgiver = () => useContext(ArbeidsgiverContext)
export const useVedtak = () => useContext(VedtakContext)


export const useContext = <T>(context: React.Context<T | undefined>) => {
    const ret = React.useContext(context)
    if (ret === undefined) {
        throw Error('Outside context')
    }
    return ret
}

export type Highlight = {
    arbeidsgiver?: string
    vedtaksperiode?: string
}

