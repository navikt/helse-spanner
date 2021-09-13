import { atom } from 'recoil'
import { Id } from './contexts'

export const highligthState = atom<Id>({
    key: 'highligthState', // unique ID (with respect to other atoms/selectors)
    default: {} // default value (aka initial value)
})
