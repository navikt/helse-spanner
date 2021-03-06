import React from 'react'
import { useRecoilValue } from 'recoil'
import { √•pneHendelseDokumentState } from '../../../state/state'
import { HendelseDokument } from './HendelseDokument'

export const HendelseDokumentView = React.memo(({}) => {
    const √•pneHendelser = useRecoilValue(√•pneHendelseDokumentState)

    return (
        <>
            {√•pneHendelser.map((hendelse) => (
                <HendelseDokument kontekst={hendelse} key={hendelse.kontekstMap.meldingsreferanseId} />
            ))}
        </>
    )
})
