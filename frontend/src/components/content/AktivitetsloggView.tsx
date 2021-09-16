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
import {AktivitetDto, AktivitetsloggDto, KontekstDto} from '../../state/dto'
import {mapNotUndefined} from '../../utils'
import {useRecoilState, useRecoilValue} from "recoil";
import {ContentView, displayViewState, expandedHendelserState} from "../../state/state";
import classNames from 'classnames'
import styles from './AktivitetsloggView.module.css'
import copyIcon from "material-design-icons/content/svg/production/ic_content_copy_24px.svg";
import parseISO from "date-fns/parseISO";
import {format} from "date-fns";

const HendelserForPerson = React.memo(() => {
    const isSelected = useIsSelected()
    const aktivitetslogg = useAktivitetslogg()
    if (!isSelected) return null

    return <Hendelser aktiviteter={aktivitetslogg.aktiviteter} />
})

const HendelserForArbeidsgiver = React.memo(() => {
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

    return <Hendelser aktiviteter={aktiviteter} />
})

export const AktivitetsloggView = React.memo(() => {
    const person = usePerson()
    const useDisplayView = useRecoilValue(displayViewState)
    if(!useDisplayView.includes(ContentView.Hendelser)) return null

    return (
        <AktivitetsloggContext.Provider value={person.aktivitetslogg}>
            <ShowIfSelected>
                <HendelserForPerson />
            </ShowIfSelected>
            {person.arbeidsgivere.map((arbeidsgiver) => (
                <ArbeidsgiverContext.Provider value={arbeidsgiver} key={arbeidsgiver.id}>
                    <ShowIfSelected>
                        <HendelserForArbeidsgiver />
                    </ShowIfSelected>
                    {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => (
                        <VedtakContext.Provider value={vedtaksperiode} key={vedtaksperiode.id}>
                            <ShowIfSelected>
                                <HendelserForVedtaksperiode />
                            </ShowIfSelected>
                        </VedtakContext.Provider>
                    ))}
                </ArbeidsgiverContext.Provider>
            ))}
        </AktivitetsloggContext.Provider>
    )
})

const HendelserForVedtaksperiode = React.memo(() => {
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

    return <Hendelser aktiviteter={aktiviteter} />
})

const Hendelser = React.memo(({ aktiviteter }: { aktiviteter: AktivitetDto[] }) => {
    const kontekstFinnesiAktiviteter = (kontekst: KontekstDto, index: number): boolean =>
        !!aktiviteter.find((aktivitet) => aktivitet.kontekster.includes(index))

    const erMeldingsKontekst = (kontekst: KontekstDto) =>
        !!kontekst.kontekstMap.meldingsreferanseId && kontekst.kontekstType != 'GjenopptaBehandling'

    const aktivitetslogg = useAktivitetslogg()

    const hendelseKontektster: [KontekstDto, number][] = mapNotUndefined(aktivitetslogg.kontekster, (kontekst, index) => {
        if (kontekstFinnesiAktiviteter(kontekst, index) && erMeldingsKontekst(kontekst)) {
            return [kontekst, index]
        } else return undefined
    })

    return (
        <div className={styles.AktivitetListeView}>
            {hendelseKontektster.map(([kontekst, index]) => {
                const kontekstAktiviter = aktiviteter.filter((aktivitet) => aktivitet.kontekster.includes(index))
                return (
                    <Hendelse
                        aktiviteter={kontekstAktiviter}
                        kontekst={kontekst}
                        key={kontekst.kontekstMap.meldingsreferanseId}
                    />
                )
            })}
        </div>
    )
})

const Hendelse = React.memo(({ aktiviteter, kontekst }: { aktiviteter: AktivitetDto[]; kontekst: KontekstDto }) => {
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
    aktivitet: AktivitetDto
}

const AktivitetView: React.FC<AktivitetViewProps> = React.memo(({ aktivitet }: { aktivitet: AktivitetDto }) => {
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
    aktivitetslogg: AktivitetsloggDto,
    filter: (kontekst: KontekstDto) => boolean
): AktivitetDto[] => {
    const mapping = (y: KontekstDto, index: number) => filter(y) && index
    const konteksterIndex = mapNotUndefined(aktivitetslogg.kontekster, mapping)
    return aktivitetslogg.aktiviteter.filter((aktivitet) =>
        aktivitet.kontekster.find((kontekstI) => konteksterIndex.includes(kontekstI))
    )
}
