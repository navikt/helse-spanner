import React, {useEffect, useState} from 'react'
import {PersonHeader} from './PersonHeader'
import {PersonTree} from '../tree/PersonTree'
import {Content} from '../content/Content'
import {useResetRecoilState} from 'recoil'
import {åpneHendelseDokumentState} from '../../state/state'
import {usePerson} from '../../state/contexts'
import {Box, HGrid, Page} from "@navikt/ds-react";

export const PersonView = () => {
    const resetÅpneHendelser = useResetRecoilState(åpneHendelseDokumentState)
    const person = usePerson()
    useEffect(() => {
        resetÅpneHendelser()
    }, [person])


    const [valgteTing, setValgteTing] = useState([person.aktørId])

    const toggleValgtTing = (e: React.MouseEvent, id: string) => {
        setValgteTing((previous) => {
            // fjern fra settet hvis iden er der fra før
            if (previous.includes(id)) return previous.filter((it) => it != id)
            // legg til iden i settet hvis ctrl/meta-key holdes inne
            if (e.ctrlKey || e.metaKey) return [...previous.filter((it) => it != id), id]
            return [id]
        })
    }

    return (<>
        <Box background="surface-alt-3-moderate" paddingBlock="5" paddingInline="8" as="header">
            <Page.Block>
                <PersonHeader />
            </Page.Block>
        </Box>
        <Box
            background="bg-subtle"
            paddingBlock="10"
            paddingInline="8"
            as="main"
        >
            <Page.Block>
                <HGrid gap="6" columns="300px auto">
                    <PersonTree
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                    />
                    <Content person={person} valgteTing={valgteTing} />
                </HGrid>
            </Page.Block>
        </Box>
    </>)
}
PersonView.displayName = 'PersonView'
