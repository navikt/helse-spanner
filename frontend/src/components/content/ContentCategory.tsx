import React, {ReactNode} from 'react'
import {ContentView} from '../../state/state'
import {Card} from "../Card";
import {
    ArbeidsgiverDto,
    BehandlingDto, EndringDto,
    FokastetVedtaksperiodeDto,
    PersonDto,
    UtbetalingDto,
    VedtakDto
} from "../../state/dto";

type ContentCategoryProperties = {
    displayName: ContentView
    person: PersonDto,
    Person?: React.FC<{ person: PersonDto }>
    Arbeidsgiver?: React.FC<{ arbeidsgiver: ArbeidsgiverDto }>
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
        {Vedtaksperiode && tingene.vedtaksperioder.map((it) =>
            <Ramme key={it.id}  valgteTing={valgteTing} ting={it.id}><Vedtaksperiode vedtaksperiode={it} /></Ramme>
        )}
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
    return <Card style={{ borderStyle: valgteTing.length > 1 ? `solid` : 'none', borderWidth: '7px', borderColor: color }}>
        {children}
    </Card>
}
