import styles from './PersonTree.module.css'
import React from 'react'
import {usePerson,} from '../../state/contexts'
import {useRecoilState, useSetRecoilState} from 'recoil'
import {expandedArbeidsgivereState, hideForkastedeVedtakState} from '../../state/state'
import {personSporingUrl} from './links'
import {Box, HStack, Switch} from "@navikt/ds-react";
import SporingLenke from "./SporingLenke";
import KopierPersonPåminnelseJson from "./KopierPersonPåminnelseJson";
import SelectableTreeNode from "./SelectableTreeNode";
import {ArbeidsgiverNode} from "./ArbeidsgiverNode";


interface PersonTreeProps {
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
}

export const PersonTree = ({valgteTing, toggleValgtTing } : PersonTreeProps) => {
    const person = usePerson()
    const setExpandedArbeidsgivere = useSetRecoilState(expandedArbeidsgivereState)
    const [hideForkastedeVedtak, setHideForkastedeVedtak] = useRecoilState(hideForkastedeVedtakState)
    const toggleArbeidsgivere = (skjule: boolean) => {
        setExpandedArbeidsgivere((tidligere) => {
            if (tidligere.length > 0) return []
            return person.arbeidsgivere.map((arb) => arb.organisasjonsnummer)
        })
    }
    const toggleForkaastede = (skjule: boolean) => {
        setHideForkastedeVedtak((state) => !state)
    }

    return (
        <Box background="surface-default">
            <Box background="bg-subtle" paddingBlock="4 0">
                <HStack gap="5">
                    <Switch size="small" onChange={(e) => toggleArbeidsgivere(e.target.checked) }>Åpne alle</Switch>
                    <Switch size="small" onChange={(e) => toggleForkaastede(e.target.checked) }>Skjul forkastede</Switch>
                </HStack>
            </Box>
            <Box padding="0">
                <SelectableTreeNode indent={0} className={styles.PersonNode} valgteTing={valgteTing} ting={person.aktørId} vedValg={toggleValgtTing}>
                    <div>
                        <span>{person.aktørId}</span>
                        <div>
                            <SporingLenke url={personSporingUrl(person.aktørId)} />
                            <KopierPersonPåminnelseJson person={person} />
                        </div>
                    </div>
                </SelectableTreeNode>
                {person.arbeidsgivere.map((arbeidsgiver) => (
                    <ArbeidsgiverNode
                        key={arbeidsgiver.id}
                        arbeidsgiver={arbeidsgiver}
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                    />
                ))}
            </Box>
        </Box>
    )
}
PersonTree.displayName = 'PersonTree'
