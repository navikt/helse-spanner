import React from 'react'
import { AktivitetDto, KontekstDto } from '../../../state/dto'
import { useRecoilState } from 'recoil'
import { expandedHendelserState } from '../../../state/state'
import classNames from 'classnames'
import styles from './Hendelse.module.css'
import { format } from 'date-fns'
import parseISO from 'date-fns/parseISO'
import copyIcon from 'material-design-icons/content/svg/production/ic_content_copy_24px.svg'
import { Aktivitet } from './Aktivitet'

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

        return (
            <div>
                <span className={classNames(styles.DatoText)}>
                    {format(parseISO(aktiviteter[0].tidsstempel), 'yyyy-MM-dd')}
                </span>
                <button onClick={toggleSelected} className={styles.Ekspander}>
                    {kontekst.kontekstType}
                </button>
                <span className={classNames(styles.Meldingsreferanse)}>{kontekst.kontekstMap.meldingsreferanseId}</span>
                <button className={classNames(styles.KopierMeldingsreferanse)}>
                    <img
                        src={copyIcon}
                        className={classNames(styles.Icon)}
                        alt={'kopier tekst'}
                        onClick={copyMeldingRefId}
                    />
                </button>

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
