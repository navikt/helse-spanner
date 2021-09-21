import React from 'react'
import { AktivitetDto, KontekstDto } from '../../../state/dto'
import { useRecoilState } from 'recoil'
import { expandedHendelserState } from '../../../state/state'
import classNames from 'classnames'
import styles from './Hendelse.module.css'
import { format } from 'date-fns'
import parseISO from 'date-fns/parseISO'
import { Copy } from '@navikt/ds-icons'
import { Aktivitet } from './Aktivitet'
import commonStyles from '../../Common.module.css'

export const Hendelse = React.memo(
    ({ aktiviteter, kontekst }: { aktiviteter: AktivitetDto[]; kontekst: KontekstDto }) => {
        const writeToClipboard = (data: string) =>
            navigator.clipboard.writeText(data).catch((error) => console.warn('Error copying to clipboard:', error))
        let meldingsReferanseId = ''
        if (kontekst.kontekstMap.meldingsreferanseId != undefined) {
            meldingsReferanseId = kontekst.kontekstMap.meldingsreferanseId
        } else {
            throw Error(`kontekst = ${kontekst} har ikke meldingsreferanseId`)
        }
        const copyMeldingRefId = () => writeToClipboard(meldingsReferanseId)

        const [expandedHendelser, setExpandedHendelser] = useRecoilState(expandedHendelserState)
        const isExpanded = expandedHendelser.includes(meldingsReferanseId)

        const toggleSelected = () => {
            if (isExpanded) {
                setExpandedHendelser(expandedHendelser.filter((it) => it !== meldingsReferanseId))
            } else {
                setExpandedHendelser([...expandedHendelser, meldingsReferanseId])
            }
        }

        const isError = aktiviteter.find((it) => it.alvorlighetsgrad == 'ERROR')

        const isWarning = aktiviteter.find((it) => it.alvorlighetsgrad == 'WARN')

        return (
            <div>
                <div className={classNames(styles.Header, isWarning && commonStyles.Warning, isError && commonStyles.Error)}>
                    <div className={classNames(styles.DatoText)}>
                        {format(parseISO(aktiviteter[0].tidsstempel), 'yyyy-MM-dd')}
                    </div>

                    <button onClick={toggleSelected} className={styles.Ekspander}>
                        {kontekst.kontekstType}
                    </button>
                    <div className={classNames(styles.Meldingsreferanse)}>
                        {kontekst.kontekstMap.meldingsreferanseId}
                    </div>
                    <button
                        className={classNames(styles.KopierMeldingsreferanse)}
                        aria-label={'Kopier melding id'}
                        onClick={copyMeldingRefId}
                    >
                        <Copy color={'black'} />
                    </button>
                </div>
                {isExpanded && (
                    <div style={{ gridArea: 'aktiviteter' }}>
                        {aktiviteter.map((it, index) => (
                            <Aktivitet key={index} aktivitet={it} />
                        ))}
                    </div>
                )}
            </div>
        )
    }
)

Hendelse.displayName="Hendelse"