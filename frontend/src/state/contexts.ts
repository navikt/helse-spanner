import {ArbeidsgiverDto, FokastetVedtaksperiodeDto, PersonDto, UtbetalingDto, VedtakDto} from './dto'
import React from 'react'
import {AktivitetsloggV2} from './model'

//For å slippe defaultvalue til context.
//https://kentcdodds.com/blog/how-to-use-react-context-effectively
// @ts-ignore
export const createContext = <T>() => React.createContext<T | undefined>(undefined)
export const useContext = <T>(context: React.Context<T | undefined>) => {
    const ret = React.useContext(context)
    if (ret === undefined) {
        throw Error('Outside context')
    }
    return ret
}

export const PersonContext = createContext<PersonDto>()
export const ArbeidsgiverContext = createContext<ArbeidsgiverDto>()
export const VedtakContext = createContext<VedtakDto>()
export const ForkastetVedtaksperiodeContext = createContext<FokastetVedtaksperiodeDto>()
export const UtbetalingContext = createContext<UtbetalingDto>()

export const usePerson = () => useContext(PersonContext)
export const useArbeidsgiver = () => useContext(ArbeidsgiverContext)
export const useVedtak = () => useContext(VedtakContext)
export const useForkastetVedtaksperiode = () => useContext(ForkastetVedtaksperiodeContext)
export const useUtbetaling = () => useContext(UtbetalingContext)

export const AktivitetsloggContext = createContext<AktivitetsloggV2>()
export const useAktivitetslogg = () => useContext(AktivitetsloggContext)
