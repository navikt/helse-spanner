import React from 'react'
import { useRecoilValue } from 'recoil'
import { åpneHendelseDokumentState } from '../../../state/state'
import { HendelseDokument } from './HendelseDokument'

export const HendelseDokumentView = React.memo(({}) => {
    const åpneHendelser = useRecoilValue(åpneHendelseDokumentState)

    return (
        <>
            {åpneHendelser.map((hendelse) => (
                <HendelseDokument kontekst={hendelse} key={hendelse.kontekstMap.meldingsreferanseId} />
            ))}
        </>
    )
})
