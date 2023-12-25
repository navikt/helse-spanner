import React from 'react'
import {JsonView} from './JsonView'
import {HendelseView} from './hendelser/HendelseView'
import {ContentView} from '../../state/state'
import {HendelseDokumentView} from './hendelseDokument/HendelseDokumentView'
import {IngressView} from './IngressView'
import {Box, Tabs} from "@navikt/ds-react";

export const Content = ({ valgteTing }: { valgteTing: string[] }) => {
    return (
        <Box>
            <Tabs defaultValue={ ContentView.Json }>
                <Tabs.List>
                    <ViewButton value={ ContentView.Json } />
                    <ViewButton value={ ContentView.Hendelser } />
                    <ViewButton value={ ContentView.Ingress } />
                </Tabs.List>
                <Tabs.Panel value={ ContentView.Json }>
                    <JsonView valgteTing={valgteTing} />
                </Tabs.Panel>
                <Tabs.Panel value={ ContentView.Hendelser }>
                    <HendelseView valgteTing={valgteTing} />
                </Tabs.Panel>
                <Tabs.Panel value={ ContentView.Ingress }>
                    <IngressView valgteTing={valgteTing} />
                </Tabs.Panel>
            </Tabs>
            <HendelseDokumentView />
        </Box>
    )
}
Content.displayName = 'Content'

function ViewButton({ value }: { value: ContentView }) {
    return <Tabs.Tab value={value} label={value} />
}
