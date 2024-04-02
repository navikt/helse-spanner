import React from 'react'
import { ContentCategory } from './ContentCategory'
import { ContentView } from '../../state/state'
import { PersonDto } from '../../state/dto'

const _IngressView = () => <div>Vil du til Sporing? Trykk på 🔎-ikonet til venstre!</div>
_IngressView.displayName = 'IngressView'

export const IngressView = ({ person, valgteTing }: { person: PersonDto, valgteTing: string[] }) => {
    return (
        <ContentCategory
            displayName={ContentView.Ingress}
            valgteTing={valgteTing}
            person={person}
            Person={_IngressView}
            Vedtaksperiode={_IngressView}
            ForkastetVedtaksperiode={_IngressView}
        />
    )
}

IngressView.displayName = 'IngressView'
