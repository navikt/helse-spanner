import React from 'react'
import {JsonView} from './JsonView'
import {HendelseView} from './hendelser/HendelseView'
import {ContentView} from '../../state/state'
import {HendelseDokumentView} from './hendelseDokument/HendelseDokumentView'
import {IngressView} from './IngressView'
import {Box, Tabs} from "@navikt/ds-react";
import {PersonDto} from "../../state/dto";

export const Content = ({ person, valgteTing }: { person: PersonDto, valgteTing: string[] }) => {
    return (
        <Box>
            <Tabs defaultValue={ ContentView.Json }>
                <Tabs.List>
                    <ViewButton value={ ContentView.Json } />
                    <ViewButton value={ ContentView.Hendelser } />
                    <ViewButton value={ ContentView.Ingress } />
                </Tabs.List>
                <Tabs.Panel value={ ContentView.Json }>
                    <JsonView person={person} valgteTing={valgteTing} />
                </Tabs.Panel>
                <Tabs.Panel value={ ContentView.Hendelser }>
                    <HendelseView person={person} valgteTing={valgteTing} />
                </Tabs.Panel>
                <Tabs.Panel value={ ContentView.Ingress }>
                    <IngressView person={person} valgteTing={valgteTing} />
                </Tabs.Panel>
            </Tabs>
            <HendelseDokumentView />
        </Box>
    )
}

function ViewButton({ value }: { value: ContentView }) {
    return <Tabs.Tab value={value} label={value} />
}
