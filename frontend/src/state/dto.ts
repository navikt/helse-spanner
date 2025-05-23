export type PersonDto = {
    arbeidsgivere: ArbeidsgiverDto[]
    aktørId: string
    fødselsnummer: string
    opprettet: string
    aktivitetslogg?: AktivitetsloggDto
    aktivitetsloggV2?: AktivitetsloggV2Dto
    infotrygdhistorikk: InfotrygdhistorikkDto[]
    vilkårsgrunnlagHistorikk: VilkårsgrunnlaghistorikkDto[]
    [x: string]: any
}

export type VilkårsgrunnlaghistorikkDto = {
    id: string,
    opprettet: string,
    vilkårsgrunnlag: VilkårsgrunnlagDto[],
    [x: string]: any
}

export type VilkårsgrunnlagDto = {
    vilkårsgrunnlagId: string,
    skjæringstidspunkt: string,
    [x: string]: any
}

export type InfotrygdhistorikkDto = {
    arbeidsgiverutbetalingsperioder: InfotrygdperiodeDto[],
    ferieperioder: InfotrygdperiodeDto[],
    personutbetalingsperioder: InfotrygdperiodeDto[],
    [x: string]: any
}

export type InfotrygdperiodeDto = {
    fom: string,
    tom: string,
    [x: string]: any
}

export type MaskertDto = {
    id: string
}

export type FeilDto = {
    error_id: string
    description?: string
    [x: string]: any
}

export type ArbeidsgiverDto = {
    id: string
    organisasjonsnummer: string
    yrkesaktivitetstype: string
    vedtaksperioder: VedtakDto[]
    forkastede: FokastelseDto[]
    utbetalinger: UtbetalingDto[]
    [x: string]: any
}

export type VedtakDto = {
    tilstand: string
    fom: string
    tom: string
    id: string
    skjæringstidspunkt?: string
    behandlinger: BehandlingDto[]
    gjeldende: Gjeldende
    [x: string]: any
}

export type Gjeldende = {
    arbeidsgiverperiode: {
        fom: string,
        tom: string
    }[]
}

export type BehandlingDto = {
    tilstand: string
    id: string
    endringer: EndringDto[]
    kilde: KildeDto
    [x: string]: any
}

export type EndringDto = {
    id: string
    tidsstempel: string
    sykdomstidslinje: SykdomstidslinjeDto
    dokumentsporing: DokumentsporingDto
    [x: string]: any
}

export type DokumentsporingDto = {
    "dokumentId": string,
    "dokumenttype": string,
    [x: string] : any
}

export type SykdomstidslinjeDto = {
    dager: DagDto[],
    [x: string]: any
}

export type KildeDto = {
    avsender: string,
    innsendt: string,
    registrert: string,
    meldingsreferanseId: string,
    [x: string]: any
}

export type DagDto = {
    fom: string | null,
    tom: string | null,
    dato: string | null,
    type: string,
    [x: string]: any
}

//Får forkastet vedtaksperiode oppgradert versjoner? Hvis ikke må forkastet vedtaksperiode være egen type ettersom den
//kan være på et eget format
export type FokastetVedtaksperiodeDto = {
    tilstand: string
    fom: string
    tom: string
    id: string
    [x: string]: any
}

export type FokastelseDto = {
    vedtaksperiode: FokastetVedtaksperiodeDto
    årsak: string
}

export type UtbetalingDto = {
    status: string
    fom: string
    tom: string
    type: string
    id: string
    korrelasjonsId: string
    [x: string]: any
}

export interface AktivitetDto {
    kontekster: number[]
    alvorlighetsgrad: string
    aktivitet?: string
    melding: string
    detaljer: any
    tidsstempel: string
    behovtype?: string
    [x: string]: any
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
    [x: string]: any
}

export type AktivitetsloggDto = {
    aktiviteter: AktivitetDto[]
    kontekster: KontekstDto[]
}

export type AktivitetsloggV2Dto = {
    aktiviteter: AktivitetV2Dto[]
}
export interface AktivitetV2Dto {
    id: number
    nivå: string
    tekst: string
    tidsstempel: string
    kontekster: KonteksterDto
    interessant?: boolean
}

export type KonteksterDto = {
    [x: string]: KontekstMapV2Dto
}

export type KontekstMapV2Dto = {
    meldingsreferanseId?: string
    vedtaksperiodeId?: string
    aktørId?: string
    fødselsnummer?: string
    organisasjonsnummer?: string
    tilstand?: string
    utbetalingId?: string
    [x: string]: any
}

export type MeldingDto = {
    id: string
}
