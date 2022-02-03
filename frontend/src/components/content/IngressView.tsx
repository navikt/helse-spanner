import React from "react";
import {ContentCategory} from "./ContentCategory";
import {ContentView} from "../../state/state";
import {Hendelser} from "./hendelser/Hendelser";
import {useForkastetVedtaksperiode, useVedtak} from "../../state/contexts"

const _ingressView = (vedtaksperiodeId: string) => {
    const sporing = window.location.origin.includes("dev") ? 'https://sporing.dev.intern.nav.no' : 'https://sporing.intern.nav.no'
    return (
        <div>
            <a href={`${sporing}/tilstandsmaskin/${vedtaksperiodeId}`} target="_blank">Sporing</a>
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

export const IngressView = React.memo(() => {
    return (
        <ContentCategory
            displayName={ContentView.Ingress}
            {...{Vedtaksperiode, ForkastetVedtaksperiode}}
        />

    )
})

Hendelser.displayName = 'IngressView'
