import {AktivitetsloggDto, ArbeidsgiverDto, PersonDto, VedtakDto} from './dto'
import React, {useMemo} from 'react'
import {useRecoilValue} from 'recoil'
import {selectedState} from './state'

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

export const usePerson = () => useContext(PersonContext)
export const useArbeidsgiver = () => useContext(ArbeidsgiverContext)
export const useVedtak = () => useContext(VedtakContext)

export const AktivitetsloggContext = createContext<AktivitetsloggDto>()
export const useAktivitetslogg = () => useContext(AktivitetsloggContext)

export type Id = {
    arbeidsgiver?: string
    vedtaksperiode?: string
}

export const useId = (): Id => {
    const arbeidsgiver = React.useContext(ArbeidsgiverContext)?.id
    const vedtaksperiode = React.useContext(VedtakContext)?.id
    return useMemo(
        () => ({
            arbeidsgiver,
            vedtaksperiode,
        }),
        [arbeidsgiver, vedtaksperiode]
    )
}

export const idEqual = (a: Id, b: Id) => a.arbeidsgiver === b.arbeidsgiver && a.vedtaksperiode === b.vedtaksperiode
export const useIsSelected = () => {
    const highlighted = useRecoilValue(selectedState)
    const id = useId()
    return highlighted && idEqual(id, highlighted)
}
