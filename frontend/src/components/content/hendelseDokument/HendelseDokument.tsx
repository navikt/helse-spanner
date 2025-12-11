import React from 'react'
import { KontekstDto } from '../../../state/dto'
import { Card } from '../../Card'
import { useSetRecoilState } from 'recoil'
import { åpneHendelseDokumentState } from '../../../state/state'
import classNames from 'classnames'
import styles from './HendelseDokument.module.css'
import { XMarkIcon } from '@navikt/aksel-icons'
import { useBackend } from '../../../external/backend'
import { useQuery } from 'react-query'
import { Spinner } from '../../Spinner'
import { Feilmelding } from '../../Feilmelding'
import ReactJson from '@microlink/react-json-view'

export const HendelseDokument = React.memo<{ kontekst: KontekstDto }>(({ kontekst }) => {
    const setÅpneHendelser = useSetRecoilState(åpneHendelseDokumentState)
    const fjernFraÅpneHendelser = () => setÅpneHendelser((prev) => prev.filter((hendelse) => hendelse !== kontekst))

    if (kontekst.kontekstMap.meldingsreferanseId == undefined) {
        throw Error('Kontekst mangler meldingsreferanse, burde ikke kunne skje')
    }

    return (
        <Card>
            Meldingsreferanse: {kontekst.kontekstMap.meldingsreferanseId}
            <button
                className={classNames(styles.LukkHendelseButton)}
                aria-label={'Lukk hendelse dokument'}
                onClick={fjernFraÅpneHendelser}
            >
                <XMarkIcon color={'black'} />
            </button>
            <br />
            <br />
            <HendelseJson meldingsReferanse={kontekst.kontekstMap.meldingsreferanseId} />
        </Card>
    )
})

export type FetchHendelseProps = {
    meldingsReferanse: string
}

const HendelseJson = (props: FetchHendelseProps) => {
    const backend = useBackend()
    try {
        const { isLoading, isError, data, error } = useQuery(['melding', props.meldingsReferanse], () => {
            return backend.hendelseForRef(props.meldingsReferanse)
        })
        if (isLoading) {
            return <Spinner />
        }
        if (isError) {
            return <Feilmelding feil={error} />
        }
        if (data === undefined) {
            return <Spinner />
        }
        return <ReactJson src={data} name={null} collapsed={1} sortKeys={true} />
    } catch (error) {
        return <Feilmelding feil={error} />
    }
}
