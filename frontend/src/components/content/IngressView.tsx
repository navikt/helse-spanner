import React from 'react'
import { ContentCategory } from './ContentCategory'
import { ContentView } from '../../state/state'
import {FokastetVedtaksperiodeDto, PersonDto, VedtakDto} from '../../state/dto'
import { personSporingUrl, tilstandsmaskinSporingUrl } from '../tree/links'

const _IngressView = (url: string) => <div><a href={url} target="_blank" rel="noreferrer">Sporing</a> 🔎</div>

const Vedtaksperiode = ({ vedtaksperiode }: { vedtaksperiode: VedtakDto }) =>
    _IngressView(tilstandsmaskinSporingUrl(vedtaksperiode.id))
Vedtaksperiode.displayName = 'IngressView.Vedtaksperiode'

const ForkastetVedtaksperiode = ({ vedtaksperiode }: { vedtaksperiode: FokastetVedtaksperiodeDto }) =>
    _IngressView(tilstandsmaskinSporingUrl(vedtaksperiode.id))
ForkastetVedtaksperiode.displayName = 'IngressView.Vedtaksperiode'

const Person = ({ person }: { person: PersonDto }) => _IngressView(personSporingUrl(person.aktørId))
Person.displayName = 'IngressView.Vedtaksperiode'

export const IngressView = ({ person, valgteTing }: { person: PersonDto, valgteTing: string[] }) => {
    return (
        <ContentCategory
            displayName={ContentView.Ingress}
            valgteTing={valgteTing}
            person={person}
            Person={Person}
            Vedtaksperiode={Vedtaksperiode}
            ForkastetVedtaksperiode={ForkastetVedtaksperiode}
        />
    )
}

IngressView.displayName = 'IngressView'
