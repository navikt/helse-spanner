import React from "react";
import {ContentCategory} from "./ContentCategory";
import {ContentView} from "../../state/state";
import {Hendelser} from "./hendelser/Hendelser";
import {useVedtak} from "../../state/contexts";

const Vedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    const erDev = window.location.origin.includes("dev")
    return (
        <div>
            {erDev
                ? <a href={`https://sporing.dev.intern.nav.no/tilstandsmaskin/${vedtaksperiode.id}`} target="_blank">Sporing (dev)</a>
                : <a href={`https://sporing.intern.nav.no/tilstandsmaskin/${vedtaksperiode.id}`} target="_blank">Sporing (prod)</a>}
        </div>
    )
})
Vedtaksperiode.displayName = 'IngressView.Vedtaksperiode'

export const IngressView = React.memo(() => {
    return (
        <ContentCategory
            displayName={ContentView.Ingress}
            {...{Vedtaksperiode}}
        />

    )
})

Hendelser.displayName = 'IngressView'
