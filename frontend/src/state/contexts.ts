import {PersonDto} from './dto'
import React from 'react'

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
export const usePerson = () => useContext(PersonContext)