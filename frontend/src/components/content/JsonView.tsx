import React, {useMemo} from 'react'
import ReactJson from '@microlink/react-json-view'
import {ContentView} from '../../state/state'
import {ContentCategory} from './ContentCategory'
import {writeToClipboard} from '../../utils'
import {
    ArbeidsgiverDto,
    BehandlingDto, EndringDto,
    FokastetVedtaksperiodeDto,
    PersonDto,
    UtbetalingDto,
    VedtakDto, VilkårsgrunnlagDto
} from "../../state/dto";

const Arbeidsgiver = ({ arbeidsgiver }: { arbeidsgiver: ArbeidsgiverDto }) => {
    return (
        <div>
            <ReactJsonMedBedreKopiering src={arbeidsgiver} />
        </div>
    )
}
Arbeidsgiver.displayName = 'JsonView.Arbeidsgiver'

const Person = ({ person }: { person: PersonDto }) => {
    return (
        <div>
            <ReactJsonMedBedreKopiering src={person} />
        </div>
    )
}
Person.displayName = 'JsonView.Person'

const Vilkårsgrunnlag = ({ vilkårsgrunnlag }: { vilkårsgrunnlag: VilkårsgrunnlagDto }) => {
    return (
        <div>
            <ReactJsonMedBedreKopiering src={vilkårsgrunnlag} />
        </div>
    )
}
Person.displayName = 'JsonView.Vilkårsgrunnlag'

const Vedtaksperiode = ({ vedtaksperiode }: { vedtaksperiode: VedtakDto }) => {
    return (
        <div>
            <ReactJsonMedBedreKopiering src={vedtaksperiode} />
        </div>
    )
}

const ForkastetVedtaksperiode = ({ vedtaksperiode }: { vedtaksperiode: FokastetVedtaksperiodeDto }) => {
    return (
        <div>
            <ReactJsonMedBedreKopiering src={vedtaksperiode} />
        </div>
    )
}
ForkastetVedtaksperiode.displayName = 'JsonView.ForkastetVedtaksperiode'

const Utbetaling = ({ utbetaling }: { utbetaling: UtbetalingDto }) => {
    return (
        <div>
            <ReactJsonMedBedreKopiering src={utbetaling} />
        </div>
    )
}
Utbetaling.displayName = 'JsonView.Utbetaling'

const Behandling = ({ behandling }: { behandling: BehandlingDto }) => {
    return (
        <div>
            <ReactJsonMedBedreKopiering src={behandling} />
        </div>
    )
}
Behandling.displayName = 'JsonView.Behandling'


const Endring = ({ endring }: { endring: EndringDto }) => {
    return (
        <div>
            <ReactJsonMedBedreKopiering src={endring} />
        </div>
    )
}
Endring.displayName = 'JsonView.Endring'

// Vi kan velge mellom to implementasjoner:
// Stripper vekk anførselstegn fra innholder den kopierer ut fra JSON-en
// eller
// Kopierer hele json-strukturen
// begge er nyttige, og det er vanskelig å vite hvem som er best
// idéelt lager vi en funksjon "kopierLurtOgSmart" og som ser på `data.scr`
// og kjører JSON.stringify(src.data) om det er en json-struktur eller String(data.src) om det er et verdi-felt.
//
// Jeg tar en råsjans og bruker JSON.stringify nå, siden det er sykt mye vanskeligere å få ut en fornuftig json enn å fjerne fnutter.
const ReactJsonMedBedreKopiering = (props: { src: object }) => (
    <ReactJson
        src={props.src}
        name={null}
        collapsed={1}
        enableClipboard={(data) => writeToClipboard(JSON.stringify(data.src))}
        sortKeys={true}
    />
)

export const JsonView = ({ person, valgteTing }: { person: PersonDto, valgteTing: string[] }) => {
    return useMemo(() => {
        return <ContentCategory
            displayName={ContentView.Json}
            person={person}
            valgteTing={valgteTing}
            {...{ Person, Vilkårsgrunnlag, Arbeidsgiver, Vedtaksperiode, ForkastetVedtaksperiode, Utbetaling, Behandling, Endring }}
        />
    }, [valgteTing, person])
}
