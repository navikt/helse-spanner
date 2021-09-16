import React, {PropsWithChildren} from 'react'
import {
    AktivitetsloggContext,
    ArbeidsgiverContext,
    useAktivitetslogg,
    useArbeidsgiver,
    useIsSelected,
    usePerson,
    useVedtak,
    VedtakContext,
} from '../../state/contexts'
import {Aktivitet, Aktivitetslogg, Kontekst} from '../../state/dto'
import {mapNotUndefined} from '../../utils'
import {useRecoilState, useRecoilValue} from "recoil";
import {ContentView, displayViewState, expandedHendelserState} from "../../state/state";
import classNames from 'classnames'
import styles from './AktivitetsloggView.module.css'
import copyIcon from "material-design-icons/content/svg/production/ic_content_copy_24px.svg";
import parseISO from "date-fns/parseISO";
import {format} from "date-fns";

const AktiviteterForPerson = React.memo(() => {
    const isSelected = useIsSelected()
    const aktivitetslogg = useAktivitetslogg()
    if (!isSelected) return null

    return <AktivitetListeView aktiviteter={aktivitetslogg.aktiviteter} />
})

const AktiviteterForArbeidsgiver = React.memo(() => {
    const isSelected = useIsSelected()
    const aktivitetslogg = useAktivitetslogg()
    const arbeidsgiver = useArbeidsgiver()
    if (!isSelected) return null

    const aktiviteter = aktiviteterForKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.organisasjonsnummer &&
                kontekst.kontekstMap.organisasjonsnummer === arbeidsgiver.organisasjonsnummer) ??
            false
    )

    return <AktivitetListeView aktiviteter={aktiviteter} />
})

export const AktivitetsloggView = React.memo(() => {
    const person = usePerson()
    const useDisplayView = useRecoilValue(displayViewState)
    if(!useDisplayView.includes(ContentView.Aktivitetslogg)) return null

    return (
        <AktivitetsloggContext.Provider value={person.aktivitetslogg}>
            <ShowIfSelected>
                <AktiviteterForPerson />
            </ShowIfSelected>
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <ShowIfSelected>
                        <AktiviteterForArbeidsgiver />
                    </ShowIfSelected>
                    {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                        <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                            <ShowIfSelected>
                                <AktiviteterForVedtaksperiode />
                            </ShowIfSelected>
                        </VedtakContext.Provider>
                    ))}
                </ArbeidsgiverContext.Provider>
            ))}
        </AktivitetsloggContext.Provider>
    )
})

const AktiviteterForVedtaksperiode = React.memo(() => {
    console.log('Rendrer vedtak view')
    const vedtaksperiode = useVedtak()
    const aktivitetslogg = useAktivitetslogg()
    const isSelected = useIsSelected()
    if (!isSelected) return null

    const aktiviteter = aktiviteterForKontekst(
        aktivitetslogg,
        (kontekst) =>
            (!!kontekst.kontekstMap.vedtaksperiodeId && kontekst.kontekstMap.vedtaksperiodeId === vedtaksperiode.id) ??
            false
    )

    return <AktivitetListeView aktiviteter={aktiviteter} />
})

const AktivitetListeView = React.memo(({ aktiviteter }: { aktiviteter: Aktivitet[] }) => {
    const kontekstFinnesiAktiviteter = (kontekst: Kontekst, index: number): boolean =>
        !!aktiviteter.find((aktivitet) => aktivitet.kontekster.includes(index))

    const erMeldingsKontekst = (kontekst: Kontekst) =>
        !!kontekst.kontekstMap.meldingsreferanseId && kontekst.kontekstType != 'GjenopptaBehandling'

    const aktivitetslogg = useAktivitetslogg()

    const hendelseKontektster: [Kontekst, number][] = mapNotUndefined(aktivitetslogg.kontekster, (kontekst, index) => {
        if (kontekstFinnesiAktiviteter(kontekst, index) && erMeldingsKontekst(kontekst)) {
            return [kontekst, index]
        } else return undefined
    })

    return (
        <div className={styles.AktivitetListeView}>
            {hendelseKontektster.map(([kontekst, index]) => {
                const kontekstAktiviter = aktiviteter.filter((aktivitet) => aktivitet.kontekster.includes(index))
                return (
                    <HendelseView
                        aktiviteter={kontekstAktiviter}
                        kontekst={kontekst}
                        key={kontekst.kontekstMap.meldingsreferanseId}
                    />
                )
            })}
        </div>
    )
})

const HendelseView = React.memo(({ aktiviteter, kontekst }: { aktiviteter: Aktivitet[]; kontekst: Kontekst }) => {
    const writeToClipboard = (data: string) => navigator.clipboard.writeText(data).catch(error => console.warn("Error copying to clipboard:", error));
    let meldingsReferanseId = ""
        if( kontekst.kontekstMap.meldingsreferanseId != undefined) {
            meldingsReferanseId = kontekst.kontekstMap.meldingsreferanseId
        } else {
            throw Error(`kontekst = ${kontekst} har ikke meldingsreferanseId` )
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
            <span className={classNames(styles.HendelseDatoText)}>{format(parseISO(aktiviteter[0].tidsstempel), "yyyy-MM-dd")}</span>
            <button onClick={toggleSelected} className={styles.HendelseToggleButton}>{kontekst.kontekstType}</button>
            <span className={classNames(styles.HendelseIDText)}>{kontekst.kontekstMap.meldingsreferanseId}</span>
            <button className={styles.HendelseIDButton}><img src={copyIcon} className={styles.Icon} alt={"kopier tekst"} onClick={copyMeldingRefId}/></button>


            {isExpanded &&
                <div style={{gridArea: "aktiviteter"}}>{
                    aktiviteter.map((it, index) => (
                        <AktivitetView key={index} aktivitet={it} />
                    ))}
                </div>
            }
        </div>
    )
})

type AktivitetViewProps = {
    aktivitet: Aktivitet
}

const AktivitetView: React.FC<AktivitetViewProps> = React.memo(({ aktivitet }: { aktivitet: Aktivitet }) => {
    const isWarning = aktivitet.alvorlighetsgrad == "WARN"
    return  <div className={styles.AktivitetView}>
        <span className={classNames(styles.AktivitetViewLinje, isWarning && styles.Warning)}>
            <bdi className={styles.AktivitetViewAlvorlighetsgradLabel}>{aktivitet.alvorlighetsgrad}</bdi>
            <bdi className={styles.AktivitetViewMeldingText}>{aktivitet.melding}</bdi>
        </span>
    </div>
})



const ShowIfSelected: React.FC<PropsWithChildren<any>> = React.memo(({ children }) => {
    const isSelected = useIsSelected()
    if (!isSelected) return null
    return <>{children}</>
})

const aktiviteterForKontekst = (
    aktivitetslogg: Aktivitetslogg,
    filter: (kontekst: Kontekst) => boolean
): Aktivitet[] => {
    const mapping = (y: Kontekst, index: number) => filter(y) && index
    const konteksterIndex = mapNotUndefined(aktivitetslogg.kontekster, mapping)
    return aktivitetslogg.aktiviteter.filter((aktivitet) =>
        aktivitet.kontekster.find((kontekstI) => konteksterIndex.includes(kontekstI))
    )
}
