import React from 'react'
import { useAtom } from 'jotai'
import { expandedHendelserState, åpneHendelseDokumentState } from '../../../state/state'
import classNames from 'classnames'
import styles from './Hendelse.module.css'
import { FileJsonIcon, FilesIcon } from '@navikt/aksel-icons'
import { Aktivitet } from './Aktivitet'
import commonStyles from '../../Common.module.css'
import { Hendelsekontekst } from '../../../state/model'
import { writeToClipboard } from '../../../utils'
import { somNorskDato, somNorskKlokkeslett } from '../../i18n'

export const Hendelse = ({ kontekst }: { kontekst: Hendelsekontekst }) => {
    const aktiviteter = kontekst.aktiviteter
    let meldingsReferanseId = ''
    if (kontekst.kontekstMap.meldingsreferanseId != undefined) {
        meldingsReferanseId = kontekst.kontekstMap.meldingsreferanseId
    } else {
        throw Error(`kontekst = ${kontekst} har ikke meldingsreferanseId`)
    }
    const copyMeldingRefId = () => writeToClipboard(meldingsReferanseId)

    const [expandedHendelser, setExpandedHendelser] = useAtom(expandedHendelserState)
    const isExpanded = expandedHendelser.includes(kontekst.id)
    const toggleSelected = () => {
        if (isExpanded) {
            setExpandedHendelser(expandedHendelser.filter((it: number) => it !== kontekst.id))
        } else {
            setExpandedHendelser([...expandedHendelser, kontekst.id])
        }
    }

    const [åpneHendelser, setÅpneHendelser] = useAtom(åpneHendelseDokumentState)
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
        [åpneHendelser, setÅpneHendelser, kontekst]
    )

    const isError = aktiviteter.find((it) => it.nivå == 'FUNKSJONELL_FEIL')

    const isWarning = aktiviteter.find((it) => it.nivå == 'VARSEL')

    return (
        <>
            <div
                className={classNames(
                    styles.Hendelselinje,
                    isWarning && commonStyles.Warning,
                    isError && commonStyles.Error
                )}
            >
                <div
                    title={'Kl. ' + somNorskKlokkeslett(aktiviteter[0].tidsstempel)}
                    className={classNames(styles.DatoText, styles.SkriftMedNestenLikBredde, styles.FokusPåHover)}
                >
                    {somNorskDato(aktiviteter[0].tidsstempel)}
                </div>

                <button onClick={toggleSelected} className={styles.Hendelsetype}>
                    {kontekst.kontekstType}
                </button>
                <div className={classNames(styles.Meldingsreferanse, styles.SkriftMedNestenLikBredde)}>
                    {kontekst.kontekstMap.meldingsreferanseId}
                </div>
                <button
                    className={classNames(styles.KopierMeldingsreferanse)}
                    aria-label={'Kopier melding-id'}
                    onClick={copyMeldingRefId}
                >
                    <FilesIcon className={classNames(styles.Fil)} />
                </button>
                <button
                    className={classNames(styles.KopierMeldingsreferanse)}
                    aria-label={'Åpne hendelsedokument'}
                    onClick={åpneHendelse}
                >
                    <FileJsonIcon className={classNames(styles.Json)} />
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
}

