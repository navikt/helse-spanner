import React, {PropsWithChildren} from 'react'
import {JsonView} from './JsonView'
import {HendelseView} from './hendelser/HendelseView'
import {ContentView} from '../../state/state'
import {useIsOnlySelected, useIsSelected} from '../../state/contexts'
import {Card} from '../Card'
import {HendelseDokumentView} from './hendelseDokument/HendelseDokumentView'
import {IngressView} from './IngressView'
import {Box, Tabs} from "@navikt/ds-react";

export const Content = () => {
    return (
        <Box>
            <Tabs defaultValue={ ContentView.Json }>
                <Tabs.List>
                    <ViewButton value={ ContentView.Json } />
                    <ViewButton value={ ContentView.Hendelser } />
                    <ViewButton value={ ContentView.Ingress } />
                </Tabs.List>
                <Tabs.Panel value={ ContentView.Json }>
                    <JsonView />
                </Tabs.Panel>
                <Tabs.Panel value={ ContentView.Hendelser }>
                    <HendelseView />
                </Tabs.Panel>
                <Tabs.Panel value={ ContentView.Ingress }>
                    <IngressView />
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

export const ShowIfSelected: React.FC<PropsWithChildren<any>> = ({ children }) => {
    const selectedColor = useIsSelected()
    const onlySelected = useIsOnlySelected()
    if (!selectedColor) return null
    return (
        <Card style={{ borderStyle: onlySelected ? `solid` : 'none', borderWidth: '7px', borderColor: selectedColor }}>
            {children}
        </Card>
    )
}
ShowIfSelected.displayName = 'ShowIfSelected'
