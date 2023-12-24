import React, {useEffect} from 'react'
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
                    <PersonTree />
                    <Content />
                </HGrid>
            </Page.Block>
        </Box>
    </>)
}
PersonView.displayName = 'PersonView'
