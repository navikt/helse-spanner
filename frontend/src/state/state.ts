import Recoil from 'recoil'
import { Id } from './contexts'
import {KontekstDto} from "./dto";

export const selectedState = Recoil.atom<Id[]>({
    key: 'highligthState',
    default: [{}]
})
export enum ContentView {
    Json = "Json",
    Hendelser = "Hendelser",
}

export const displayViewState = Recoil.atom<ContentView[]>({
    key: 'displayViewState',
    default: [ContentView.Json]
})

export const expandedHendelserState = Recoil.atom<string[]>({
    key: 'expandedHendelser',
    default: []
})

export const expandedArbeidsgivereState = Recoil.atom<string[]>({
    key: 'expandedArbeidsgiver',
    default: []
})

export const  visBareFeilState = Recoil.atom({
    key: 'visBareFeilState',
    default: false
})
export const  skjulPåminnelserState = Recoil.atom({
    key: 'skjulPåminnelserState',
    default: true
})

export const  åpneHendelseDokumentState = Recoil.atom<KontekstDto[]>({
    key: 'åpneHendelseDokument',
    default: []
})
