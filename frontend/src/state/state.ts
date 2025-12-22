import { atom } from 'jotai'
import {KontekstDto} from './dto'

export enum ContentView {
    Json = 'Data',
    Hendelser = 'Hendelser',
    Ingress = 'Ingress',
}

export const expandedHendelserState = atom<number[]>([])

export const visBareFeilState = atom(false)

export const skjulPåminnelserState = atom(true)

export const hendelseprefix = atom("")

export const åpneHendelseDokumentState = atom<KontekstDto[]>([])
