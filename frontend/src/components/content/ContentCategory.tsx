import React, {ReactNode} from 'react'
import {ContentView} from '../../state/state'
import {Card} from "../Card";
import {
    ArbeidsgiverDto,
    BehandlingDto,
    EndringDto,
    FokastetVedtaksperiodeDto,
    PersonDto,
    UtbetalingDto,
    VedtakDto, VilkårsgrunnlagDto
} from "../../state/dto";
import parseISO from "date-fns/parseISO";

type ContentCategoryProperties = {
    displayName: ContentView
    person: PersonDto,
    Person?: React.FC<{ person: PersonDto }>
    Arbeidsgiver?: React.FC<{ arbeidsgiver: ArbeidsgiverDto }>
    Vilkårsgrunnlag?: React.FC<{ vilkårsgrunnlag: VilkårsgrunnlagDto }>
    Vedtaksperiode?: React.FC<{ vedtaksperiode: VedtakDto }>
    ForkastetVedtaksperiode?: React.FC<{ vedtaksperiode: FokastetVedtaksperiodeDto }>
    Utbetaling?: React.FC<{ utbetaling: UtbetalingDto }>,
    Behandling?: React.FC<{ behandling: BehandlingDto }>,
    Endring?: React.FC<{ endring: EndringDto }>,
    valgteTing: string[]
}

export function ContentCategory({
    person,
    Person = undefined,
    Arbeidsgiver = undefined,
    Vilkårsgrunnlag = undefined,
    Vedtaksperiode = undefined,
    ForkastetVedtaksperiode = undefined,
    Utbetaling = undefined,
    Behandling = undefined,
    Endring = undefined,
    valgteTing
} : ContentCategoryProperties) {
    let tingene = HentVisning(person, valgteTing)
    return <>
        {Person && tingene.person && <Ramme key={tingene.person.aktørId} valgteTing={valgteTing} ting={tingene.person.aktørId}><Person person={tingene.person} /></Ramme>}
        {Arbeidsgiver && tingene.arbeidsgivere.map((it) =>
            <Ramme key={it.id} valgteTing={valgteTing} ting={it.id}><Arbeidsgiver arbeidsgiver={it} /></Ramme>
        )}
        {Vilkårsgrunnlag && tingene.vilkårsgrunnlag.map((it) =>
            <Ramme key={it.id} valgteTing={valgteTing} ting={it.id}><Vilkårsgrunnlag vilkårsgrunnlag={it} /></Ramme>
        )}
        {Vedtaksperiode && tingene.vedtaksperioder.map((it) =>
            <Ramme key={it.id}  valgteTing={valgteTing} ting={it.id}><Vedtaksperiode vedtaksperiode={it} /></Ramme>
        )}
        {valgteTing.length === 2 && tingene.vedtaksperioder.length===2 && <DagerMellom vedtaksperioder={tingene.vedtaksperioder}></DagerMellom>}
        {Behandling && tingene.behandlinger.map((it) =>
            <Ramme key={it.id}  valgteTing={valgteTing} ting={it.id}><Behandling behandling={it} /></Ramme>
        )}
        {Endring && tingene.endringer.map((it) =>
            <Ramme key={it.id}  valgteTing={valgteTing} ting={it.id}><Endring endring={it} /></Ramme>
        )}
        {ForkastetVedtaksperiode && tingene.forkastedeVedtaksperioder.map((it) =>
            <Ramme key={it.id}  valgteTing={valgteTing} ting={it.id}><ForkastetVedtaksperiode vedtaksperiode={it} /></Ramme>
        )}
        {Utbetaling && tingene.utbetalinger.map((it) =>
            <Ramme key={it.id}  valgteTing={valgteTing} ting={it.id}><Utbetaling utbetaling={it} /></Ramme>
        )}
        {Utbetaling && tingene.grupperteUtbetalinger.map((it) =>
            <Ramme key={it.id}  valgteTing={valgteTing} ting={it.korrelasjonsId}><Utbetaling utbetaling={it} /></Ramme>
        )}
    </>
}

ContentCategory.displayName = 'ContentCategory'

function HentVisning(person: PersonDto, valgteTing: string[]) {
    return {
        person: valgteTing.includes(person.aktørId) ? person : undefined,
        arbeidsgivere: person.arbeidsgivere.filter((it) => valgteTing.includes(it.id)),
        vilkårsgrunnlag: person.vilkårsgrunnlagHistorikk.length >= 1 ?
            person.vilkårsgrunnlagHistorikk.flatMap((it) => it.vilkårsgrunnlag.filter((vilkårsgrunnlag) => valgteTing.includes(vilkårsgrunnlag.id)))
            : [],
        vedtaksperioder: person.arbeidsgivere.flatMap((it) =>
            it.vedtaksperioder.filter((vedtaksperiode) => valgteTing.includes(vedtaksperiode.id))
        ),
        behandlinger: person.arbeidsgivere.flatMap((it) =>
            it.vedtaksperioder.flatMap((v) => v.behandlinger)
                .filter((behandling) => valgteTing.includes(behandling.id))
        ),
        endringer: person.arbeidsgivere.flatMap((it) =>
            it.vedtaksperioder.flatMap((v) => v.behandlinger)
                .flatMap((b) => b.endringer)
                .filter((endring) => valgteTing.includes(endring.id))
        ),
        forkastedeVedtaksperioder: person.arbeidsgivere
            .flatMap((it) =>
                it.forkastede.filter((forkastet) => valgteTing.includes(forkastet.vedtaksperiode.id))
            )
            .map((forkastede) => forkastede.vedtaksperiode),
        utbetalinger: person.arbeidsgivere.flatMap((it) =>
            it.utbetalinger.filter((utbetaling) => valgteTing.includes(utbetaling.id))
        ),
        grupperteUtbetalinger: person.arbeidsgivere.flatMap((it) =>
            it.utbetalinger.filter((utbetaling) => valgteTing.includes(utbetaling.korrelasjonsId))
        )
    }
}

export function fargeForTing(valgteTing: string[], ting: string) {
    const index = valgteTing.findIndex((it) => it === ting)
    if (index == -1) return undefined
    const selectColors = ['LightBlue', 'DarkGray', 'DarkOrange', 'Turquoise', 'DarkSeaGreen', 'Orchid', 'Gold', 'LawnGreen']
    return selectColors[index % selectColors.length]
}

export function Ramme({ valgteTing, ting, children }: { valgteTing: string[], ting: string, children: ReactNode }) {
    const color = fargeForTing(valgteTing, ting)
    if (!color) return null
    return<Card style={{ borderStyle: valgteTing.length > 1 ? `solid` : 'none', borderWidth: '7px', borderColor: color }}>
        {children}
    </Card>
}

function DagerMellom({vedtaksperioder}: {vedtaksperioder: VedtakDto[]}) {
    const sortedVedtaksperioder = vedtaksperioder.sort((v1, v2) => {
        if (parseISO(v1.fom) > parseISO(v2.fom)) return 1
        else return -1
    })
    const førsteTom = parseISO(sortedVedtaksperioder[0].tom)
    const sisteFom = parseISO(sortedVedtaksperioder[1].fom)

    /**
     * Take the difference between the dates and divide by milliseconds per day.
     * Round to nearest whole number to deal with DST.
     */
    const dagerMellom = Math.round((sisteFom.valueOf() - førsteTom.valueOf()) / (1000 * 60 * 60 * 24));
    return <h3>{`Det er ${dagerMellom} dager mellom disse to vedtaksperiodene`}</h3>
}
