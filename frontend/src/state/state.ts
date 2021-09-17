import Recoil from 'recoil'
import {Id} from './contexts'
import {ContentViewId} from "../components/content/ContentView";

export const highligthState = Recoil.atom<Id>({
    key: 'highligthState',
    default: {}
})

export const displayViewState = Recoil.atom<ContentViewId[]>({
    key: 'displayViewState',
    default: [ContentViewId.Json]
})

export const expandedHendelserState = Recoil.atom<string[]>({
    key: 'expandedHendelser',
    default: []
})


