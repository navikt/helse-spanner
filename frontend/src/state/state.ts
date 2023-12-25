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

export const expandedArbeidsgivereState = Recoil.atom<string[]>({
    key: 'expandedArbeidsgiver',
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

export const hideForkastedeVedtakState = Recoil.atom({
    key: 'skjulForkastedeVedtak',
    default: false,
})

export const åpneHendelseDokumentState = Recoil.atom<KontekstDto[]>({
    key: 'åpneHendelseDokument',
    default: [],
})
