import Recoil from 'recoil'
import { Id } from './contexts'

export const highligthState = Recoil.atom<Id>({
    key: 'highligthState',
    default: {}
})
export enum ContentView {
    Json = "Json",
    Aktivitetslogg = "Aktivitetslogg",
}

export const displayViewState = Recoil.atom<ContentView[]>({
    key: 'displayViewState',
    default: [ContentView.Json]
})

export const expandedHendelserState = Recoil.atom<string[]>({
    key: 'expandedHendelser',
    default: []
})


