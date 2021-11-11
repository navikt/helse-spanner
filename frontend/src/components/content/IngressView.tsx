import React from "react";
import {ContentCategory} from "./ContentCategory";
import {ContentView} from "../../state/state";
import {Hendelser} from "./hendelser/Hendelser";
import {useVedtak} from "../../state/contexts";

const Vedtaksperiode = React.memo(() => {
    const vedtaksperiode = useVedtak()
    const sporing = window.location.origin.includes("dev") ? 'https://sporing.dev.intern.nav.no' : 'https://sporing.intern.nav.no'
    return (
        <div>
            <a href={`${sporing}/tilstandsmaskin/${vedtaksperiode.id}`} target="_blank">Sporing</a>
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
