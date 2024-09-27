import styles from './PersonTree.module.css'
import React, {useState} from 'react'
import {usePerson,} from '../../state/contexts'
import {personSporingUrl} from './links'
import {Box, HStack, Switch} from "@navikt/ds-react";
import SporingLenke from "./SporingLenke";
import KopierPersonPåminnelseJson from "./KopierPersonPåminnelseJson";
import SelectableTreeNode from "./SelectableTreeNode";
import {ArbeidsgiverNode} from "./ArbeidsgiverNode";
import {Key, useKeyboard} from "./useKeyboard";
import {VilkårsgrunnlagHistorikkNode} from "./VilkårsgrunnlagNode";


interface PersonTreeProps {
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
}

export const PersonTree = ({valgteTing, toggleValgtTing } : PersonTreeProps) => {
    const person = usePerson()
    const [visForkastede, setVisForkastede] = useState(true)
    const [visBehandlinger, setVisBehandlinger] = useState(false)
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
    useKeyboard([{
        key: Key.B,
        action: () => {
            setVisBehandlinger((forrige) => !forrige)
        }
    }]);

    return (
        <Box background="surface-default">
            <Box background="bg-subtle" paddingBlock="4 0">
                <HStack gap="5">
                    <Switch size="small" onChange={(e) => toggleArbeidsgivere(e.target.checked) }>Åpne alle</Switch>
                    <Switch size="small" onChange={() => setVisForkastede((forrige) => !forrige) }>Skjul forkastede</Switch>
                    <Switch size="small" checked={visBehandlinger} onChange={() => setVisBehandlinger((forrige) => !forrige) }>Vis behandlinger med endringer</Switch>
                </HStack>
            </Box>
            <Box padding="0">
                <SelectableTreeNode indent={0} className={styles.PersonNode} valgteTing={valgteTing} ting={person.aktørId} vedValg={toggleValgtTing}>
                    <span>{person.aktørId}</span>
                    <div className={styles.Knapper}>
                        <SporingLenke url={personSporingUrl(person.aktørId)} />
                        <KopierPersonPåminnelseJson person={person} />
                    </div>
                </SelectableTreeNode>
                {person.arbeidsgivere.map((arbeidsgiver) => (
                    <ArbeidsgiverNode
                        key={arbeidsgiver.id}
                        arbeidsgiver={arbeidsgiver}
                        erUtvidet={ erArbeidsgiverUtvidet(arbeidsgiver.id) }
                        toggleUtvidet={() => toggleArbeidsgiver(arbeidsgiver.id) }
                        visForkastede={visForkastede}
                        visBehandlinger={visBehandlinger}
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                    />
                ))}
                {person.vilkårsgrunnlagHistorikk.length >= 1 &&
                    <VilkårsgrunnlagHistorikkNode
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                        vilkårsgrunnlagHistorikkInnslag={person.vilkårsgrunnlagHistorikk[0]}
                    />
                }
            </Box>
        </Box>
    )
}
PersonTree.displayName = 'PersonTree'
