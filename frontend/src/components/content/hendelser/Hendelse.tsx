import React from 'react'
import { useRecoilState } from 'recoil'
import {expandedHendelserState, åpneHendelseDokumentState} from '../../../state/state'
import classNames from 'classnames'
import styles from './Hendelse.module.css'
import { format } from 'date-fns'
import parseISO from 'date-fns/parseISO'
import {Copy, FileContent} from '@navikt/ds-icons'
import { Aktivitet } from './Aktivitet'
import commonStyles from '../../Common.module.css'
import {Kontekst} from "../../../state/model";

export const Hendelse = React.memo(
    ({ kontekst }: { kontekst: Kontekst }) => {
        const writeToClipboard = (data: string) =>
            navigator.clipboard.writeText(data).catch((error) => console.warn('Error copying to clipboard:', error))
        const aktiviteter = kontekst.aktiviteter
        let meldingsReferanseId = ''
        if (kontekst.kontekstMap.meldingsreferanseId != undefined) {
            meldingsReferanseId = kontekst.kontekstMap.meldingsreferanseId
        } else {
            throw Error(`kontekst = ${kontekst} har ikke meldingsreferanseId`)
        }
        const copyMeldingRefId = () => writeToClipboard(meldingsReferanseId)

        const [expandedHendelser, setExpandedHendelser] = useRecoilState(expandedHendelserState)
        const isExpanded = expandedHendelser.includes(kontekst.id)
        const toggleSelected = () => {
            if (isExpanded) {
                setExpandedHendelser(expandedHendelser.filter((it) => it !== kontekst.id))
            } else {
                setExpandedHendelser([...expandedHendelser, kontekst.id])
            }
        }

        const [åpneHendelser, setÅpneHendelser] = useRecoilState(åpneHendelseDokumentState)
        const åpneHendelse = React.useMemo( () => () => {
            const nyåpneHendelser = [...åpneHendelser, kontekst]
            const unique = nyåpneHendelser.filter((v, i, a) => a.indexOf(v) === i);
            setÅpneHendelser(() => unique)
        },
            [åpneHendelser, setÅpneHendelser]
        )

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
                    <button
                        className={classNames(styles.KopierMeldingsreferanse)}
                        aria-label={'Åpne hendelse dokument'}
                        onClick={åpneHendelse}
                    >
                        <FileContent color={'black'} />
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