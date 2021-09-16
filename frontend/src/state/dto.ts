export type PersonDto = {
    arbeidsgivere: ArbeidsgiverDto[]
    aktørId: string
    fødselsnummer: string
    opprettet: string
    aktivitetslogg: AktivitetsloggDto
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

export interface AktivitetDto {
    kontekster: number[]
    alvorlighetsgrad: string
    aktivitet?: string
    melding: string
    detaljer: any
    tidsstempel: string
    behovtype?: string
}

export type KontekstDto = {
    kontekstType: string
    kontekstMap: KontekstMapDto
}

export type KontekstMapDto = {
    meldingsreferanseId?: string
    vedtaksperiodeId?: string
    aktørId?: string
    fødselsnummer?: string
    organisasjonsnummer?: string
    tilstand?: string
    utbetalingId?: string
}

export type AktivitetsloggDto = {
    aktiviteter: AktivitetDto[]
    kontekster: KontekstDto[]
}
