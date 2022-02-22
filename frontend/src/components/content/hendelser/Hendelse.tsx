import React from 'react'
import { useRecoilState } from 'recoil'
import { expandedHendelserState, åpneHendelseDokumentState } from '../../../state/state'
import classNames from 'classnames'
import styles from './Hendelse.module.css'
import { Copy, FileContent } from '@navikt/ds-icons'
import { Aktivitet } from './Aktivitet'
import commonStyles from '../../Common.module.css'
import { Kontekst } from '../../../state/model'
import { writeToClipboard } from '../../../utils'
import { somNorskDato } from '../../i18n'

export const Hendelse = React.memo(({ kontekst }: { kontekst: Kontekst }) => {
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
    const åpneHendelse = React.useMemo(
        () => () => {
            const ikkeLagretISpleis = ['Utbetalingshistorikk', 'Påminnelse']
            if (ikkeLagretISpleis.includes(kontekst.kontekstType)) {
                alert(`Hendelser av type ${kontekst.kontekstType} er ikke lagret i Spleis.\n\n\n:(`)
                return
            }
            const nyåpneHendelser = [...åpneHendelser, kontekst]
            const unique = nyåpneHendelser.filter((v, i, a) => a.indexOf(v) === i)
            setÅpneHendelser(() => unique)
        },
        [åpneHendelser, setÅpneHendelser]
    )

    const isError = aktiviteter.find((it) => it.alvorlighetsgrad == 'ERROR')

    const isWarning = aktiviteter.find((it) => it.alvorlighetsgrad == 'WARN')

    return (
        <>
            <div
                className={classNames(
                    styles.Hendelselinje,
                    isWarning && commonStyles.Warning,
                    isError && commonStyles.Error
                )}
            >
                <div className={classNames(styles.DatoText)}>{somNorskDato(aktiviteter[0].tidsstempel)}</div>

                <button onClick={toggleSelected} className={styles.Hendelsetype}>
                    {kontekst.kontekstType}
                </button>
                <div className={classNames(styles.Meldingsreferanse)}>{kontekst.kontekstMap.meldingsreferanseId}</div>
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
        </>
    )
})

Hendelse.displayName = 'Hendelse'
