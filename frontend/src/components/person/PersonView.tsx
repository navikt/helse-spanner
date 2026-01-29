import React, {useEffect, useState} from 'react'
import {PersonHeader} from './PersonHeader'
import {PersonTree} from '../tree/PersonTree'
import {Content} from '../content/Content'
import {useSetAtom} from 'jotai'
import {åpneHendelseDokumentState} from '../../state/state'
import {usePerson} from '../../state/contexts'
import {Box, HGrid, Page} from "@navikt/ds-react";
import {Tidslinjer} from "./Tidslinjer";

export const PersonView = () => {
    const setÅpneHendelser = useSetAtom(åpneHendelseDokumentState)
    const resetÅpneHendelser = () => setÅpneHendelser([])
    const person = usePerson()
    useEffect(() => {
        resetÅpneHendelser()
    }, [person])


    const [valgteTing, setValgteTing] = useState([person.aktørId])

    const skalUtvideValgtTing = (e: React.MouseEvent | React.KeyboardEvent | boolean) => {
        return (typeof e === 'boolean') ? e : (e.ctrlKey || e.metaKey)
    }
    const toggleValgtTing = (e: React.MouseEvent | React.KeyboardEvent | boolean, id: string) => {
        setValgteTing((previous) => {
            // fjern fra settet hvis iden er der fra før
            if (previous.includes(id)) return previous.filter((it) => it != id)
            // legg til iden i settet hvis ctrl/meta-key holdes inne
            if (skalUtvideValgtTing(e)) return [...previous.filter((it) => it != id), id]
            return [id]
        })
    }

    return (
        <>
            <Box background={'accent-moderate'} paddingBlock="space-16" paddingInline="space-32">
                <Page.Block>
                    <PersonHeader toggleValgtTing={toggleValgtTing} />
                    <Tidslinjer valgteTing={valgteTing} toggleValgtTing={toggleValgtTing} />
                </Page.Block>
            </Box>
            <Box background={'neutral-moderate'} paddingBlock="space-16" paddingInline="space-32" as="main">
                <Page.Block>
                    <HGrid gap="space-24" columns="300px auto">
                        <PersonTree valgteTing={valgteTing} toggleValgtTing={toggleValgtTing} />
                        <Content person={person} valgteTing={valgteTing} />
                    </HGrid>
                </Page.Block>
            </Box>
        </>
    )
}

