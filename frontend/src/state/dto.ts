export type PersonDto = {
    arbeidsgivere: ArbeidsgiverDto[]
    aktørId: string
    fødselsnummer: string
    opprettet: string
    aktivitetslogg: Aktivitetslogg
}

export type FeilDto = {
    error_id: string
    description?: string
}

export type ArbeidsgiverDto = {
    id: string
    organisasjonsnummer: string
    vedtaksperioder: VedtakDto[]
}

export type VedtakDto = {
    tilstand: string
    fom: string
    tom: string
    id: string
}

export interface Aktivitet {
    kontekster: number[]
    alvorlighetsgrad: string
    aktivitet?: string
    melding: string
    detaljer: any
    tidsstempel: string
    behovtype?: string
}

export type Kontekst = {
    kontekstType: string
    kontekstMap: KontekstMap
}

export type KontekstMap = {
    meldingsreferanseId?: string
    vedtaksperiodeId?: string
    aktørId?: string
    fødselsnummer?: string
    organisasjonsnummer?: string
    tilstand?: string
    utbetalingId?: string
}

export type Aktivitetslogg = {
    aktiviteter: Aktivitet[]
    kontekster: Kontekst[]
}
