import React from 'react'
import {PersonDto} from '../external/dto'
import {useQuery} from 'react-query'
import {useBackend} from '../external/backend'
import * as Utils from '../utils'
import {PersonVisning} from "./PersonVisning";

export type FetchPersonProps = {
  aktørId: string
}

export const PersonContext = Utils.createContext<PersonDto>()
export const usePerson = () => Utils.useContext(PersonContext)

export const Person = React.memo((props: FetchPersonProps) => {
  const backend = useBackend()
  const { isSuccess, isLoading, isError, data } = useQuery(['person', props.aktørId], () =>
     backend.personForAktørId(props.aktørId)
  )
  if (isSuccess) {
    return <PersonContext.Provider value={data}>
      <PersonVisning/>
    </PersonContext.Provider>
  } else {
    return null
  }
})
