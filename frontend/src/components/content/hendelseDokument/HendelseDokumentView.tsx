import React from 'react'
import { useAtomValue } from 'jotai'
import { åpneHendelseDokumentState } from '../../../state/state'
import { HendelseDokument } from './HendelseDokument'

export const HendelseDokumentView = () => {
    const åpneHendelser = useAtomValue(åpneHendelseDokumentState)

    return (
        <>
            {åpneHendelser.map((hendelse) => (
                <HendelseDokument kontekst={hendelse} key={hendelse.kontekstMap.meldingsreferanseId} />
            ))}
        </>
    )
}
