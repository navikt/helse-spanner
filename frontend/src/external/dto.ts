export type PersonDto = {
  arbeidsgivere: ArbeidsgiverDto[];
  aktørId: string
  fødselsnummer: string
  opprettet: string
}

export type FeilDto = {
  error_id: string
  description?: string
}

export type ArbeidsgiverDto = {
  id: string;
  organisasjonsnummer: string
  vedtaksperioder: VedtakDto[]
}

export type VedtakDto = {
  fom: string
  tom: string
  id: string
}
