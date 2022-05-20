import React from 'react'
import { useArbeidsgiver, useForkastetVedtaksperiode, usePerson, useUtbetaling, useVedtak } from '../../state/contexts'
import ReactJson from 'react-json-view'
import { ContentView } from '../../state/state'
import { ContentCategory } from './ContentCategory'
import { writeToClipboard } from '../../utils'

const Arbeidsgiver = React.memo(() => {
    const arbeidsgiver = useArbeidsgiver()
    return (
        <div>
            <ReactJsonMedBedreKopiering src={arbeidsgiver} />
        </div>
    )
})
Arbeidsgiver.displayName = 'JsonView.Arbeidsgiver'

const Person = React.memo(() => {
    const person = usePerson()
    return (
        <div>
            <ReactJsonMedBedreKopiering src={person} />
        </div>
    )
})
Person.displayName = 'JsonView.Person'

const Vedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    return (
        <div>
            <ReactJsonMedBedreKopiering src={vedtaksperiode} />
        </div>
    )
})
Vedtaksperiode.displayName = 'JsonView.Vedtaksperiode'

const ForkastetVedtaksperiode = React.memo(() => {
    const vedtaksperiode = useForkastetVedtaksperiode()
    return (
        <div>
            <ReactJsonMedBedreKopiering src={vedtaksperiode} />
        </div>
    )
})
Vedtaksperiode.displayName = 'JsonView.Vedtaksperiode'

const Utbetaling = React.memo(() => {
    const utbetaling = useUtbetaling()
    return (
        <div>
            <ReactJsonMedBedreKopiering src={utbetaling} />
        </div>
    )
})
Vedtaksperiode.displayName = 'JsonView.Utbetaling'

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
    />
)

export const JsonView = React.memo(() => {
    return (
        <ContentCategory
            displayName={ContentView.Json}
            {...{ Person, Arbeidsgiver, Vedtaksperiode, ForkastetVedtaksperiode, Utbetaling }}
        />
    )
})
JsonView.displayName = 'JsonView'
