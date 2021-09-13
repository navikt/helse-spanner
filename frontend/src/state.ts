import { atom } from 'recoil'
import { Id } from './contexts'

export const highligthState = atom<Id | undefined>({
    key: 'highligthState', // unique ID (with respect to other atoms/selectors)
    default: undefined // default value (aka initial value)
})
