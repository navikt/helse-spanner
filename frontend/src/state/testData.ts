import {
    ArbeidsgiverDto,
    BehandlingDto, EndringDto,
    FokastelseDto,
    FokastetVedtaksperiodeDto,
    PersonDto,
    UtbetalingDto,
    VedtakDto
} from './dto'

let next_id = 42

const new_id = () => `${next_id++}`

export const createTestPerson = (
    aktørId: string = new_id(),
    fødselsnummer: string = '42123245125',
    opprettet = '2021-01-01',
    arbeidsgivere: ArbeidsgiverDto[] = [createTestArbeidsgiver(), createTestArbeidsgiver('554321')]
): PersonDto => ({
    aktørId,
    fødselsnummer,
    opprettet,
    arbeidsgivere,
    aktivitetslogg: {
        aktiviteter: [],
        kontekster: [],
    },
    infotrygdhistorikk: []
})

export const createTestArbeidsgiver = (
    organisasjonsnummer: string = '12345',
    vedtaksperioder: VedtakDto[] = [createTestVedtaksperiode(), createTestVedtaksperiode('2021-01-01', '2021-02-03')],
    forkastedeVedtaksperioder: FokastetVedtaksperiodeDto[] = [],
    utbetalinger: UtbetalingDto[] = [],
    id: string = new_id()
): ArbeidsgiverDto => ({
    id,
    organisasjonsnummer,
    vedtaksperioder,
    forkastede: forkastedeVedtaksperioder.map((it): FokastelseDto => ({ årsak: 'Ikke støttet', vedtaksperiode: it })),
    utbetalinger,
})

export const createTestVedtaksperiode = (
    fom: string = '2021-01-01',
    tom: string = '2021-02-03',
    id: string = new_id()
): VedtakDto => ({
    fom,
    tom,
    id,
    tilstand: 'START',
    behandlinger: []
})
