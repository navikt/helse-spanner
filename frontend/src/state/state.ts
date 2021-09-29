import Recoil from 'recoil'
import { Id } from './contexts'
import {KontekstDto} from "./dto";

export const selectedState = Recoil.atom<Id[]>({
    key: 'highligth',
    default: [{}]
})
export enum ContentView {
    Json = "Json",
    Hendelser = "Hendelser",
}

export const displayViewState = Recoil.atom<ContentView[]>({
    key: 'displayView',
    default: [ContentView.Json]
})

export const expandedHendelserState = Recoil.atom<number[]>({
    key: 'expandedHendelser',
    default: []
})

export const expandedArbeidsgivereState = Recoil.atom<string[]>({
    key: 'expandedArbeidsgiver',
    default: []
})

export const  visBareFeilState = Recoil.atom({
    key: 'visBareFeil',
    default: false
})
export const  skjulPåminnelserState = Recoil.atom({
    key: 'skjulPåminnelser',
    default: true
})

export const  hideForkastedeVedtakState = Recoil.atom({
    key: 'skjulForkastedeVedtak',
    default: false
})

export const  åpneHendelseDokumentState = Recoil.atom<KontekstDto[]>({
    key: 'åpneHendelseDokument',
    default: []
})
