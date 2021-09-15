import Recoil from 'recoil'
import { Id } from './contexts'

export const highligthState = Recoil.atom<Id>({
    key: 'highligthState',
    default: {}
})


export const displayViewState = Recoil.atom<string[]>({
    key: 'displayViewState',
    default: ["json"]
})

