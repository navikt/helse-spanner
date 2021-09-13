import { PersonDto, ArbeidsgiverDto, VedtakDto } from './external/dto'
import React, { useMemo } from 'react'

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

export type Id = {
  person?: string
  arbeidsgiver?: string
  vedtaksperiode?: string
}

export const useId = (): Id => {
  const person = React.useContext(PersonContext)?.aktørId
  const arbeidsgiver = React.useContext(ArbeidsgiverContext)?.id
  const vedtaksperiode = React.useContext(VedtakContext)?.id
  return useMemo(
    () => ({
      person,
      arbeidsgiver,
      vedtaksperiode,
    }),
    [person, arbeidsgiver, vedtaksperiode]
  )
}

export const idEqual = (a: Id, b: Id) =>
  a.person === b.person && a.arbeidsgiver === b.arbeidsgiver && a.vedtaksperiode === b.vedtaksperiode

