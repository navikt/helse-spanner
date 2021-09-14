import { ArbeidsgiverDto, PersonDto, VedtakDto } from './dto'

let next_id = 1

const new_id = () =>
    `${next_id++}`

export const createTestPerson = (
    aktørId: string = new_id(),
    fødselsnummer: string = '42',
    opprettet = '2021-01-01',
    arbeidsgivere: ArbeidsgiverDto[] = [createTestArbeidsgiver(), createTestArbeidsgiver('554321')]
): PersonDto => ({
    aktørId,
    fødselsnummer,
    opprettet,
    arbeidsgivere,
    aktivitetslogg: {
        aktiviteter: [],
        kontekster: []
    }
})

export const createTestArbeidsgiver = (
    organisasjonsnummer: string = '12345',
    vedtaksperioder: VedtakDto[] = [createTestVedtaksperiode(), createTestVedtaksperiode('2021-01-01', '2021-02-03')],
    id: string = new_id(),
): ArbeidsgiverDto => ({
    id,
    organisasjonsnummer,
    vedtaksperioder,
})

export const createTestVedtaksperiode = (
    fom: string = '2021-01-01',
    tom: string = '2021-02-03',
    id: string = new_id()
): VedtakDto => ({
    fom,
    tom,
    id,
    tilstand: "START"
})
