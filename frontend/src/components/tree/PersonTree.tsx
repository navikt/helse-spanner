import styles from './PersonTree.module.css'
import React, {useState} from 'react'
import {usePerson,} from '../../state/contexts'
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
    const [visForkastede, setVisForkastede] = useState(true)
    const [erUtvidet, setErUtvidet] = useState([] as string[])
    const erArbeidsgiverUtvidet = (id: string) => erUtvidet.includes(id)
    const toggleArbeidsgiver = (id: string) => setErUtvidet((arbeidsgivere) => {
        if (arbeidsgivere.includes(id)) return arbeidsgivere.filter((it) => it !== id)
        return [...arbeidsgivere, id]
    })
    const toggleArbeidsgivere = (viseAlle: boolean) => {
        setErUtvidet(() => {
            if (viseAlle) return person.arbeidsgivere.map((it) => it.id)
            return []
        })
    }
    return (
        <Box background="surface-default">
            <Box background="bg-subtle" paddingBlock="4 0">
                <HStack gap="5">
                    <Switch size="small" onChange={(e) => toggleArbeidsgivere(e.target.checked) }>Åpne alle</Switch>
                    <Switch size="small" onChange={(e) => setVisForkastede((forrige) => !forrige) }>Skjul forkastede</Switch>
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
                        erUtvidet={ erArbeidsgiverUtvidet(arbeidsgiver.id) }
                        toggleUtvidet={() => toggleArbeidsgiver(arbeidsgiver.id) }
                        visForkastede={visForkastede}
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                    />
                ))}
            </Box>
        </Box>
    )
}
PersonTree.displayName = 'PersonTree'
