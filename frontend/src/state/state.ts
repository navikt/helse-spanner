import Recoil from 'recoil'
import {KontekstDto} from './dto'

export enum ContentView {
    Json = 'Data',
    Hendelser = 'Hendelser',
    Ingress = 'Ingress',
}

export const expandedHendelserState = Recoil.atom<number[]>({
    key: 'expandedHendelser',
    default: [],
})

export const visBareFeilState = Recoil.atom({
    key: 'visBareFeil',
    default: false,
})
export const skjulPåminnelserState = Recoil.atom({
    key: 'skjulPåminnelser',
    default: true,
})

export const hendelseprefix = Recoil.atom({
    key: 'hendelseregex',
    default: ""
})

export const åpneHendelseDokumentState = Recoil.atom<KontekstDto[]>({
    key: 'åpneHendelseDokument',
    default: [],
})
