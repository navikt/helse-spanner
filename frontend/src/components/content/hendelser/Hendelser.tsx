import React from 'react'
import { Hendelse } from './Hendelse'
import compareAsc from 'date-fns/compareAsc'
import { useRecoilState } from 'recoil'
import {hendelseprefix, skjulPåminnelserState, visBareFeilState} from '../../../state/state'
import {Hendelsekontekst} from '../../../state/model'
import {HStack, Switch, TextField} from "@navikt/ds-react";

const erPåminnelse = (kontekst: Hendelsekontekst) =>
    kontekst.kontekstType == 'Påminnelse' || kontekst.kontekstType == 'Utbetalingshistorikk'

const harFeil = (kontekst: Hendelsekontekst) => kontekst.harError || kontekst.harWarning

export const Hendelser = ({ hendelser }: { hendelser: Hendelsekontekst[] }) => {
    const [visBareFeil, setVisBareFeil] = useRecoilState(visBareFeilState)
    const [skjulPåminnelser, setSkjulPåminnelser] = useRecoilState(skjulPåminnelserState)
    const [prefix, setPrefix] = useRecoilState(hendelseprefix)
    const toggleVisBareFeil = () => setVisBareFeil(!visBareFeil)
    const toggleVisPåminnelser = () => setSkjulPåminnelser(!skjulPåminnelser)
    const harRettPrefix = (kontekst: Hendelsekontekst) => kontekst.kontekstType.toLowerCase().startsWith(prefix.toLowerCase())
    const oppdaterPrefix = (e: React.FormEvent<HTMLInputElement>) => {
        setPrefix(e.currentTarget.value)
    }

    const filtrerteHendelser = hendelser
        .filter((it) => !skjulPåminnelser || !erPåminnelse(it) || harFeil(it))
        .filter((it) => !visBareFeil || harFeil(it))
        .filter((it) => harRettPrefix(it))

    const sorterteHendelser = filtrerteHendelser.sort((a, b) => compareAsc(a.opprettet, b.opprettet))

    return (
        <>
            <HStack gap="5">
                <Switch size="small" onChange={(_) => toggleVisBareFeil() }>Bare feil</Switch>
                <Switch size="small" checked={skjulPåminnelser} onChange={(_) => toggleVisPåminnelser() }>Skjul påminnelser og utbetalingshistorikk</Switch>
                <TextField id={"hendelseprefix_input"} size={"small"} onInput={(e) => oppdaterPrefix(e) } label={"prefix (optional)"} ></TextField>
            </HStack>
            {sorterteHendelser.map((it) => {
                return (!visBareFeil || it.harError || it.harWarning) && <Hendelse kontekst={it} key={it.id} />
            })}
        </>
    )
}
