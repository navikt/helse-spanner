object Data {
    val json = """
{
  "aktørId": "42",
  "fødselsnummer": "12020052345",
  "opprettet": "2021-07-23T09:01:53.14463035",
  "dødsdato": null,
  "aktivitetslogg": {
    "aktiviteter": [
      {
        "kontekster": [
          0,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.27600"
      },
      {
        "kontekster": [
          0,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ny arbeidsgiver med organisasjonsnummer arbeidsgiver 1 for denne personen",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.27800"
      },
      {
        "kontekster": [
          0,
          1,
          2
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Lager ny vedtaksperiode pga. Sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.30200"
      },
      {
        "kontekster": [
          0,
          1,
          2,
          3,
          4,
          5
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Fullført behandling av sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.32500"
      },
      {
        "kontekster": [
          6,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.33200"
      },
      {
        "kontekster": [
          6,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ny arbeidsgiver med organisasjonsnummer arbeidsgiver 2 for denne personen",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.33200"
      },
      {
        "kontekster": [
          6,
          1,
          7
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Lager ny vedtaksperiode pga. Sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.33200"
      },
      {
        "kontekster": [
          6,
          1,
          7,
          8,
          4,
          5
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Fullført behandling av sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.33300"
      },
      {
        "kontekster": [
          9,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.33400"
      },
      {
        "kontekster": [
          9,
          1,
          2
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Lager ny vedtaksperiode pga. Sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.33400"
      },
      {
        "kontekster": [
          9,
          1,
          2,
          10,
          4,
          11
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Fullført behandling av sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.35200"
      },
      {
        "kontekster": [
          12,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.35200"
      },
      {
        "kontekster": [
          12,
          1,
          7
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Lager ny vedtaksperiode pga. Sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.35200"
      },
      {
        "kontekster": [
          12,
          1,
          7,
          13,
          4,
          11
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Fullført behandling av sykmelding",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.35300"
      },
      {
        "kontekster": [
          14,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler søknad",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.35800"
      },
      {
        "kontekster": [
          14,
          1,
          2,
          10,
          11,
          15
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Fullført behandling av søknad",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.36200"
      },
      {
        "kontekster": [
          16,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler søknad",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.36200"
      },
      {
        "kontekster": [
          16,
          1,
          7,
          13,
          11,
          15
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Fullført behandling av søknad",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.36300"
      },
      {
        "kontekster": [
          17,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler søknad",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.39800"
      },
      {
        "kontekster": [
          17,
          1,
          2,
          3,
          5,
          18
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Sykepengehistorikk",
        "melding": "Trenger sykepengehistorikk fra Infotrygd",
        "detaljer": {
          "historikkFom": "2017-01-27",
          "historikkTom": "2021-07-23"
        },
        "tidsstempel": "2021-07-23T09:01:53.40100"
      },
      {
        "kontekster": [
          17,
          1,
          2,
          3,
          5,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Må oppfriske Infotrygdhistorikken",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.40100"
      },
      {
        "kontekster": [
          17,
          1,
          2,
          3,
          5,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Fullført behandling av søknad",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.40100"
      },
      {
        "kontekster": [
          19,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Oppdaterer Infotrygdhistorikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.41500"
      },
      {
        "kontekster": [
          19,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Oppfrisket Infotrygdhistorikk ble lagret",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.41500"
      },
      {
        "kontekster": [
          19,
          1,
          2,
          3,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder for overlapp mot 27-01-2021 til 31-01-2021",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.41900"
      },
      {
        "kontekster": [
          19,
          1,
          2,
          3,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.41900"
      },
      {
        "kontekster": [
          19,
          1,
          2,
          3,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.41900"
      },
      {
        "kontekster": [
          19,
          1,
          2,
          3,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.42000"
      },
      {
        "kontekster": [
          19,
          1,
          2,
          3,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Oppdaget at perioden er en forlengelse",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.42100"
      },
      {
        "kontekster": [
          19,
          1,
          2,
          3,
          18,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "InntekterForSykepengegrunnlag",
        "melding": "Trenger inntekter for sykepengegrunnlag",
        "detaljer": {
          "beregningStart": "2020-10",
          "beregningSlutt": "2020-12"
        },
        "tidsstempel": "2021-07-23T09:01:53.42400"
      },
      {
        "kontekster": [
          19,
          1,
          2,
          3,
          18,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "ArbeidsforholdV2",
        "melding": "Trenger informasjon om arbeidsforhold",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.42500"
      },
      {
        "kontekster": [
          21,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgrunnlag",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.46700"
      },
      {
        "kontekster": [
          21,
          1,
          2,
          3,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Foreldrepenger",
        "melding": "Trenger informasjon om foreldrepengeytelser fra FPSAK",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.47200"
      },
      {
        "kontekster": [
          21,
          1,
          2,
          3,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Pleiepenger",
        "melding": "Trenger informasjon om pleiepengeytelser fra Infotrygd",
        "detaljer": {
          "pleiepengerFom": "2021-01-27",
          "pleiepengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.47200"
      },
      {
        "kontekster": [
          21,
          1,
          2,
          3,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Omsorgspenger",
        "melding": "Trenger informasjon om omsorgspengerytelser fra Infotrygd",
        "detaljer": {
          "omsorgspengerFom": "2021-01-27",
          "omsorgspengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.47300"
      },
      {
        "kontekster": [
          21,
          1,
          2,
          3,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Opplæringspenger",
        "melding": "Trenger informasjon om opplæringspengerytelser fra Infotrygd",
        "detaljer": {
          "opplæringspengerFom": "2021-01-27",
          "opplæringspengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.47300"
      },
      {
        "kontekster": [
          21,
          1,
          2,
          3,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Institusjonsopphold",
        "melding": "Trenger informasjon om institusjonsopphold fra Inst2",
        "detaljer": {
          "institusjonsoppholdFom": "2021-01-27",
          "institusjonsoppholdTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.47300"
      },
      {
        "kontekster": [
          21,
          1,
          2,
          3,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Arbeidsavklaringspenger",
        "melding": "Trenger informasjon om arbeidsavklaringspenger",
        "detaljer": {
          "periodeFom": "2020-07-27",
          "periodeTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.47300"
      },
      {
        "kontekster": [
          21,
          1,
          2,
          3,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dagpenger",
        "melding": "Trenger informasjon om dagpenger",
        "detaljer": {
          "periodeFom": "2020-07-27",
          "periodeTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.47300"
      },
      {
        "kontekster": [
          21,
          1,
          2,
          3,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dødsinfo",
        "melding": "Trenger informasjon om dødsdato fra PDL",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.47300"
      },
      {
        "kontekster": [
          21,
          1,
          2,
          3,
          20,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forespør sykdoms- og inntektshistorikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.47300"
      },
      {
        "kontekster": [
          23,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler historiske utbetalinger og inntekter",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.47700"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Perioden er en direkte overgang fra periode med opphav i Infotrygd",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.48600"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.48600"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.48600"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.48700"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen foreldrepenge- eller svangerskapsytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.48700"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen pleiepengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.48700"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen omsorgspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.48700"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen opplæringspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.48700"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen institusjonsoppholdsperioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.48700"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ingen avviste dager på grunn av 20 % samlet sykdomsgrad-regel for denne perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.50100"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Redusert utbetaling minst én dag på grunn av inntekt over 6G",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.51600"
      },
      {
        "kontekster": [
          23,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Maksimalt antall sykedager overskrides ikke i perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.51700"
      },
      {
        "kontekster": [
          24,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler søknad",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.51800"
      },
      {
        "kontekster": [
          24,
          1,
          7,
          8,
          5,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Trenger ikke oppfriske Infotrygdhistorikken, bruker lagret historikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.51900"
      },
      {
        "kontekster": [
          24,
          1,
          7,
          8,
          5,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder for overlapp mot 27-01-2021 til 31-01-2021",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.52000"
      },
      {
        "kontekster": [
          24,
          1,
          7,
          8,
          5,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.52000"
      },
      {
        "kontekster": [
          24,
          1,
          7,
          8,
          5,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.52000"
      },
      {
        "kontekster": [
          24,
          1,
          7,
          8,
          5,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.52000"
      },
      {
        "kontekster": [
          24,
          1,
          7,
          8,
          5,
          18
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Oppdaget at perioden er en forlengelse",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.52100"
      },
      {
        "kontekster": [
          24,
          1,
          7,
          8,
          5,
          18,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "InntekterForSykepengegrunnlag",
        "melding": "Trenger inntekter for sykepengegrunnlag",
        "detaljer": {
          "beregningStart": "2020-10",
          "beregningSlutt": "2020-12"
        },
        "tidsstempel": "2021-07-23T09:01:53.52100"
      },
      {
        "kontekster": [
          24,
          1,
          7,
          8,
          5,
          18,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "ArbeidsforholdV2",
        "melding": "Trenger informasjon om arbeidsforhold",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.52100"
      },
      {
        "kontekster": [
          24,
          1,
          7,
          8,
          5,
          18,
          20
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Fullført behandling av søknad",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.52100"
      },
      {
        "kontekster": [
          25,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgrunnlag",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54200"
      },
      {
        "kontekster": [
          25,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Foreldrepenger",
        "melding": "Trenger informasjon om foreldrepengeytelser fra FPSAK",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54300"
      },
      {
        "kontekster": [
          25,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Pleiepenger",
        "melding": "Trenger informasjon om pleiepengeytelser fra Infotrygd",
        "detaljer": {
          "pleiepengerFom": "2021-01-27",
          "pleiepengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54300"
      },
      {
        "kontekster": [
          25,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Omsorgspenger",
        "melding": "Trenger informasjon om omsorgspengerytelser fra Infotrygd",
        "detaljer": {
          "omsorgspengerFom": "2021-01-27",
          "omsorgspengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54300"
      },
      {
        "kontekster": [
          25,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Opplæringspenger",
        "melding": "Trenger informasjon om opplæringspengerytelser fra Infotrygd",
        "detaljer": {
          "opplæringspengerFom": "2021-01-27",
          "opplæringspengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54300"
      },
      {
        "kontekster": [
          25,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Institusjonsopphold",
        "melding": "Trenger informasjon om institusjonsopphold fra Inst2",
        "detaljer": {
          "institusjonsoppholdFom": "2021-01-27",
          "institusjonsoppholdTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54300"
      },
      {
        "kontekster": [
          25,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Arbeidsavklaringspenger",
        "melding": "Trenger informasjon om arbeidsavklaringspenger",
        "detaljer": {
          "periodeFom": "2020-07-27",
          "periodeTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54300"
      },
      {
        "kontekster": [
          25,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dagpenger",
        "melding": "Trenger informasjon om dagpenger",
        "detaljer": {
          "periodeFom": "2020-07-27",
          "periodeTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54300"
      },
      {
        "kontekster": [
          25,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dødsinfo",
        "melding": "Trenger informasjon om dødsdato fra PDL",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54300"
      },
      {
        "kontekster": [
          25,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forespør sykdoms- og inntektshistorikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54300"
      },
      {
        "kontekster": [
          26,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler historiske utbetalinger og inntekter",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54400"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Perioden er en direkte overgang fra periode med opphav i Infotrygd",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54400"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54400"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54500"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54500"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen foreldrepenge- eller svangerskapsytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54500"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen pleiepengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54500"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen omsorgspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54500"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen opplæringspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54500"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen institusjonsoppholdsperioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54500"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ingen avviste dager på grunn av 20 % samlet sykdomsgrad-regel for denne perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54700"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Redusert utbetaling minst én dag på grunn av inntekt over 6G",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54800"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Maksimalt antall sykedager overskrides ikke i perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54800"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22,
          27,
          2,
          3,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Foreldrepenger",
        "melding": "Trenger informasjon om foreldrepengeytelser fra FPSAK",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54900"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22,
          27,
          2,
          3,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Pleiepenger",
        "melding": "Trenger informasjon om pleiepengeytelser fra Infotrygd",
        "detaljer": {
          "pleiepengerFom": "2021-01-27",
          "pleiepengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54900"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22,
          27,
          2,
          3,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Omsorgspenger",
        "melding": "Trenger informasjon om omsorgspengerytelser fra Infotrygd",
        "detaljer": {
          "omsorgspengerFom": "2021-01-27",
          "omsorgspengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54900"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22,
          27,
          2,
          3,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Opplæringspenger",
        "melding": "Trenger informasjon om opplæringspengerytelser fra Infotrygd",
        "detaljer": {
          "opplæringspengerFom": "2021-01-27",
          "opplæringspengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54900"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22,
          27,
          2,
          3,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Institusjonsopphold",
        "melding": "Trenger informasjon om institusjonsopphold fra Inst2",
        "detaljer": {
          "institusjonsoppholdFom": "2021-01-27",
          "institusjonsoppholdTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54900"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22,
          27,
          2,
          3,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Arbeidsavklaringspenger",
        "melding": "Trenger informasjon om arbeidsavklaringspenger",
        "detaljer": {
          "periodeFom": "2020-07-27",
          "periodeTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54900"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22,
          27,
          2,
          3,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dagpenger",
        "melding": "Trenger informasjon om dagpenger",
        "detaljer": {
          "periodeFom": "2020-07-27",
          "periodeTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:53.54900"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22,
          27,
          2,
          3,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dødsinfo",
        "melding": "Trenger informasjon om dødsdato fra PDL",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54900"
      },
      {
        "kontekster": [
          26,
          1,
          7,
          8,
          22,
          27,
          2,
          3,
          27,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forespør sykdoms- og inntektshistorikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.54900"
      },
      {
        "kontekster": [
          28,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler historiske utbetalinger og inntekter",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55000"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Perioden er en direkte overgang fra periode med opphav i Infotrygd",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55100"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55100"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55100"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55100"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen foreldrepenge- eller svangerskapsytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55100"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen pleiepengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55100"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen omsorgspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55100"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen opplæringspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55100"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen institusjonsoppholdsperioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55100"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ingen avviste dager på grunn av 20 % samlet sykdomsgrad-regel for denne perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55400"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Redusert utbetaling minst én dag på grunn av inntekt over 6G",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55600"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Maksimalt antall sykedager overskrides ikke i perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.55600"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingslinjer bygget vellykket",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.57000"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingslinjer bygget vellykket",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.57200"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Perioden er en forlengelse, av type OVERGANG_FRA_IT",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.57600"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Saken oppfyller krav for behandling, settes til \"Avventer simulering\"",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:53.57700"
      },
      {
        "kontekster": [
          28,
          1,
          2,
          3,
          22,
          29,
          30
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Simulering",
        "melding": "Trenger simulering fra Oppdragssystemet",
        "detaljer": {
          "maksdato": "2021-12-31",
          "saksbehandler": "SPLEIS",
          "mottaker": "arbeidsgiver 1",
          "fagområde": "SPREF",
          "linjer": [
            {
              "fom": "2021-01-27",
              "tom": "2021-01-31",
              "satstype": "DAG",
              "sats": 1170,
              "dagsats": 1170,
              "lønn": 1431,
              "grad": 100.0,
              "stønadsdager": 3,
              "totalbeløp": 3510,
              "endringskode": "NY",
              "delytelseId": 1,
              "refDelytelseId": null,
              "refFagsystemId": null,
              "statuskode": null,
              "datoStatusFom": null,
              "klassekode": "SPREFAG-IOP"
            }
          ],
          "fagsystemId": "VI3L47ZMIFH25OX3NAJTCZLQEU",
          "endringskode": "NY"
        },
        "tidsstempel": "2021-07-23T09:01:54.18100"
      },
      {
        "kontekster": [
          31,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler simulering",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.18300"
      },
      {
        "kontekster": [
          31,
          1,
          2,
          3,
          29
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Simulering kom frem til et annet totalbeløp. Kontroller beløpet til utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.18400"
      },
      {
        "kontekster": [
          31,
          1,
          2,
          3,
          29,
          32,
          30
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Godkjenning",
        "melding": "Forespør godkjenning fra saksbehandler",
        "detaljer": {
          "periodeFom": "2021-01-27",
          "periodeTom": "2021-01-31",
          "skjæringstidspunkt": "2021-01-20",
          "periodetype": "OVERGANG_FRA_IT",
          "utbetalingtype": "UTBETALING",
          "inntektskilde": "FLERE_ARBEIDSGIVERE",
          "warnings": {
            "aktiviteter": [],
            "kontekster": []
          },
          "aktiveVedtaksperioder": [
            {
              "orgnummer": "arbeidsgiver 1",
              "vedtaksperiodeId": "d9568be3-bdcc-4af3-8dae-96d7b851f889",
              "periodetype": "OVERGANG_FRA_IT"
            },
            {
              "orgnummer": "arbeidsgiver 2",
              "vedtaksperiodeId": "09ca19d1-871f-40bc-a074-25a5887cbfc2",
              "periodetype": "OVERGANG_FRA_IT"
            }
          ],
          "arbeidsforholdId": null
        },
        "tidsstempel": "2021-07-23T09:01:54.18700"
      },
      {
        "kontekster": [
          33,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgodkjenning",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.19100"
      },
      {
        "kontekster": [
          33,
          1,
          2
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetaling markert som godkjent av saksbehandler Ola Nordmann 2021-07-23T09:01:54.190965258",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.19100"
      },
      {
        "kontekster": [
          33,
          1,
          2,
          30
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Utbetaling",
        "melding": "Trenger å sende utbetaling til Oppdrag",
        "detaljer": {
          "mottaker": "arbeidsgiver 1",
          "fagområde": "SPREF",
          "linjer": [
            {
              "fom": "2021-01-27",
              "tom": "2021-01-31",
              "satstype": "DAG",
              "sats": 1170,
              "dagsats": 1170,
              "lønn": 1431,
              "grad": 100.0,
              "stønadsdager": 3,
              "totalbeløp": 3510,
              "endringskode": "NY",
              "delytelseId": 1,
              "refDelytelseId": null,
              "refFagsystemId": null,
              "statuskode": null,
              "datoStatusFom": null,
              "klassekode": "SPREFAG-IOP"
            }
          ],
          "fagsystemId": "VI3L47ZMIFH25OX3NAJTCZLQEU",
          "endringskode": "NY",
          "saksbehandler": "Ola Nordmann",
          "maksdato": "2021-12-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.21500"
      },
      {
        "kontekster": [
          34,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetaling overført",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.24800"
      },
      {
        "kontekster": [
          34,
          1,
          2,
          30
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingen ble overført til Oppdrag/UR 2021-07-23T09:01:54.248318409, og har fått avstemmingsnøkkel 123456",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.24900"
      },
      {
        "kontekster": [
          35,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.26300"
      },
      {
        "kontekster": [
          35,
          1,
          2,
          30,
          3,
          36
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "OK fra Oppdragssystemet",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.28400"
      },
      {
        "kontekster": [
          35,
          1,
          2,
          30,
          3,
          36,
          37,
          30,
          38,
          10,
          15,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "InntekterForSykepengegrunnlag",
        "melding": "Trenger inntekter for sykepengegrunnlag",
        "detaljer": {
          "beregningStart": "2020-10",
          "beregningSlutt": "2020-12"
        },
        "tidsstempel": "2021-07-23T09:01:54.29100"
      },
      {
        "kontekster": [
          35,
          1,
          2,
          30,
          3,
          36,
          37,
          30,
          38,
          10,
          15,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "ArbeidsforholdV2",
        "melding": "Trenger informasjon om arbeidsforhold",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.29100"
      },
      {
        "kontekster": [
          35,
          1,
          2,
          30,
          3,
          36,
          37,
          30,
          38,
          10,
          15,
          20,
          7,
          8,
          27,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "InntekterForSykepengegrunnlag",
        "melding": "Trenger inntekter for sykepengegrunnlag",
        "detaljer": {
          "beregningStart": "2020-10",
          "beregningSlutt": "2020-12"
        },
        "tidsstempel": "2021-07-23T09:01:54.29100"
      },
      {
        "kontekster": [
          35,
          1,
          2,
          30,
          3,
          36,
          37,
          30,
          38,
          10,
          15,
          20,
          7,
          8,
          27,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "ArbeidsforholdV2",
        "melding": "Trenger informasjon om arbeidsforhold",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.29100"
      },
      {
        "kontekster": [
          39,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgrunnlag",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.31600"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Foreldrepenger",
        "melding": "Trenger informasjon om foreldrepengeytelser fra FPSAK",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.31600"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Pleiepenger",
        "melding": "Trenger informasjon om pleiepengeytelser fra Infotrygd",
        "detaljer": {
          "pleiepengerFom": "2021-01-27",
          "pleiepengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.31600"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Omsorgspenger",
        "melding": "Trenger informasjon om omsorgspengerytelser fra Infotrygd",
        "detaljer": {
          "omsorgspengerFom": "2021-01-27",
          "omsorgspengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.31600"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Opplæringspenger",
        "melding": "Trenger informasjon om opplæringspengerytelser fra Infotrygd",
        "detaljer": {
          "opplæringspengerFom": "2021-01-27",
          "opplæringspengerTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.31600"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Institusjonsopphold",
        "melding": "Trenger informasjon om institusjonsopphold fra Inst2",
        "detaljer": {
          "institusjonsoppholdFom": "2021-01-27",
          "institusjonsoppholdTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.31600"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Arbeidsavklaringspenger",
        "melding": "Trenger informasjon om arbeidsavklaringspenger",
        "detaljer": {
          "periodeFom": "2020-07-27",
          "periodeTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.31600"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dagpenger",
        "melding": "Trenger informasjon om dagpenger",
        "detaljer": {
          "periodeFom": "2020-07-27",
          "periodeTom": "2021-01-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.31600"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dødsinfo",
        "melding": "Trenger informasjon om dødsdato fra PDL",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.31600"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22,
          40
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forkaster utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.31700"
      },
      {
        "kontekster": [
          39,
          1,
          7,
          8,
          20,
          22,
          40
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forespør sykdoms- og inntektshistorikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.32400"
      },
      {
        "kontekster": [
          41,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgrunnlag",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.34500"
      },
      {
        "kontekster": [
          41,
          1,
          2,
          10,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Foreldrepenger",
        "melding": "Trenger informasjon om foreldrepengeytelser fra FPSAK",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.34600"
      },
      {
        "kontekster": [
          41,
          1,
          2,
          10,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Pleiepenger",
        "melding": "Trenger informasjon om pleiepengeytelser fra Infotrygd",
        "detaljer": {
          "pleiepengerFom": "2021-02-01",
          "pleiepengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.34600"
      },
      {
        "kontekster": [
          41,
          1,
          2,
          10,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Omsorgspenger",
        "melding": "Trenger informasjon om omsorgspengerytelser fra Infotrygd",
        "detaljer": {
          "omsorgspengerFom": "2021-02-01",
          "omsorgspengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.34600"
      },
      {
        "kontekster": [
          41,
          1,
          2,
          10,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Opplæringspenger",
        "melding": "Trenger informasjon om opplæringspengerytelser fra Infotrygd",
        "detaljer": {
          "opplæringspengerFom": "2021-02-01",
          "opplæringspengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.34600"
      },
      {
        "kontekster": [
          41,
          1,
          2,
          10,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Institusjonsopphold",
        "melding": "Trenger informasjon om institusjonsopphold fra Inst2",
        "detaljer": {
          "institusjonsoppholdFom": "2021-02-01",
          "institusjonsoppholdTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.34600"
      },
      {
        "kontekster": [
          41,
          1,
          2,
          10,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Arbeidsavklaringspenger",
        "melding": "Trenger informasjon om arbeidsavklaringspenger",
        "detaljer": {
          "periodeFom": "2020-08-01",
          "periodeTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.34600"
      },
      {
        "kontekster": [
          41,
          1,
          2,
          10,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dagpenger",
        "melding": "Trenger informasjon om dagpenger",
        "detaljer": {
          "periodeFom": "2020-08-01",
          "periodeTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.34600"
      },
      {
        "kontekster": [
          41,
          1,
          2,
          10,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dødsinfo",
        "melding": "Trenger informasjon om dødsdato fra PDL",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.34600"
      },
      {
        "kontekster": [
          41,
          1,
          2,
          10,
          20,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forespør sykdoms- og inntektshistorikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.34600"
      },
      {
        "kontekster": [
          42,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler historiske utbetalinger og inntekter",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35600"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35600"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35600"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35600"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen foreldrepenge- eller svangerskapsytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35600"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen pleiepengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35600"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen omsorgspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35600"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen opplæringspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35700"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen institusjonsoppholdsperioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35700"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ingen avviste dager på grunn av 20 % samlet sykdomsgrad-regel for denne perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35800"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Redusert utbetaling minst én dag på grunn av inntekt over 6G",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35900"
      },
      {
        "kontekster": [
          42,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Maksimalt antall sykedager overskrides ikke i perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.35900"
      },
      {
        "kontekster": [
          43,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler historiske utbetalinger og inntekter",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36700"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Perioden er en direkte overgang fra periode med opphav i Infotrygd",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36800"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36800"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36800"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36800"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen foreldrepenge- eller svangerskapsytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36800"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen pleiepengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36800"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen omsorgspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36800"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen opplæringspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36800"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen institusjonsoppholdsperioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.36800"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ingen avviste dager på grunn av 20 % samlet sykdomsgrad-regel for denne perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.37000"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Redusert utbetaling minst én dag på grunn av inntekt over 6G",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.37100"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Maksimalt antall sykedager overskrides ikke i perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.37100"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingslinjer bygget vellykket",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.37100"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Perioden er en forlengelse, av type OVERGANG_FRA_IT",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.37200"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Saken oppfyller krav for behandling, settes til \"Avventer simulering\"",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.37200"
      },
      {
        "kontekster": [
          43,
          1,
          7,
          8,
          22,
          29,
          44
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Simulering",
        "melding": "Trenger simulering fra Oppdragssystemet",
        "detaljer": {
          "maksdato": "2021-12-31",
          "saksbehandler": "SPLEIS",
          "mottaker": "arbeidsgiver 2",
          "fagområde": "SPREF",
          "linjer": [
            {
              "fom": "2021-01-27",
              "tom": "2021-01-31",
              "satstype": "DAG",
              "sats": 1169,
              "dagsats": 1169,
              "lønn": 1431,
              "grad": 100.0,
              "stønadsdager": 3,
              "totalbeløp": 3507,
              "endringskode": "NY",
              "delytelseId": 1,
              "refDelytelseId": null,
              "refFagsystemId": null,
              "statuskode": null,
              "datoStatusFom": null,
              "klassekode": "SPREFAG-IOP"
            }
          ],
          "fagsystemId": "UEAOXSYVCBEMTBFTNDJ7J2LCSI",
          "endringskode": "NY"
        },
        "tidsstempel": "2021-07-23T09:01:54.37500"
      },
      {
        "kontekster": [
          45,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler simulering",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.38400"
      },
      {
        "kontekster": [
          45,
          1,
          7,
          8,
          29
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Simulering kom frem til et annet totalbeløp. Kontroller beløpet til utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.38400"
      },
      {
        "kontekster": [
          45,
          1,
          7,
          8,
          29,
          32,
          44
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Godkjenning",
        "melding": "Forespør godkjenning fra saksbehandler",
        "detaljer": {
          "periodeFom": "2021-01-27",
          "periodeTom": "2021-01-31",
          "skjæringstidspunkt": "2021-01-20",
          "periodetype": "OVERGANG_FRA_IT",
          "utbetalingtype": "UTBETALING",
          "inntektskilde": "FLERE_ARBEIDSGIVERE",
          "warnings": {
            "aktiviteter": [],
            "kontekster": []
          },
          "aktiveVedtaksperioder": [
            {
              "orgnummer": "arbeidsgiver 2",
              "vedtaksperiodeId": "09ca19d1-871f-40bc-a074-25a5887cbfc2",
              "periodetype": "OVERGANG_FRA_IT"
            },
            {
              "orgnummer": "arbeidsgiver 1",
              "vedtaksperiodeId": "1eba2d4e-d390-4326-aad2-927b0e17b37d",
              "periodetype": "INFOTRYGDFORLENGELSE"
            }
          ],
          "arbeidsforholdId": null
        },
        "tidsstempel": "2021-07-23T09:01:54.38500"
      },
      {
        "kontekster": [
          46,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgodkjenning",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.38700"
      },
      {
        "kontekster": [
          46,
          1,
          7
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetaling markert som godkjent av saksbehandler Ola Nordmann 2021-07-23T09:01:54.387184859",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.38700"
      },
      {
        "kontekster": [
          46,
          1,
          7,
          44
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Utbetaling",
        "melding": "Trenger å sende utbetaling til Oppdrag",
        "detaljer": {
          "mottaker": "arbeidsgiver 2",
          "fagområde": "SPREF",
          "linjer": [
            {
              "fom": "2021-01-27",
              "tom": "2021-01-31",
              "satstype": "DAG",
              "sats": 1169,
              "dagsats": 1169,
              "lønn": 1431,
              "grad": 100.0,
              "stønadsdager": 3,
              "totalbeløp": 3507,
              "endringskode": "NY",
              "delytelseId": 1,
              "refDelytelseId": null,
              "refFagsystemId": null,
              "statuskode": null,
              "datoStatusFom": null,
              "klassekode": "SPREFAG-IOP"
            }
          ],
          "fagsystemId": "UEAOXSYVCBEMTBFTNDJ7J2LCSI",
          "endringskode": "NY",
          "saksbehandler": "Ola Nordmann",
          "maksdato": "2021-12-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.39600"
      },
      {
        "kontekster": [
          47,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetaling overført",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.41300"
      },
      {
        "kontekster": [
          47,
          1,
          7,
          44
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingen ble overført til Oppdrag/UR 2021-07-23T09:01:54.413199723, og har fått avstemmingsnøkkel 123456",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.41300"
      },
      {
        "kontekster": [
          48,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.42300"
      },
      {
        "kontekster": [
          48,
          1,
          7,
          44,
          8,
          36
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "OK fra Oppdragssystemet",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.43600"
      },
      {
        "kontekster": [
          48,
          1,
          7,
          44,
          8,
          36,
          37,
          44,
          49,
          13,
          15,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "InntekterForSykepengegrunnlag",
        "melding": "Trenger inntekter for sykepengegrunnlag",
        "detaljer": {
          "beregningStart": "2020-10",
          "beregningSlutt": "2020-12"
        },
        "tidsstempel": "2021-07-23T09:01:54.43700"
      },
      {
        "kontekster": [
          48,
          1,
          7,
          44,
          8,
          36,
          37,
          44,
          49,
          13,
          15,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "ArbeidsforholdV2",
        "melding": "Trenger informasjon om arbeidsforhold",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.43800"
      },
      {
        "kontekster": [
          50,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgrunnlag",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.46000"
      },
      {
        "kontekster": [
          50,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Foreldrepenger",
        "melding": "Trenger informasjon om foreldrepengeytelser fra FPSAK",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.46100"
      },
      {
        "kontekster": [
          50,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Pleiepenger",
        "melding": "Trenger informasjon om pleiepengeytelser fra Infotrygd",
        "detaljer": {
          "pleiepengerFom": "2021-02-01",
          "pleiepengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.46100"
      },
      {
        "kontekster": [
          50,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Omsorgspenger",
        "melding": "Trenger informasjon om omsorgspengerytelser fra Infotrygd",
        "detaljer": {
          "omsorgspengerFom": "2021-02-01",
          "omsorgspengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.46100"
      },
      {
        "kontekster": [
          50,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Opplæringspenger",
        "melding": "Trenger informasjon om opplæringspengerytelser fra Infotrygd",
        "detaljer": {
          "opplæringspengerFom": "2021-02-01",
          "opplæringspengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.46100"
      },
      {
        "kontekster": [
          50,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Institusjonsopphold",
        "melding": "Trenger informasjon om institusjonsopphold fra Inst2",
        "detaljer": {
          "institusjonsoppholdFom": "2021-02-01",
          "institusjonsoppholdTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.46100"
      },
      {
        "kontekster": [
          50,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Arbeidsavklaringspenger",
        "melding": "Trenger informasjon om arbeidsavklaringspenger",
        "detaljer": {
          "periodeFom": "2020-08-01",
          "periodeTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.46100"
      },
      {
        "kontekster": [
          50,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dagpenger",
        "melding": "Trenger informasjon om dagpenger",
        "detaljer": {
          "periodeFom": "2020-08-01",
          "periodeTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.46100"
      },
      {
        "kontekster": [
          50,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dødsinfo",
        "melding": "Trenger informasjon om dødsdato fra PDL",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.46100"
      },
      {
        "kontekster": [
          50,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forespør sykdoms- og inntektshistorikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.46100"
      },
      {
        "kontekster": [
          51,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler historiske utbetalinger og inntekter",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47200"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47200"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47200"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47300"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen foreldrepenge- eller svangerskapsytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47300"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen pleiepengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47300"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen omsorgspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47300"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen opplæringspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47300"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen institusjonsoppholdsperioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47300"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ingen avviste dager på grunn av 20 % samlet sykdomsgrad-regel for denne perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47400"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Redusert utbetaling minst én dag på grunn av inntekt over 6G",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47600"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Maksimalt antall sykedager overskrides ikke i perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47600"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22,
          27,
          2,
          10,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Foreldrepenger",
        "melding": "Trenger informasjon om foreldrepengeytelser fra FPSAK",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47700"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22,
          27,
          2,
          10,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Pleiepenger",
        "melding": "Trenger informasjon om pleiepengeytelser fra Infotrygd",
        "detaljer": {
          "pleiepengerFom": "2021-02-01",
          "pleiepengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.47700"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22,
          27,
          2,
          10,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Omsorgspenger",
        "melding": "Trenger informasjon om omsorgspengerytelser fra Infotrygd",
        "detaljer": {
          "omsorgspengerFom": "2021-02-01",
          "omsorgspengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.47700"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22,
          27,
          2,
          10,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Opplæringspenger",
        "melding": "Trenger informasjon om opplæringspengerytelser fra Infotrygd",
        "detaljer": {
          "opplæringspengerFom": "2021-02-01",
          "opplæringspengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.47700"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22,
          27,
          2,
          10,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Institusjonsopphold",
        "melding": "Trenger informasjon om institusjonsopphold fra Inst2",
        "detaljer": {
          "institusjonsoppholdFom": "2021-02-01",
          "institusjonsoppholdTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.47700"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22,
          27,
          2,
          10,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Arbeidsavklaringspenger",
        "melding": "Trenger informasjon om arbeidsavklaringspenger",
        "detaljer": {
          "periodeFom": "2020-08-01",
          "periodeTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.47700"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22,
          27,
          2,
          10,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dagpenger",
        "melding": "Trenger informasjon om dagpenger",
        "detaljer": {
          "periodeFom": "2020-08-01",
          "periodeTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.47700"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22,
          27,
          2,
          10,
          27,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dødsinfo",
        "melding": "Trenger informasjon om dødsdato fra PDL",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47700"
      },
      {
        "kontekster": [
          51,
          1,
          7,
          13,
          22,
          27,
          2,
          10,
          27,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forespør sykdoms- og inntektshistorikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.47700"
      },
      {
        "kontekster": [
          52,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler historiske utbetalinger og inntekter",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48600"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48700"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48700"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48700"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen foreldrepenge- eller svangerskapsytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48700"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen pleiepengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48700"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen omsorgspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48700"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen opplæringspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48700"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen institusjonsoppholdsperioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48700"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ingen avviste dager på grunn av 20 % samlet sykdomsgrad-regel for denne perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.48800"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Redusert utbetaling minst én dag på grunn av inntekt over 6G",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.49000"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Maksimalt antall sykedager overskrides ikke i perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.49000"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingslinjer bygget vellykket",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.49100"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingslinjer bygget vellykket",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.49100"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Perioden er en forlengelse, av type INFOTRYGDFORLENGELSE",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.49200"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Saken oppfyller krav for behandling, settes til \"Avventer simulering\"",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.49200"
      },
      {
        "kontekster": [
          52,
          1,
          2,
          10,
          22,
          29,
          53
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Simulering",
        "melding": "Trenger simulering fra Oppdragssystemet",
        "detaljer": {
          "maksdato": "2021-12-31",
          "saksbehandler": "SPLEIS",
          "mottaker": "arbeidsgiver 1",
          "fagområde": "SPREF",
          "linjer": [
            {
              "fom": "2021-01-27",
              "tom": "2021-02-10",
              "satstype": "DAG",
              "sats": 1170,
              "dagsats": 1170,
              "lønn": 1431,
              "grad": 100.0,
              "stønadsdager": 11,
              "totalbeløp": 12870,
              "endringskode": "ENDR",
              "delytelseId": 1,
              "refDelytelseId": null,
              "refFagsystemId": null,
              "statuskode": null,
              "datoStatusFom": null,
              "klassekode": "SPREFAG-IOP"
            }
          ],
          "fagsystemId": "VI3L47ZMIFH25OX3NAJTCZLQEU",
          "endringskode": "ENDR"
        },
        "tidsstempel": "2021-07-23T09:01:54.49700"
      },
      {
        "kontekster": [
          54,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler simulering",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.50800"
      },
      {
        "kontekster": [
          54,
          1,
          2,
          10,
          29
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Simulering kom frem til et annet totalbeløp. Kontroller beløpet til utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.50800"
      },
      {
        "kontekster": [
          54,
          1,
          2,
          10,
          29,
          32,
          53
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Godkjenning",
        "melding": "Forespør godkjenning fra saksbehandler",
        "detaljer": {
          "periodeFom": "2021-02-01",
          "periodeTom": "2021-02-10",
          "skjæringstidspunkt": "2021-01-20",
          "periodetype": "INFOTRYGDFORLENGELSE",
          "utbetalingtype": "UTBETALING",
          "inntektskilde": "FLERE_ARBEIDSGIVERE",
          "warnings": {
            "aktiviteter": [],
            "kontekster": []
          },
          "aktiveVedtaksperioder": [
            {
              "orgnummer": "arbeidsgiver 1",
              "vedtaksperiodeId": "1eba2d4e-d390-4326-aad2-927b0e17b37d",
              "periodetype": "INFOTRYGDFORLENGELSE"
            },
            {
              "orgnummer": "arbeidsgiver 2",
              "vedtaksperiodeId": "f07bd5a2-de97-4b47-9d2f-eee1e656fef4",
              "periodetype": "INFOTRYGDFORLENGELSE"
            }
          ],
          "arbeidsforholdId": null
        },
        "tidsstempel": "2021-07-23T09:01:54.50900"
      },
      {
        "kontekster": [
          55,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgodkjenning",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.51100"
      },
      {
        "kontekster": [
          55,
          1,
          2
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetaling markert som godkjent av saksbehandler Ola Nordmann 2021-07-23T09:01:54.511757409",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.51100"
      },
      {
        "kontekster": [
          55,
          1,
          2,
          53
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Utbetaling",
        "melding": "Trenger å sende utbetaling til Oppdrag",
        "detaljer": {
          "mottaker": "arbeidsgiver 1",
          "fagområde": "SPREF",
          "linjer": [
            {
              "fom": "2021-01-27",
              "tom": "2021-02-10",
              "satstype": "DAG",
              "sats": 1170,
              "dagsats": 1170,
              "lønn": 1431,
              "grad": 100.0,
              "stønadsdager": 11,
              "totalbeløp": 12870,
              "endringskode": "ENDR",
              "delytelseId": 1,
              "refDelytelseId": null,
              "refFagsystemId": null,
              "statuskode": null,
              "datoStatusFom": null,
              "klassekode": "SPREFAG-IOP"
            }
          ],
          "fagsystemId": "VI3L47ZMIFH25OX3NAJTCZLQEU",
          "endringskode": "ENDR",
          "saksbehandler": "Ola Nordmann",
          "maksdato": "2021-12-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.52100"
      },
      {
        "kontekster": [
          56,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetaling overført",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.53400"
      },
      {
        "kontekster": [
          56,
          1,
          2,
          53
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingen ble overført til Oppdrag/UR 2021-07-23T09:01:54.534410969, og har fått avstemmingsnøkkel 123456",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.53400"
      },
      {
        "kontekster": [
          57,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.54400"
      },
      {
        "kontekster": [
          57,
          1,
          2,
          53,
          10,
          36
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "OK fra Oppdragssystemet",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.55500"
      },
      {
        "kontekster": [
          57,
          1,
          2,
          53,
          10,
          36,
          37,
          53,
          58,
          7,
          13,
          27,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "InntekterForSykepengegrunnlag",
        "melding": "Trenger inntekter for sykepengegrunnlag",
        "detaljer": {
          "beregningStart": "2020-10",
          "beregningSlutt": "2020-12"
        },
        "tidsstempel": "2021-07-23T09:01:54.55600"
      },
      {
        "kontekster": [
          57,
          1,
          2,
          53,
          10,
          36,
          37,
          53,
          58,
          7,
          13,
          27,
          20
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "ArbeidsforholdV2",
        "melding": "Trenger informasjon om arbeidsforhold",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.55600"
      },
      {
        "kontekster": [
          59,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgrunnlag",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.57900"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Foreldrepenger",
        "melding": "Trenger informasjon om foreldrepengeytelser fra FPSAK",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58000"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Pleiepenger",
        "melding": "Trenger informasjon om pleiepengeytelser fra Infotrygd",
        "detaljer": {
          "pleiepengerFom": "2021-02-01",
          "pleiepengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.58000"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Omsorgspenger",
        "melding": "Trenger informasjon om omsorgspengerytelser fra Infotrygd",
        "detaljer": {
          "omsorgspengerFom": "2021-02-01",
          "omsorgspengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.58000"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Opplæringspenger",
        "melding": "Trenger informasjon om opplæringspengerytelser fra Infotrygd",
        "detaljer": {
          "opplæringspengerFom": "2021-02-01",
          "opplæringspengerTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.58000"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Institusjonsopphold",
        "melding": "Trenger informasjon om institusjonsopphold fra Inst2",
        "detaljer": {
          "institusjonsoppholdFom": "2021-02-01",
          "institusjonsoppholdTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.58000"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Arbeidsavklaringspenger",
        "melding": "Trenger informasjon om arbeidsavklaringspenger",
        "detaljer": {
          "periodeFom": "2020-08-01",
          "periodeTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.58000"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dagpenger",
        "melding": "Trenger informasjon om dagpenger",
        "detaljer": {
          "periodeFom": "2020-08-01",
          "periodeTom": "2021-02-10"
        },
        "tidsstempel": "2021-07-23T09:01:54.58000"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Dødsinfo",
        "melding": "Trenger informasjon om dødsdato fra PDL",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58000"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22,
          60
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forkaster utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58000"
      },
      {
        "kontekster": [
          59,
          1,
          7,
          13,
          20,
          22,
          60
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Forespør sykdoms- og inntektshistorikk",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58600"
      },
      {
        "kontekster": [
          61,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler historiske utbetalinger og inntekter",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58700"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker utbetalte perioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58800"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker inntektsopplysninger",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58800"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Sjekker arbeidskategorikoder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58800"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen foreldrepenge- eller svangerskapsytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58800"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen pleiepengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58800"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen omsorgspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58800"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen opplæringspengeytelser",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58800"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Bruker har ingen institusjonsoppholdsperioder",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58800"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Ingen avviste dager på grunn av 20 % samlet sykdomsgrad-regel for denne perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.58900"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Redusert utbetaling minst én dag på grunn av inntekt over 6G",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.59000"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Maksimalt antall sykedager overskrides ikke i perioden",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.59100"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingslinjer bygget vellykket",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.59100"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Perioden er en forlengelse, av type INFOTRYGDFORLENGELSE",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.59200"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Saken oppfyller krav for behandling, settes til \"Avventer simulering\"",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.59200"
      },
      {
        "kontekster": [
          61,
          1,
          7,
          13,
          22,
          29,
          62
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Simulering",
        "melding": "Trenger simulering fra Oppdragssystemet",
        "detaljer": {
          "maksdato": "2021-12-31",
          "saksbehandler": "SPLEIS",
          "mottaker": "arbeidsgiver 2",
          "fagområde": "SPREF",
          "linjer": [
            {
              "fom": "2021-01-27",
              "tom": "2021-02-10",
              "satstype": "DAG",
              "sats": 1169,
              "dagsats": 1169,
              "lønn": 1431,
              "grad": 100.0,
              "stønadsdager": 11,
              "totalbeløp": 12859,
              "endringskode": "ENDR",
              "delytelseId": 1,
              "refDelytelseId": null,
              "refFagsystemId": null,
              "statuskode": null,
              "datoStatusFom": null,
              "klassekode": "SPREFAG-IOP"
            }
          ],
          "fagsystemId": "UEAOXSYVCBEMTBFTNDJ7J2LCSI",
          "endringskode": "ENDR"
        },
        "tidsstempel": "2021-07-23T09:01:54.59400"
      },
      {
        "kontekster": [
          63,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler simulering",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.59500"
      },
      {
        "kontekster": [
          63,
          1,
          7,
          13,
          29
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Simulering kom frem til et annet totalbeløp. Kontroller beløpet til utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.59500"
      },
      {
        "kontekster": [
          63,
          1,
          7,
          13,
          29,
          32,
          62
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Godkjenning",
        "melding": "Forespør godkjenning fra saksbehandler",
        "detaljer": {
          "periodeFom": "2021-02-01",
          "periodeTom": "2021-02-10",
          "skjæringstidspunkt": "2021-01-20",
          "periodetype": "INFOTRYGDFORLENGELSE",
          "utbetalingtype": "UTBETALING",
          "inntektskilde": "FLERE_ARBEIDSGIVERE",
          "warnings": {
            "aktiviteter": [],
            "kontekster": []
          },
          "aktiveVedtaksperioder": [
            {
              "orgnummer": "arbeidsgiver 2",
              "vedtaksperiodeId": "f07bd5a2-de97-4b47-9d2f-eee1e656fef4",
              "periodetype": "INFOTRYGDFORLENGELSE"
            }
          ],
          "arbeidsforholdId": null
        },
        "tidsstempel": "2021-07-23T09:01:54.59600"
      },
      {
        "kontekster": [
          64,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetalingsgodkjenning",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.59900"
      },
      {
        "kontekster": [
          64,
          1,
          7
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetaling markert som godkjent av saksbehandler Ola Nordmann 2021-07-23T09:01:54.599903180",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.60000"
      },
      {
        "kontekster": [
          64,
          1,
          7,
          62
        ],
        "alvorlighetsgrad": "BEHOV",
        "behovtype": "Utbetaling",
        "melding": "Trenger å sende utbetaling til Oppdrag",
        "detaljer": {
          "mottaker": "arbeidsgiver 2",
          "fagområde": "SPREF",
          "linjer": [
            {
              "fom": "2021-01-27",
              "tom": "2021-02-10",
              "satstype": "DAG",
              "sats": 1169,
              "dagsats": 1169,
              "lønn": 1431,
              "grad": 100.0,
              "stønadsdager": 11,
              "totalbeløp": 12859,
              "endringskode": "ENDR",
              "delytelseId": 1,
              "refDelytelseId": null,
              "refFagsystemId": null,
              "statuskode": null,
              "datoStatusFom": null,
              "klassekode": "SPREFAG-IOP"
            }
          ],
          "fagsystemId": "UEAOXSYVCBEMTBFTNDJ7J2LCSI",
          "endringskode": "ENDR",
          "saksbehandler": "Ola Nordmann",
          "maksdato": "2021-12-31"
        },
        "tidsstempel": "2021-07-23T09:01:54.60700"
      },
      {
        "kontekster": [
          65,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetaling overført",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.61700"
      },
      {
        "kontekster": [
          65,
          1,
          7,
          62
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Utbetalingen ble overført til Oppdrag/UR 2021-07-23T09:01:54.617535100, og har fått avstemmingsnøkkel 123456",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.61700"
      },
      {
        "kontekster": [
          66,
          1
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "Behandler utbetaling",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.62600"
      },
      {
        "kontekster": [
          66,
          1,
          7,
          62,
          13,
          36
        ],
        "alvorlighetsgrad": "INFO",
        "melding": "OK fra Oppdragssystemet",
        "detaljer": {},
        "tidsstempel": "2021-07-23T09:01:54.63500"
      }
    ],
    "kontekster": [
      {
        "kontekstType": "Sykmelding",
        "kontekstMap": {
          "meldingsreferanseId": "69e98032-96b6-4d27-9744-81cd81a6cca9",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Person",
        "kontekstMap": {
          "fødselsnummer": "12020052345",
          "aktørId": "42"
        }
      },
      {
        "kontekstType": "Arbeidsgiver",
        "kontekstMap": {
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Vedtaksperiode",
        "kontekstMap": {
          "vedtaksperiodeId": "d9568be3-bdcc-4af3-8dae-96d7b851f889"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "START"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "MOTTATT_SYKMELDING_FERDIG_GAP"
        }
      },
      {
        "kontekstType": "Sykmelding",
        "kontekstMap": {
          "meldingsreferanseId": "ac7fabb5-f53e-42a8-9130-77810507bdfd",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Arbeidsgiver",
        "kontekstMap": {
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Vedtaksperiode",
        "kontekstMap": {
          "vedtaksperiodeId": "09ca19d1-871f-40bc-a074-25a5887cbfc2"
        }
      },
      {
        "kontekstType": "Sykmelding",
        "kontekstMap": {
          "meldingsreferanseId": "70624662-88a2-4a43-b56b-a1befbb42cbd",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Vedtaksperiode",
        "kontekstMap": {
          "vedtaksperiodeId": "1eba2d4e-d390-4326-aad2-927b0e17b37d"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "MOTTATT_SYKMELDING_UFERDIG_FORLENGELSE"
        }
      },
      {
        "kontekstType": "Sykmelding",
        "kontekstMap": {
          "meldingsreferanseId": "11e20786-9f55-42d3-ade6-ff14c4d4587b",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Vedtaksperiode",
        "kontekstMap": {
          "vedtaksperiodeId": "f07bd5a2-de97-4b47-9d2f-eee1e656fef4"
        }
      },
      {
        "kontekstType": "Søknad",
        "kontekstMap": {
          "meldingsreferanseId": "4833e793-a45f-483a-9809-2ee85716ad77",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "AVVENTER_INNTEKTSMELDING_UFERDIG_FORLENGELSE"
        }
      },
      {
        "kontekstType": "Søknad",
        "kontekstMap": {
          "meldingsreferanseId": "6122fe6b-4ea5-481c-8430-b4854777f2d3",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Søknad",
        "kontekstMap": {
          "meldingsreferanseId": "f158d38c-851d-490b-8de5-92341df6b502",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "AVVENTER_INNTEKTSMELDING_ELLER_HISTORIKK_FERDIG_GAP"
        }
      },
      {
        "kontekstType": "Utbetalingshistorikk",
        "kontekstMap": {
          "meldingsreferanseId": "1b1b8bec-1462-4f53-b897-a5abbc144980",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "AVVENTER_UTBETALINGSGRUNNLAG"
        }
      },
      {
        "kontekstType": "Utbetalingsgrunnlag",
        "kontekstMap": {
          "meldingsreferanseId": "392b733f-2790-426c-a543-d26ca9bed308",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "AVVENTER_HISTORIKK"
        }
      },
      {
        "kontekstType": "Ytelser",
        "kontekstMap": {
          "meldingsreferanseId": "3f4471cd-956e-49ee-b1f9-02219e9f03bd",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Søknad",
        "kontekstMap": {
          "meldingsreferanseId": "2c4baa6d-4e0d-4bce-8122-6e5c7f351123",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Utbetalingsgrunnlag",
        "kontekstMap": {
          "meldingsreferanseId": "c6671cdc-f2ca-499c-affa-84ca42c17385",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Ytelser",
        "kontekstMap": {
          "meldingsreferanseId": "92f40463-2a0f-432a-97ab-8c6de4a67f72",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "AVVENTER_ARBEIDSGIVERE"
        }
      },
      {
        "kontekstType": "Ytelser",
        "kontekstMap": {
          "meldingsreferanseId": "1a700ff5-827f-4829-aef0-4df904ca42b1",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "AVVENTER_SIMULERING"
        }
      },
      {
        "kontekstType": "Utbetaling",
        "kontekstMap": {
          "utbetalingId": "616858c5-7a17-462d-8a44-c4da38e47676"
        }
      },
      {
        "kontekstType": "Simulering",
        "kontekstMap": {
          "meldingsreferanseId": "28e9f59a-c335-4f3a-bd2b-d8684baa58b6",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "AVVENTER_GODKJENNING"
        }
      },
      {
        "kontekstType": "Utbetalingsgodkjenning",
        "kontekstMap": {
          "meldingsreferanseId": "a09b7a4d-95b6-40e5-8fe6-4a7f6b187d90",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "UtbetalingOverført",
        "kontekstMap": {
          "meldingsreferanseId": "b950a7f7-a441-4616-a939-4274b5e87b7b",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "UtbetalingHendelse",
        "kontekstMap": {
          "meldingsreferanseId": "9b320931-dd45-4820-804e-41333effb6aa",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "TIL_UTBETALING"
        }
      },
      {
        "kontekstType": "Tilstand",
        "kontekstMap": {
          "tilstand": "AVSLUTTET"
        }
      },
      {
        "kontekstType": "GjenopptaBehandling",
        "kontekstMap": {
          "meldingsreferanseId": "9b320931-dd45-4820-804e-41333effb6aa",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Utbetalingsgrunnlag",
        "kontekstMap": {
          "meldingsreferanseId": "188a24f2-3bfd-4162-bfe3-e9ce62d5877b",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Utbetaling",
        "kontekstMap": {
          "utbetalingId": "b9a6537e-f266-4a1b-a24c-e2c70fdaf4fa"
        }
      },
      {
        "kontekstType": "Utbetalingsgrunnlag",
        "kontekstMap": {
          "meldingsreferanseId": "bf5ba7f2-6c30-49af-bb7b-a31d38ff977c",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Ytelser",
        "kontekstMap": {
          "meldingsreferanseId": "86608c5b-fcb8-4014-934a-4c3f9a0fcdc6",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Ytelser",
        "kontekstMap": {
          "meldingsreferanseId": "a74b4c9e-d54e-4183-be1a-1f67ab4f0dcd",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Utbetaling",
        "kontekstMap": {
          "utbetalingId": "b4bc58e0-8279-4871-8e66-dc4a6bd49433"
        }
      },
      {
        "kontekstType": "Simulering",
        "kontekstMap": {
          "meldingsreferanseId": "f26c4c19-95f3-4a51-b933-e92366bd60e4",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Utbetalingsgodkjenning",
        "kontekstMap": {
          "meldingsreferanseId": "4dee411a-c297-4ba8-9fe5-a72723b6fe67",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "UtbetalingOverført",
        "kontekstMap": {
          "meldingsreferanseId": "1cf321f2-487c-4a84-87c7-2c08d3a6440e",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "UtbetalingHendelse",
        "kontekstMap": {
          "meldingsreferanseId": "139227b6-791b-446f-9aa1-25c92741238b",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "GjenopptaBehandling",
        "kontekstMap": {
          "meldingsreferanseId": "139227b6-791b-446f-9aa1-25c92741238b",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Utbetalingsgrunnlag",
        "kontekstMap": {
          "meldingsreferanseId": "c0ac9d64-48a4-415c-82d8-75a508cd8020",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Ytelser",
        "kontekstMap": {
          "meldingsreferanseId": "efbd2219-4347-46f1-b9ef-fb0471ec0138",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Ytelser",
        "kontekstMap": {
          "meldingsreferanseId": "fc85c3d6-88fa-430f-86bb-9a44a51c1558",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Utbetaling",
        "kontekstMap": {
          "utbetalingId": "e4dcf225-7a09-446e-887c-2a9320141b4c"
        }
      },
      {
        "kontekstType": "Simulering",
        "kontekstMap": {
          "meldingsreferanseId": "73c04091-96be-4d1b-aa96-2b93857db855",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Utbetalingsgodkjenning",
        "kontekstMap": {
          "meldingsreferanseId": "df9b0cff-c55e-4b41-8975-97ed28ae51eb",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "UtbetalingOverført",
        "kontekstMap": {
          "meldingsreferanseId": "7c4c6d64-ac65-41ed-ba86-59a3f0fe01ea",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "UtbetalingHendelse",
        "kontekstMap": {
          "meldingsreferanseId": "cbcfa162-7d0c-4319-8de9-de26880a467a",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "GjenopptaBehandling",
        "kontekstMap": {
          "meldingsreferanseId": "cbcfa162-7d0c-4319-8de9-de26880a467a",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 1"
        }
      },
      {
        "kontekstType": "Utbetalingsgrunnlag",
        "kontekstMap": {
          "meldingsreferanseId": "01ac0847-b4d8-4f74-a45a-2154183d2106",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Utbetaling",
        "kontekstMap": {
          "utbetalingId": "4a7ec9de-00d6-409b-895c-b2a3b658b3e6"
        }
      },
      {
        "kontekstType": "Ytelser",
        "kontekstMap": {
          "meldingsreferanseId": "e90a7ccb-223d-4f5a-b487-61a2cfa8da99",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Utbetaling",
        "kontekstMap": {
          "utbetalingId": "3f5f02f4-5096-4d64-914b-0de4a3d47df2"
        }
      },
      {
        "kontekstType": "Simulering",
        "kontekstMap": {
          "meldingsreferanseId": "f2b8f6fb-51f7-4aa4-a825-e1e76b1c9a76",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "Utbetalingsgodkjenning",
        "kontekstMap": {
          "meldingsreferanseId": "dcfddd01-e223-40a3-8f95-5468797e9e3f",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "UtbetalingOverført",
        "kontekstMap": {
          "meldingsreferanseId": "59b8329f-f6fc-4f55-bb45-417992a07028",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      },
      {
        "kontekstType": "UtbetalingHendelse",
        "kontekstMap": {
          "meldingsreferanseId": "65175b20-41df-47c6-b946-37e85277db88",
          "aktørId": "42",
          "fødselsnummer": "12020052345",
          "organisasjonsnummer": "arbeidsgiver 2"
        }
      }
    ]
  },
  "arbeidsgivere": [
    {
      "organisasjonsnummer": "arbeidsgiver 1",
      "id": "eaba5ea8-4c98-4cbd-be60-607d90e5d227",
      "beregnetUtbetalingstidslinjer": [
        {
          "id": "5ed48ba2-f44c-4ece-b96f-c129184b4b16",
          "sykdomshistorikkElementId": "4a94231a-ea04-4322-b207-dda54a569fc2",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "8d9acba9-27f2-4050-a79f-13ae488e90fe",
          "tidsstempel": "2021-07-23T09:01:53.517288946",
          "organisasjonsnummer": "arbeidsgiver 1",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "id": "89aaa1fc-600c-4fa7-9762-291184cf5272",
          "sykdomshistorikkElementId": "4a94231a-ea04-4322-b207-dda54a569fc2",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "c05621fd-d5ed-413d-9cef-e01dc6f412a5",
          "tidsstempel": "2021-07-23T09:01:53.548390447",
          "organisasjonsnummer": "arbeidsgiver 2",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "id": "6f70f4dc-d578-409d-8f93-386c2f3dd24f",
          "sykdomshistorikkElementId": "4a94231a-ea04-4322-b207-dda54a569fc2",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "48c4d0e7-eb97-44c2-beec-27f31c6d30bd",
          "tidsstempel": "2021-07-23T09:01:53.556166719",
          "organisasjonsnummer": "arbeidsgiver 1",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "id": "4109a080-0744-48ce-a097-28726170533d",
          "sykdomshistorikkElementId": "4a94231a-ea04-4322-b207-dda54a569fc2",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "536fb4ee-b6e4-4ea6-b8d1-39578e5c210b",
          "tidsstempel": "2021-07-23T09:01:54.359601452",
          "organisasjonsnummer": "arbeidsgiver 1",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "fd19dc92-ebc5-4828-9cc3-499e504ef543",
          "sykdomshistorikkElementId": "4a94231a-ea04-4322-b207-dda54a569fc2",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "52416c61-b8ed-43be-ab63-1f35edb0e642",
          "tidsstempel": "2021-07-23T09:01:54.371146879",
          "organisasjonsnummer": "arbeidsgiver 2",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "id": "3e009ec7-4c33-4534-ba71-049e266fc9d6",
          "sykdomshistorikkElementId": "4a94231a-ea04-4322-b207-dda54a569fc2",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "23db23e8-e3d1-4360-8ea6-7fdcfd28c063",
          "tidsstempel": "2021-07-23T09:01:54.476421141",
          "organisasjonsnummer": "arbeidsgiver 2",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "5c8801ec-8d45-4aa9-af75-fbbff2afd47b",
          "sykdomshistorikkElementId": "4a94231a-ea04-4322-b207-dda54a569fc2",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "d48ae0ec-c6a4-46c2-ac67-35aaac9391f7",
          "tidsstempel": "2021-07-23T09:01:54.490290431",
          "organisasjonsnummer": "arbeidsgiver 1",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "0fde9314-dad8-44f0-b9db-8bdac32c52a4",
          "sykdomshistorikkElementId": "4a94231a-ea04-4322-b207-dda54a569fc2",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "01f35a16-c62e-4170-b5e9-4783982458cf",
          "tidsstempel": "2021-07-23T09:01:54.591078942",
          "organisasjonsnummer": "arbeidsgiver 2",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          }
        }
      ],
      "refusjonOpphører": [],
      "inntektshistorikk": [
        {
          "id": "01f35a16-c62e-4170-b5e9-4783982458cf",
          "inntektsopplysninger": [
            {
              "id": "a1543de4-8228-496b-8f06-14233cb1533d",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.588642775"
            }
          ]
        },
        {
          "id": "d48ae0ec-c6a4-46c2-ac67-35aaac9391f7",
          "inntektsopplysninger": [
            {
              "id": "6f545ac6-68a3-40f9-aedc-bc6c225dab6e",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.487828876"
            }
          ]
        },
        {
          "id": "23db23e8-e3d1-4360-8ea6-7fdcfd28c063",
          "inntektsopplysninger": [
            {
              "id": "0a6ae09b-d78b-4365-840e-38b1484f6b49",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.473353317"
            }
          ]
        },
        {
          "id": "52416c61-b8ed-43be-ab63-1f35edb0e642",
          "inntektsopplysninger": [
            {
              "id": "0aa07549-5031-4247-b43c-50cb25a82fdb",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.369238808"
            }
          ]
        },
        {
          "id": "536fb4ee-b6e4-4ea6-b8d1-39578e5c210b",
          "inntektsopplysninger": [
            {
              "id": "c58e5a60-3241-4833-bbaf-007e36a84bd1",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.357139308"
            }
          ]
        },
        {
          "id": "48c4d0e7-eb97-44c2-beec-27f31c6d30bd",
          "inntektsopplysninger": [
            {
              "id": "d3a3a1e7-e821-43a0-a0fd-0af5f3e4450f",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:53.552541003"
            }
          ]
        },
        {
          "id": "c05621fd-d5ed-413d-9cef-e01dc6f412a5",
          "inntektsopplysninger": [
            {
              "id": "de20c4bc-8519-47e4-b1be-9408a5d857f9",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:53.545785887"
            }
          ]
        },
        {
          "id": "8d9acba9-27f2-4050-a79f-13ae488e90fe",
          "inntektsopplysninger": [
            {
              "id": "f5c09330-e633-49ca-b0c4-32cdc61f1358",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:53.49193855"
            }
          ]
        }
      ],
      "sykdomshistorikk": [
        {
          "id": "4a94231a-ea04-4322-b207-dda54a569fc2",
          "hendelseId": "f158d38c-851d-490b-8de5-92341df6b502",
          "tidsstempel": "2021-07-23T09:01:53.399584329",
          "hendelseSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-01-31"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "f158d38c-851d-490b-8de5-92341df6b502",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              }
            ]
          },
          "beregnetSykdomstidslinje": {
            "låstePerioder": [
              {
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              },
              {
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "f158d38c-851d-490b-8de5-92341df6b502",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              },
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "4833e793-a45f-483a-9809-2ee85716ad77",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "879479c4-00f2-455e-b96e-fc03d9c09142",
          "hendelseId": "4833e793-a45f-483a-9809-2ee85716ad77",
          "tidsstempel": "2021-07-23T09:01:53.361683976",
          "hendelseSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-02-01",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "4833e793-a45f-483a-9809-2ee85716ad77",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          },
          "beregnetSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "69e98032-96b6-4d27-9744-81cd81a6cca9",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              },
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "4833e793-a45f-483a-9809-2ee85716ad77",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "7b6184eb-5a39-47c9-b770-f921f166d02d",
          "hendelseId": "70624662-88a2-4a43-b56b-a1befbb42cbd",
          "tidsstempel": "2021-07-23T09:01:53.3509153",
          "hendelseSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-02-01",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "70624662-88a2-4a43-b56b-a1befbb42cbd",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          },
          "beregnetSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "69e98032-96b6-4d27-9744-81cd81a6cca9",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              },
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "70624662-88a2-4a43-b56b-a1befbb42cbd",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "92641f25-92ae-41c8-981f-6b50701eb7fa",
          "hendelseId": "69e98032-96b6-4d27-9744-81cd81a6cca9",
          "tidsstempel": "2021-07-23T09:01:53.311224005",
          "hendelseSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-01-31"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "69e98032-96b6-4d27-9744-81cd81a6cca9",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              }
            ]
          },
          "beregnetSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-01-31"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "69e98032-96b6-4d27-9744-81cd81a6cca9",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              }
            ]
          }
        }
      ],
      "utbetalinger": [
        {
          "id": "616858c5-7a17-462d-8a44-c4da38e47676",
          "beregningId": "6f70f4dc-d578-409d-8f93-386c2f3dd24f",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          },
          "arbeidsgiverOppdrag": {
            "mottaker": "arbeidsgiver 1",
            "fagområde": "SPREF",
            "linjer": [
              {
                "fom": "2021-01-27",
                "tom": "2021-01-31",
                "satstype": "DAG",
                "sats": 1170,
                "dagsats": 1170,
                "lønn": 1431,
                "grad": 100.0,
                "stønadsdager": 3,
                "totalbeløp": 3510,
                "endringskode": "NY",
                "delytelseId": 1,
                "refDelytelseId": null,
                "refFagsystemId": null,
                "statuskode": null,
                "datoStatusFom": null,
                "klassekode": "SPREFAG-IOP"
              }
            ],
            "fagsystemId": "VI3L47ZMIFH25OX3NAJTCZLQEU",
            "endringskode": "NY",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:53.572867935",
            "nettoBeløp": 3510,
            "stønadsdager": 3,
            "fom": "2021-01-27",
            "tom": "2021-01-31"
          },
          "personOppdrag": {
            "mottaker": "12020052345",
            "fagområde": "SP",
            "linjer": [],
            "fagsystemId": "JQOAB3N6TVAY3MS3ENMWOBVNTU",
            "endringskode": "NY",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:53.57305014",
            "nettoBeløp": 0,
            "stønadsdager": 0,
            "fom": "-999999999-01-01",
            "tom": "-999999999-01-01"
          },
          "fom": "2021-01-27",
          "tom": "2021-01-31",
          "stønadsdager": 3,
          "tidsstempel": "2021-07-23T09:01:53.57306548",
          "status": "UTBETALT",
          "type": "UTBETALING",
          "maksdato": "2021-12-31",
          "forbrukteSykedager": 8,
          "gjenståendeSykedager": 240,
          "vurdering": {
            "godkjent": true,
            "ident": "Ola Nordmann",
            "epost": "ola.nordmann@nav.no",
            "tidspunkt": "2021-07-23T09:01:54.190965258",
            "automatiskBehandling": false
          },
          "overføringstidspunkt": "2021-07-23T09:01:54.248318409",
          "avstemmingsnøkkel": 123456,
          "avsluttet": "2021-07-23T09:01:54.283604626",
          "oppdatert": "2021-07-23T09:01:54.264539947"
        },
        {
          "id": "e4dcf225-7a09-446e-887c-2a9320141b4c",
          "beregningId": "5c8801ec-8d45-4aa9-af75-fbbff2afd47b",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          },
          "arbeidsgiverOppdrag": {
            "mottaker": "arbeidsgiver 1",
            "fagområde": "SPREF",
            "linjer": [
              {
                "fom": "2021-01-27",
                "tom": "2021-02-10",
                "satstype": "DAG",
                "sats": 1170,
                "dagsats": 1170,
                "lønn": 1431,
                "grad": 100.0,
                "stønadsdager": 11,
                "totalbeløp": 12870,
                "endringskode": "ENDR",
                "delytelseId": 1,
                "refDelytelseId": null,
                "refFagsystemId": null,
                "statuskode": null,
                "datoStatusFom": null,
                "klassekode": "SPREFAG-IOP"
              }
            ],
            "fagsystemId": "VI3L47ZMIFH25OX3NAJTCZLQEU",
            "endringskode": "ENDR",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:54.491601719",
            "nettoBeløp": 9360,
            "stønadsdager": 11,
            "fom": "2021-01-27",
            "tom": "2021-02-10"
          },
          "personOppdrag": {
            "mottaker": "12020052345",
            "fagområde": "SP",
            "linjer": [],
            "fagsystemId": "TDHFN7JM25ET5M24KXALT56GZY",
            "endringskode": "NY",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:54.491768856",
            "nettoBeløp": 0,
            "stønadsdager": 0,
            "fom": "-999999999-01-01",
            "tom": "-999999999-01-01"
          },
          "fom": "2021-01-27",
          "tom": "2021-02-10",
          "stønadsdager": 11,
          "tidsstempel": "2021-07-23T09:01:54.491798255",
          "status": "UTBETALT",
          "type": "UTBETALING",
          "maksdato": "2021-12-31",
          "forbrukteSykedager": 16,
          "gjenståendeSykedager": 232,
          "vurdering": {
            "godkjent": true,
            "ident": "Ola Nordmann",
            "epost": "ola.nordmann@nav.no",
            "tidspunkt": "2021-07-23T09:01:54.511757409",
            "automatiskBehandling": false
          },
          "overføringstidspunkt": "2021-07-23T09:01:54.534410969",
          "avstemmingsnøkkel": 123456,
          "avsluttet": "2021-07-23T09:01:54.55575526",
          "oppdatert": "2021-07-23T09:01:54.544939597"
        }
      ],
      "vedtaksperioder": [
        {
          "fom": "2021-01-27",
          "tom": "2021-01-31",
          "sykmeldingFom": "2021-01-27",
          "sykmeldingTom": "2021-01-31",
          "hendelseIder": [
            "69e98032-96b6-4d27-9744-81cd81a6cca9",
            "f158d38c-851d-490b-8de5-92341df6b502"
          ],
          "inntektskilde": "FLERE_ARBEIDSGIVERE",
          "id": "d9568be3-bdcc-4af3-8dae-96d7b851f889",
          "tilstand": "AVSLUTTET",
          "skjæringstidspunktFraInfotrygd": "2021-01-20",
          "inntektsmeldingInfo": null,
          "skjæringstidspunkt": "2021-01-20",
          "dataForSimulering": {
            "totalbeløp": 2000,
            "perioder": [
              {
                "fom": "2018-01-17",
                "tom": "2018-01-20",
                "utbetalinger": [
                  {
                    "forfallsdato": "2018-01-21",
                    "utbetalesTil": {
                      "id": "arbeidsgiver 1",
                      "navn": "Org Orgesen AS"
                    },
                    "feilkonto": false,
                    "detaljer": [
                      {
                        "fom": "2018-01-17",
                        "tom": "2018-01-20",
                        "konto": "81549300",
                        "beløp": 2000,
                        "klassekode": {
                          "kode": "SPREFAG-IOP",
                          "beskrivelse": "Sykepenger, Refusjon arbeidsgiver"
                        },
                        "uføregrad": 100,
                        "utbetalingstype": "YTEL",
                        "tilbakeføring": false,
                        "sats": {
                          "sats": 1000,
                          "antall": 2,
                          "type": "DAG"
                        },
                        "refunderesOrgnummer": "arbeidsgiver 1"
                      }
                    ]
                  }
                ]
              }
            ]
          },
          "utbetalinger": [
            "616858c5-7a17-462d-8a44-c4da38e47676"
          ],
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          },
          "forlengelseFraInfotrygd": "JA",
          "opprettet": "2021-07-23T09:01:53.304011123",
          "oppdatert": "2021-07-23T09:01:54.284747285",
          "sykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-01-31"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "f158d38c-851d-490b-8de5-92341df6b502",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "fom": "2021-02-01",
          "tom": "2021-02-10",
          "sykmeldingFom": "2021-02-01",
          "sykmeldingTom": "2021-02-10",
          "hendelseIder": [
            "70624662-88a2-4a43-b56b-a1befbb42cbd",
            "4833e793-a45f-483a-9809-2ee85716ad77"
          ],
          "inntektskilde": "FLERE_ARBEIDSGIVERE",
          "id": "1eba2d4e-d390-4326-aad2-927b0e17b37d",
          "tilstand": "AVSLUTTET",
          "skjæringstidspunktFraInfotrygd": "2021-01-20",
          "inntektsmeldingInfo": null,
          "skjæringstidspunkt": "2021-01-20",
          "dataForSimulering": {
            "totalbeløp": 2000,
            "perioder": [
              {
                "fom": "2018-01-17",
                "tom": "2018-01-20",
                "utbetalinger": [
                  {
                    "forfallsdato": "2018-01-21",
                    "utbetalesTil": {
                      "id": "arbeidsgiver 1",
                      "navn": "Org Orgesen AS"
                    },
                    "feilkonto": false,
                    "detaljer": [
                      {
                        "fom": "2018-01-17",
                        "tom": "2018-01-20",
                        "konto": "81549300",
                        "beløp": 2000,
                        "klassekode": {
                          "kode": "SPREFAG-IOP",
                          "beskrivelse": "Sykepenger, Refusjon arbeidsgiver"
                        },
                        "uføregrad": 100,
                        "utbetalingstype": "YTEL",
                        "tilbakeføring": false,
                        "sats": {
                          "sats": 1000,
                          "antall": 2,
                          "type": "DAG"
                        },
                        "refunderesOrgnummer": "arbeidsgiver 1"
                      }
                    ]
                  }
                ]
              }
            ]
          },
          "utbetalinger": [
            "e4dcf225-7a09-446e-887c-2a9320141b4c"
          ],
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1170.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          },
          "forlengelseFraInfotrygd": "JA",
          "opprettet": "2021-07-23T09:01:53.334518445",
          "oppdatert": "2021-07-23T09:01:54.555791546",
          "sykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-02-01",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "4833e793-a45f-483a-9809-2ee85716ad77",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          }
        }
      ],
      "forkastede": [],
      "feriepengeutbetalinger": [],
      "arbeidsforholdhistorikk": []
    },
    {
      "organisasjonsnummer": "arbeidsgiver 2",
      "id": "f0a67f63-9544-40a6-85ef-a33ea8b46a5d",
      "beregnetUtbetalingstidslinjer": [
        {
          "id": "c45d777c-27f8-418b-8556-69e8167e796c",
          "sykdomshistorikkElementId": "b8bbccae-7cdc-4351-a32a-4c8263e76175",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "db87ac50-a438-4767-942e-66a5d11f3470",
          "tidsstempel": "2021-07-23T09:01:53.517344155",
          "organisasjonsnummer": "arbeidsgiver 1",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "id": "c3a5fe07-84d4-4cf1-b78a-87975bb5f799",
          "sykdomshistorikkElementId": "2d1e383b-a36b-4b79-ae72-cdb3e7167ee4",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "2b67a961-662a-467b-bcb7-328f262f32ff",
          "tidsstempel": "2021-07-23T09:01:53.548427083",
          "organisasjonsnummer": "arbeidsgiver 2",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "id": "16af3564-6cb2-4906-b766-91b269a111a0",
          "sykdomshistorikkElementId": "2d1e383b-a36b-4b79-ae72-cdb3e7167ee4",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "43b1c580-bda6-4324-920b-b01a427c746e",
          "tidsstempel": "2021-07-23T09:01:53.556222385",
          "organisasjonsnummer": "arbeidsgiver 1",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "id": "b2490c11-b1d1-4d28-b80c-2482f2b8aead",
          "sykdomshistorikkElementId": "2d1e383b-a36b-4b79-ae72-cdb3e7167ee4",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "e29d8588-683a-4a01-a34e-ab909f5ad353",
          "tidsstempel": "2021-07-23T09:01:54.359635162",
          "organisasjonsnummer": "arbeidsgiver 1",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "ba54cc5a-cd4e-4b2d-8e50-715363c23eee",
          "sykdomshistorikkElementId": "2d1e383b-a36b-4b79-ae72-cdb3e7167ee4",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "5ab6b531-0fe3-45c2-b17d-e25bfbe99177",
          "tidsstempel": "2021-07-23T09:01:54.371162165",
          "organisasjonsnummer": "arbeidsgiver 2",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "id": "863cedc8-943e-4774-8ea1-18578c612353",
          "sykdomshistorikkElementId": "2d1e383b-a36b-4b79-ae72-cdb3e7167ee4",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "753efd9f-889e-47e2-b8ff-adf8dc4e02d4",
          "tidsstempel": "2021-07-23T09:01:54.476438689",
          "organisasjonsnummer": "arbeidsgiver 2",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "c784768a-92eb-479e-b722-2abdc10493a2",
          "sykdomshistorikkElementId": "2d1e383b-a36b-4b79-ae72-cdb3e7167ee4",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "505a78e9-1984-42f9-98bf-bd49e5ab652c",
          "tidsstempel": "2021-07-23T09:01:54.49032436",
          "organisasjonsnummer": "arbeidsgiver 1",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "abe12b89-8acc-485a-b820-d323076a1a8a",
          "sykdomshistorikkElementId": "2d1e383b-a36b-4b79-ae72-cdb3e7167ee4",
          "vilkårsgrunnlagHistorikkInnslagId": "d259df09-7031-451d-93b5-c42bfc59c627",
          "inntektshistorikkInnslagId": "b4d18389-c272-400e-af95-ee27d2075d20",
          "tidsstempel": "2021-07-23T09:01:54.591094301",
          "organisasjonsnummer": "arbeidsgiver 2",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          }
        }
      ],
      "refusjonOpphører": [],
      "inntektshistorikk": [
        {
          "id": "b4d18389-c272-400e-af95-ee27d2075d20",
          "inntektsopplysninger": [
            {
              "id": "7125a7ee-e6a8-4269-8c53-4f8a79d80cc4",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.588797691"
            }
          ]
        },
        {
          "id": "505a78e9-1984-42f9-98bf-bd49e5ab652c",
          "inntektsopplysninger": [
            {
              "id": "c56f35da-5e61-466c-9aa3-2afe491cdc8e",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.487908146"
            }
          ]
        },
        {
          "id": "753efd9f-889e-47e2-b8ff-adf8dc4e02d4",
          "inntektsopplysninger": [
            {
              "id": "4a3341cf-a86d-4b5f-8de3-f90c6152b9f1",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.473434007"
            }
          ]
        },
        {
          "id": "5ab6b531-0fe3-45c2-b17d-e25bfbe99177",
          "inntektsopplysninger": [
            {
              "id": "09bb175f-2532-4b94-af9e-928144741084",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.369356363"
            }
          ]
        },
        {
          "id": "e29d8588-683a-4a01-a34e-ab909f5ad353",
          "inntektsopplysninger": [
            {
              "id": "18c0c0b2-11d4-4c2a-bb5d-b2146b096f22",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:54.357200596"
            }
          ]
        },
        {
          "id": "43b1c580-bda6-4324-920b-b01a427c746e",
          "inntektsopplysninger": [
            {
              "id": "af4d0aff-0e21-40ca-b579-20667da9bd00",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:53.5526728"
            }
          ]
        },
        {
          "id": "2b67a961-662a-467b-bcb7-328f262f32ff",
          "inntektsopplysninger": [
            {
              "id": "cf27e0c4-ea83-4d78-85dd-6b6985e2209c",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:53.546368773"
            }
          ]
        },
        {
          "id": "db87ac50-a438-4767-942e-66a5d11f3470",
          "inntektsopplysninger": [
            {
              "id": "670233ff-b8f4-40d8-bdf9-2d5de2903b2a",
              "dato": "2021-01-20",
              "hendelseId": "83d95421-aae9-47f0-8613-96c541dd3637",
              "beløp": 31000.0,
              "kilde": "INFOTRYGD",
              "tidsstempel": "2021-07-23T09:01:53.493057897"
            }
          ]
        }
      ],
      "sykdomshistorikk": [
        {
          "id": "2d1e383b-a36b-4b79-ae72-cdb3e7167ee4",
          "hendelseId": "2c4baa6d-4e0d-4bce-8122-6e5c7f351123",
          "tidsstempel": "2021-07-23T09:01:53.51921683",
          "hendelseSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-01-31"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "2c4baa6d-4e0d-4bce-8122-6e5c7f351123",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              }
            ]
          },
          "beregnetSykdomstidslinje": {
            "låstePerioder": [
              {
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              },
              {
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "2c4baa6d-4e0d-4bce-8122-6e5c7f351123",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              },
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "6122fe6b-4ea5-481c-8430-b4854777f2d3",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "b8bbccae-7cdc-4351-a32a-4c8263e76175",
          "hendelseId": "6122fe6b-4ea5-481c-8430-b4854777f2d3",
          "tidsstempel": "2021-07-23T09:01:53.363156612",
          "hendelseSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-02-01",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "6122fe6b-4ea5-481c-8430-b4854777f2d3",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          },
          "beregnetSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "ac7fabb5-f53e-42a8-9130-77810507bdfd",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              },
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "6122fe6b-4ea5-481c-8430-b4854777f2d3",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "ca3f3944-1ebc-4672-8988-caa455acf61d",
          "hendelseId": "11e20786-9f55-42d3-ade6-ff14c4d4587b",
          "tidsstempel": "2021-07-23T09:01:53.353089217",
          "hendelseSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-02-01",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "11e20786-9f55-42d3-ade6-ff14c4d4587b",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          },
          "beregnetSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "ac7fabb5-f53e-42a8-9130-77810507bdfd",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              },
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "11e20786-9f55-42d3-ade6-ff14c4d4587b",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          }
        },
        {
          "id": "1719d2d3-4f13-445c-a3f1-737647f6e85c",
          "hendelseId": "ac7fabb5-f53e-42a8-9130-77810507bdfd",
          "tidsstempel": "2021-07-23T09:01:53.333076244",
          "hendelseSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-01-31"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "ac7fabb5-f53e-42a8-9130-77810507bdfd",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              }
            ]
          },
          "beregnetSykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-01-31"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Sykmelding",
                  "id": "ac7fabb5-f53e-42a8-9130-77810507bdfd",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              }
            ]
          }
        }
      ],
      "utbetalinger": [
        {
          "id": "b9a6537e-f266-4a1b-a24c-e2c70fdaf4fa",
          "beregningId": "16af3564-6cb2-4906-b766-91b269a111a0",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          },
          "arbeidsgiverOppdrag": {
            "mottaker": "arbeidsgiver 2",
            "fagområde": "SPREF",
            "linjer": [
              {
                "fom": "2021-01-27",
                "tom": "2021-01-31",
                "satstype": "DAG",
                "sats": 1169,
                "dagsats": 1169,
                "lønn": 1431,
                "grad": 100.0,
                "stønadsdager": 3,
                "totalbeløp": 3507,
                "endringskode": "NY",
                "delytelseId": 1,
                "refDelytelseId": null,
                "refFagsystemId": null,
                "statuskode": null,
                "datoStatusFom": null,
                "klassekode": "SPREFAG-IOP"
              }
            ],
            "fagsystemId": "J7XHWIGDZRGY5AAAFC7D63DKRI",
            "endringskode": "NY",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:53.570684277",
            "nettoBeløp": 3507,
            "stønadsdager": 3,
            "fom": "2021-01-27",
            "tom": "2021-01-31"
          },
          "personOppdrag": {
            "mottaker": "12020052345",
            "fagområde": "SP",
            "linjer": [],
            "fagsystemId": "HI6KTYTONZDKNKGNRODRJXDHFE",
            "endringskode": "NY",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:53.571441407",
            "nettoBeløp": 0,
            "stønadsdager": 0,
            "fom": "-999999999-01-01",
            "tom": "-999999999-01-01"
          },
          "fom": "2021-01-27",
          "tom": "2021-01-31",
          "stønadsdager": 3,
          "tidsstempel": "2021-07-23T09:01:53.571501321",
          "status": "FORKASTET",
          "type": "UTBETALING",
          "maksdato": "2021-12-31",
          "forbrukteSykedager": 8,
          "gjenståendeSykedager": 240,
          "vurdering": null,
          "overføringstidspunkt": null,
          "avstemmingsnøkkel": null,
          "avsluttet": null,
          "oppdatert": "2021-07-23T09:01:54.317087649"
        },
        {
          "id": "b4bc58e0-8279-4871-8e66-dc4a6bd49433",
          "beregningId": "ba54cc5a-cd4e-4b2d-8e50-715363c23eee",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          },
          "arbeidsgiverOppdrag": {
            "mottaker": "arbeidsgiver 2",
            "fagområde": "SPREF",
            "linjer": [
              {
                "fom": "2021-01-27",
                "tom": "2021-01-31",
                "satstype": "DAG",
                "sats": 1169,
                "dagsats": 1169,
                "lønn": 1431,
                "grad": 100.0,
                "stønadsdager": 3,
                "totalbeløp": 3507,
                "endringskode": "NY",
                "delytelseId": 1,
                "refDelytelseId": null,
                "refFagsystemId": null,
                "statuskode": null,
                "datoStatusFom": null,
                "klassekode": "SPREFAG-IOP"
              }
            ],
            "fagsystemId": "UEAOXSYVCBEMTBFTNDJ7J2LCSI",
            "endringskode": "NY",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:54.371668619",
            "nettoBeløp": 3507,
            "stønadsdager": 3,
            "fom": "2021-01-27",
            "tom": "2021-01-31"
          },
          "personOppdrag": {
            "mottaker": "12020052345",
            "fagområde": "SP",
            "linjer": [],
            "fagsystemId": "SY7HKGEOTRCLFBTLYUYJ7OFZRU",
            "endringskode": "NY",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:54.371798111",
            "nettoBeløp": 0,
            "stønadsdager": 0,
            "fom": "-999999999-01-01",
            "tom": "-999999999-01-01"
          },
          "fom": "2021-01-27",
          "tom": "2021-01-31",
          "stønadsdager": 3,
          "tidsstempel": "2021-07-23T09:01:54.371841799",
          "status": "UTBETALT",
          "type": "UTBETALING",
          "maksdato": "2021-12-31",
          "forbrukteSykedager": 8,
          "gjenståendeSykedager": 240,
          "vurdering": {
            "godkjent": true,
            "ident": "Ola Nordmann",
            "epost": "ola.nordmann@nav.no",
            "tidspunkt": "2021-07-23T09:01:54.387184859",
            "automatiskBehandling": false
          },
          "overføringstidspunkt": "2021-07-23T09:01:54.413199723",
          "avstemmingsnøkkel": 123456,
          "avsluttet": "2021-07-23T09:01:54.436666218",
          "oppdatert": "2021-07-23T09:01:54.423061586"
        },
        {
          "id": "4a7ec9de-00d6-409b-895c-b2a3b658b3e6",
          "beregningId": "c784768a-92eb-479e-b722-2abdc10493a2",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          },
          "arbeidsgiverOppdrag": {
            "mottaker": "arbeidsgiver 2",
            "fagområde": "SPREF",
            "linjer": [
              {
                "fom": "2021-01-27",
                "tom": "2021-02-10",
                "satstype": "DAG",
                "sats": 1169,
                "dagsats": 1169,
                "lønn": 1431,
                "grad": 100.0,
                "stønadsdager": 11,
                "totalbeløp": 12859,
                "endringskode": "ENDR",
                "delytelseId": 1,
                "refDelytelseId": null,
                "refFagsystemId": null,
                "statuskode": null,
                "datoStatusFom": null,
                "klassekode": "SPREFAG-IOP"
              }
            ],
            "fagsystemId": "UEAOXSYVCBEMTBFTNDJ7J2LCSI",
            "endringskode": "ENDR",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:54.490867044",
            "nettoBeløp": 9352,
            "stønadsdager": 11,
            "fom": "2021-01-27",
            "tom": "2021-02-10"
          },
          "personOppdrag": {
            "mottaker": "12020052345",
            "fagområde": "SP",
            "linjer": [],
            "fagsystemId": "SRK6FZGRABG6REKXAXYAYPPGD4",
            "endringskode": "NY",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:54.491217569",
            "nettoBeløp": 0,
            "stønadsdager": 0,
            "fom": "-999999999-01-01",
            "tom": "-999999999-01-01"
          },
          "fom": "2021-01-27",
          "tom": "2021-02-10",
          "stønadsdager": 11,
          "tidsstempel": "2021-07-23T09:01:54.491239691",
          "status": "FORKASTET",
          "type": "UTBETALING",
          "maksdato": "2021-12-31",
          "forbrukteSykedager": 16,
          "gjenståendeSykedager": 232,
          "vurdering": null,
          "overføringstidspunkt": null,
          "avstemmingsnøkkel": null,
          "avsluttet": null,
          "oppdatert": "2021-07-23T09:01:54.580635177"
        },
        {
          "id": "3f5f02f4-5096-4d64-914b-0de4a3d47df2",
          "beregningId": "abe12b89-8acc-485a-b820-d323076a1a8a",
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          },
          "arbeidsgiverOppdrag": {
            "mottaker": "arbeidsgiver 2",
            "fagområde": "SPREF",
            "linjer": [
              {
                "fom": "2021-01-27",
                "tom": "2021-02-10",
                "satstype": "DAG",
                "sats": 1169,
                "dagsats": 1169,
                "lønn": 1431,
                "grad": 100.0,
                "stønadsdager": 11,
                "totalbeløp": 12859,
                "endringskode": "ENDR",
                "delytelseId": 1,
                "refDelytelseId": null,
                "refFagsystemId": null,
                "statuskode": null,
                "datoStatusFom": null,
                "klassekode": "SPREFAG-IOP"
              }
            ],
            "fagsystemId": "UEAOXSYVCBEMTBFTNDJ7J2LCSI",
            "endringskode": "ENDR",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:54.59153606",
            "nettoBeløp": 9352,
            "stønadsdager": 11,
            "fom": "2021-01-27",
            "tom": "2021-02-10"
          },
          "personOppdrag": {
            "mottaker": "12020052345",
            "fagområde": "SP",
            "linjer": [],
            "fagsystemId": "YDTMVIKV7JEAPB24AY24RG2F7E",
            "endringskode": "NY",
            "sisteArbeidsgiverdag": null,
            "tidsstempel": "2021-07-23T09:01:54.591702964",
            "nettoBeløp": 0,
            "stønadsdager": 0,
            "fom": "-999999999-01-01",
            "tom": "-999999999-01-01"
          },
          "fom": "2021-01-27",
          "tom": "2021-02-10",
          "stønadsdager": 11,
          "tidsstempel": "2021-07-23T09:01:54.591732644",
          "status": "UTBETALT",
          "type": "UTBETALING",
          "maksdato": "2021-12-31",
          "forbrukteSykedager": 16,
          "gjenståendeSykedager": 232,
          "vurdering": {
            "godkjent": true,
            "ident": "Ola Nordmann",
            "epost": "ola.nordmann@nav.no",
            "tidspunkt": "2021-07-23T09:01:54.59990318",
            "automatiskBehandling": false
          },
          "overføringstidspunkt": "2021-07-23T09:01:54.6175351",
          "avstemmingsnøkkel": 123456,
          "avsluttet": "2021-07-23T09:01:54.635517973",
          "oppdatert": "2021-07-23T09:01:54.626760102"
        }
      ],
      "vedtaksperioder": [
        {
          "fom": "2021-01-27",
          "tom": "2021-01-31",
          "sykmeldingFom": "2021-01-27",
          "sykmeldingTom": "2021-01-31",
          "hendelseIder": [
            "ac7fabb5-f53e-42a8-9130-77810507bdfd",
            "2c4baa6d-4e0d-4bce-8122-6e5c7f351123"
          ],
          "inntektskilde": "FLERE_ARBEIDSGIVERE",
          "id": "09ca19d1-871f-40bc-a074-25a5887cbfc2",
          "tilstand": "AVSLUTTET",
          "skjæringstidspunktFraInfotrygd": "2021-01-20",
          "inntektsmeldingInfo": null,
          "skjæringstidspunkt": "2021-01-20",
          "dataForSimulering": {
            "totalbeløp": 2000,
            "perioder": [
              {
                "fom": "2018-01-17",
                "tom": "2018-01-20",
                "utbetalinger": [
                  {
                    "forfallsdato": "2018-01-21",
                    "utbetalesTil": {
                      "id": "arbeidsgiver 2",
                      "navn": "Org Orgesen AS"
                    },
                    "feilkonto": false,
                    "detaljer": [
                      {
                        "fom": "2018-01-17",
                        "tom": "2018-01-20",
                        "konto": "81549300",
                        "beløp": 2000,
                        "klassekode": {
                          "kode": "SPREFAG-IOP",
                          "beskrivelse": "Sykepenger, Refusjon arbeidsgiver"
                        },
                        "uføregrad": 100,
                        "utbetalingstype": "YTEL",
                        "tilbakeføring": false,
                        "sats": {
                          "sats": 1000,
                          "antall": 2,
                          "type": "DAG"
                        },
                        "refunderesOrgnummer": "arbeidsgiver 2"
                      }
                    ]
                  }
                ]
              }
            ]
          },
          "utbetalinger": [
            "b9a6537e-f266-4a1b-a24c-e2c70fdaf4fa",
            "b4bc58e0-8279-4871-8e66-dc4a6bd49433"
          ],
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-01-27",
                "tom": "2021-01-29"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-01-30",
                "tom": "2021-01-31"
              }
            ]
          },
          "forlengelseFraInfotrygd": "JA",
          "opprettet": "2021-07-23T09:01:53.332891558",
          "oppdatert": "2021-07-23T09:01:54.436714228",
          "sykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-01-27",
              "tom": "2021-01-31"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "2c4baa6d-4e0d-4bce-8122-6e5c7f351123",
                  "tidsstempel": "2021-01-27T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-01-27",
                "tom": "2021-01-31"
              }
            ]
          }
        },
        {
          "fom": "2021-02-01",
          "tom": "2021-02-10",
          "sykmeldingFom": "2021-02-01",
          "sykmeldingTom": "2021-02-10",
          "hendelseIder": [
            "11e20786-9f55-42d3-ade6-ff14c4d4587b",
            "6122fe6b-4ea5-481c-8430-b4854777f2d3"
          ],
          "inntektskilde": "FLERE_ARBEIDSGIVERE",
          "id": "f07bd5a2-de97-4b47-9d2f-eee1e656fef4",
          "tilstand": "AVSLUTTET",
          "skjæringstidspunktFraInfotrygd": "2021-01-20",
          "inntektsmeldingInfo": null,
          "skjæringstidspunkt": "2021-01-20",
          "dataForSimulering": {
            "totalbeløp": 2000,
            "perioder": [
              {
                "fom": "2018-01-17",
                "tom": "2018-01-20",
                "utbetalinger": [
                  {
                    "forfallsdato": "2018-01-21",
                    "utbetalesTil": {
                      "id": "arbeidsgiver 2",
                      "navn": "Org Orgesen AS"
                    },
                    "feilkonto": false,
                    "detaljer": [
                      {
                        "fom": "2018-01-17",
                        "tom": "2018-01-20",
                        "konto": "81549300",
                        "beløp": 2000,
                        "klassekode": {
                          "kode": "SPREFAG-IOP",
                          "beskrivelse": "Sykepenger, Refusjon arbeidsgiver"
                        },
                        "uføregrad": 100,
                        "utbetalingstype": "YTEL",
                        "tilbakeføring": false,
                        "sats": {
                          "sats": 1000,
                          "antall": 2,
                          "type": "DAG"
                        },
                        "refunderesOrgnummer": "arbeidsgiver 2"
                      }
                    ]
                  }
                ]
              }
            ]
          },
          "utbetalinger": [
            "4a7ec9de-00d6-409b-895c-b2a3b658b3e6",
            "3f5f02f4-5096-4d64-914b-0de4a3d47df2"
          ],
          "utbetalingstidslinje": {
            "dager": [
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-01",
                "tom": "2021-02-05"
              },
              {
                "type": "NavHelgDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 0.0,
                "aktuellDagsinntekt": 0.0,
                "arbeidsgiverbeløp": 0.0,
                "personbeløp": 0.0,
                "er6GBegrenset": false,
                "fom": "2021-02-06",
                "tom": "2021-02-07"
              },
              {
                "type": "NavDag",
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "skjæringstidspunkt": "2021-01-20",
                "totalGrad": 100.0,
                "dekningsgrunnlag": 1430.7692307692307,
                "aktuellDagsinntekt": 1430.7692307692307,
                "arbeidsgiverbeløp": 1169.0,
                "personbeløp": 0.0,
                "er6GBegrenset": true,
                "fom": "2021-02-08",
                "tom": "2021-02-10"
              }
            ]
          },
          "forlengelseFraInfotrygd": "JA",
          "opprettet": "2021-07-23T09:01:53.352912335",
          "oppdatert": "2021-07-23T09:01:54.635552842",
          "sykdomstidslinje": {
            "låstePerioder": [],
            "periode": {
              "fom": "2021-02-01",
              "tom": "2021-02-10"
            },
            "dager": [
              {
                "type": "SYKEDAG",
                "kilde": {
                  "type": "Søknad",
                  "id": "6122fe6b-4ea5-481c-8430-b4854777f2d3",
                  "tidsstempel": "2021-02-01T00:00:00"
                },
                "grad": 100.0,
                "arbeidsgiverBetalingProsent": 100.0,
                "fom": "2021-02-01",
                "tom": "2021-02-10"
              }
            ]
          }
        }
      ],
      "forkastede": [],
      "feriepengeutbetalinger": [],
      "arbeidsforholdhistorikk": []
    }
  ],
  "infotrygdhistorikk": [
    {
      "id": "83d95421-aae9-47f0-8613-96c541dd3637",
      "tidsstempel": "2021-07-23T09:01:53.414511942",
      "hendelseId": "1b1b8bec-1462-4f53-b897-a5abbc144980",
      "ferieperioder": [],
      "arbeidsgiverutbetalingsperioder": [
        {
          "orgnr": "arbeidsgiver 1",
          "fom": "2021-01-20",
          "tom": "2021-01-26",
          "grad": 100,
          "inntekt": 31005.0
        },
        {
          "orgnr": "arbeidsgiver 2",
          "fom": "2021-01-20",
          "tom": "2021-01-26",
          "grad": 100,
          "inntekt": 31005.0
        }
      ],
      "personutbetalingsperioder": [],
      "ukjenteperioder": [],
      "inntekter": [
        {
          "orgnr": "arbeidsgiver 1",
          "sykepengerFom": "2021-01-20",
          "inntekt": 31000.0,
          "refusjonTilArbeidsgiver": true,
          "refusjonTom": null,
          "lagret": "2021-07-23T09:01:54.588653601"
        },
        {
          "orgnr": "arbeidsgiver 2",
          "sykepengerFom": "2021-01-20",
          "inntekt": 31000.0,
          "refusjonTilArbeidsgiver": true,
          "refusjonTom": null,
          "lagret": "2021-07-23T09:01:54.588810285"
        }
      ],
      "arbeidskategorikoder": {},
      "ugyldigePerioder": [],
      "harStatslønn": false,
      "lagretInntekter": true,
      "lagretVilkårsgrunnlag": true,
      "oppdatert": "2021-07-23T09:01:53.40331777"
    }
  ],
  "vilkårsgrunnlagHistorikk": [
    {
      "id": "d259df09-7031-451d-93b5-c42bfc59c627",
      "opprettet": "2021-07-23T09:01:53.494547591",
      "vilkårsgrunnlag": [
        {
          "skjæringstidspunkt": "2021-01-20",
          "type": "Infotrygd"
        }
      ]
    }
  ],
  "skjemaVersjon": 111
}

    """
}
