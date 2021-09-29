import { AktivitetDto, KontekstMapDto } from './dto'

export type Aktivitetslogg = {
    aktiviteter: AktivitetDto[]
    kontekster: Kontekst[]
}

export type Kontekst = {
    kontekstType: string
    kontekstMap: KontekstMapDto
    aktiviteter: AktivitetDto[]
    id: number
    erHendelsekontekst: boolean
    opprettet: Date
    harError: Boolean
    harWarning: Boolean
}
