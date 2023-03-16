import React from 'react'
import { ContentCategory } from './ContentCategory'
import { ContentView } from '../../state/state'
import { useForkastetVedtaksperiode, usePerson, useVedtak } from '../../state/contexts'

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
const Vedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    return _ingressView(vedtaksperiode.id)
})
Vedtaksperiode.displayName = 'IngressView.Vedtaksperiode'

const ForkastetVedtaksperiode = React.memo(() => {
    const vedtaksperiode = useForkastetVedtaksperiode()
    return _ingressView(vedtaksperiode.id)
})
ForkastetVedtaksperiode.displayName = 'IngressView.ForkastetVedtaksperiode'

const Person = React.memo(() => {
    const p = usePerson()
    return (
        <div>
            <a href={`${sporing}/person/${p.aktørId}`} target="_blank">
                Sporing
            </a>
        </div>
    )
})
Person.displayName = 'IngressView.Person'

export const IngressView = React.memo(() => {
    return (
        <ContentCategory displayName={ContentView.Ingress} {...{ Person, Vedtaksperiode, ForkastetVedtaksperiode }} />
    )
})

IngressView.displayName = 'IngressView'
