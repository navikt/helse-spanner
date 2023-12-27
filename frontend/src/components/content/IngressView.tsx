import React from 'react'
import {ContentCategory} from './ContentCategory'
import {ContentView} from '../../state/state'
import {FokastetVedtaksperiodeDto, PersonDto, VedtakDto} from "../../state/dto";

const sporing = window.location.origin.includes('dev')
    ? 'https://sporing.intern.dev.nav.no'
    : 'https://sporing.intern.nav.no'
const _ingressView = (vedtaksperiodeId: string) => {
    return (
        <div>
            <a href={`${sporing}/tilstandsmaskin/${vedtaksperiodeId}`} target="_blank">
                Sporing
            </a>
        </div>
    )
}
const Vedtaksperiode = ({ vedtaksperiode }: { vedtaksperiode: VedtakDto }) => {
    return _ingressView(vedtaksperiode.id)
}
Vedtaksperiode.displayName = 'IngressView.Vedtaksperiode'

const ForkastetVedtaksperiode = ({ vedtaksperiode }: { vedtaksperiode: FokastetVedtaksperiodeDto }) => {
    return _ingressView(vedtaksperiode.id)
}
ForkastetVedtaksperiode.displayName = 'IngressView.ForkastetVedtaksperiode'

const Person = ({ person }: { person: PersonDto }) => {
    return (
        <div>
            <a href={`${sporing}/person/${person.aktørId}`} target="_blank">
                Sporing
            </a>
        </div>
    )
}
Person.displayName = 'IngressView.Person'

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
