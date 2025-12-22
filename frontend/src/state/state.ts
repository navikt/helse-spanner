import { atom } from 'jotai'
import { atomWithStorage } from 'jotai/utils'
import {KontekstDto} from './dto'

export type ThemeMode = 'light' | 'dark'

export const themeAtom = atomWithStorage<ThemeMode>('theme', 'dark')

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
