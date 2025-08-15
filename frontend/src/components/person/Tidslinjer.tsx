import React, {useEffect, useState} from "react";
import {usePerson} from "../../state/contexts";
import {add, differenceInMonths, sub} from "date-fns";
import {Timeline} from "@navikt/ds-react";
import styles from "./Tidslinjer.module.css";
import {BriefcaseIcon, Buldings3Icon, PackageIcon, ParasolBeachIcon, PiggybankIcon, TrashIcon} from "@navikt/aksel-icons";
import {VedtakDto} from "../../state/dto";
import { format } from 'date-fns'
import {somNorskDato} from "../i18n";


type TidslinjeState = {
    endDate: Date,
    startDate: Date,
    currentZoom: number
}

const Zoom = ({step, onClick}: { step: number, onClick: (step: number) => void }) => {
    return <button onClick={() => {
        onClick(step)
    }}>Zoom {step} mnd</button>
}

export const Tidslinjer = ({valgteTing, toggleValgtTing}: {
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
}) => {
    const person = usePerson()
    const j = person.arbeidsgivere.flatMap((it) => {
        return [...it.vedtaksperioder.map((vedtaksperiode) => vedtaksperiode.fom), ...it.forkastede.map((forkastet) => forkastet.vedtaksperiode.fom)]
    })
    const sortertePerioder = person.arbeidsgivere
        .flatMap((it) => {
            return [...it.vedtaksperioder.map((vedtaksperiode) => vedtaksperiode.tom), ...it.forkastede.map((forkastet) => forkastet.vedtaksperiode.tom)]
        })
        .map((it) => new Date(it))
        .sort((a, b) => (b.getTime() - a.getTime()))

    const nå = sortertePerioder.length > 0 ? sortertePerioder[0] : new Date()
    const [tidslinjeperiode, setTidslinjeperiode] = useState({
        endDate: add(nå, {months: 1}),
        startDate: sub(nå, {months: 11}),
        currentZoom: 12
    } as TidslinjeState)
    const [skalViseInfotrygdUtvalg, setSkalViseInfotrygdUtvalg] = useState(false)
    const [infotrygdhistorikkElementIndex, setInfotrygdhistorikkElementIndex] = useState(0)

    useEffect(() => {
        // zoom slik at vedtaksperiodene som er valgt, vises i tidslinjen :)
        const vedtak = person.arbeidsgivere
            .flatMap((it) => it.vedtaksperioder)
            .find((it) => valgteTing.find((a) => it.id === a))

        const alleVedtak = person.arbeidsgivere.flatMap((it) => it.vedtaksperioder)
        const valgteVedtak = SorterNyesteVedtakØverst(valgteTing
            .map((valgtTing) => alleVedtak.find((it) => it.id === valgtTing))
            .filter((it) => typeof it !== 'undefined') as VedtakDto[])

        if (valgteVedtak.length == 0) return

        const nyesteVedtak = valgteVedtak[0]
        const eldsteVedtak = valgteVedtak[valgteVedtak.length - 1]
        setTidslinjeperiode((current) => {
            const endDate = new Date(nyesteVedtak.tom) > current.endDate ? add(new Date(nyesteVedtak.tom), {months: 1}) : current.endDate
            const startDate = new Date(eldsteVedtak.fom) < current.startDate ? sub(new Date(eldsteVedtak.fom), {months: 1}) : current.startDate
            return {
                endDate: endDate,
                startDate: startDate,
                currentZoom: differenceInMonths(endDate, startDate)
            }
        })
    }, [valgteTing]);

    const scrollStep = (tidslinjeperiode: TidslinjeState) => Math.max(1, Math.round(tidslinjeperiode.currentZoom / 3))
    const scroll = (tidslinjeperiode: TidslinjeState, strategy: (d: Date, dd: Duration) => Date): TidslinjeState => {
        return {
            endDate: strategy(tidslinjeperiode.endDate, {months: scrollStep(tidslinjeperiode)}),
            startDate: strategy(tidslinjeperiode.startDate, {months: scrollStep(tidslinjeperiode)}),
            currentZoom: tidslinjeperiode.currentZoom
        }
    }

    const oppdaterZoom = (step: number) => {
        setTidslinjeperiode((current) => ({
            endDate: current.endDate,
            startDate: sub(current.endDate, {months: step}),
            currentZoom: step
        } as TidslinjeState))
    }

    const skjæringstidspunkter = Array.from(new Set(person.arbeidsgivere
        .flatMap((it) => it.vedtaksperioder.map((it) => it.skjæringstidspunkt))
        .filter((it) => typeof it === 'string')
        .map((it) => it as string)
    ))

    const aktiveVilkårsgrunnlag = person.vilkårsgrunnlagHistorikk.length >= 1
        ?
        Array.from(new Set(person.vilkårsgrunnlagHistorikk[0].vilkårsgrunnlag))
        :
        []

    const alleVilkårsgrunnlag = person.vilkårsgrunnlagHistorikk.length >= 1
        ?
        Array.from(new Set(person.vilkårsgrunnlagHistorikk.flatMap((it) => it.vilkårsgrunnlag)))
        :
        []

    return (<div className="min-w-[800px]">
        <Timeline className={styles.tidslinje} direction={"right"} startDate={tidslinjeperiode.startDate}
                  endDate={tidslinjeperiode.endDate}>
            {skjæringstidspunkter.map((skjæringstidspunkt) => {
                const detteVilkårsgrunnlaget = alleVilkårsgrunnlag.find((it) => it.skjæringstidspunkt == skjæringstidspunkt)?.vilkårsgrunnlagId
                const aktivt = aktiveVilkårsgrunnlag.find((it) => it.skjæringstidspunkt == skjæringstidspunkt)
                const dødt = detteVilkårsgrunnlaget && !aktivt
                const tekst = aktivt ?
                    <p>Skjæringstidspunkt: {skjæringstidspunkt} <br/> Status: Med aktivt vilkårsgrunnlag </p>
                    : dødt ?
                        <p>Skjæringstidspunkt: {skjæringstidspunkt} <br/> Status: Med dødt vilkårsgrunnlag💀 </p>
                        : <p>Skjæringstidspunkt: {skjæringstidspunkt} <br/> Status: Ikke vilkårsprøvd </p>
                return (<Timeline.Pin
                    style={aktivt ? {borderStyle: "dotted"} : dødt ? {
                        borderStyle: "solid",
                        borderWidth: "thick",
                        borderColor: "whitesmoke"
                    } : {}}
                    date={new Date(skjæringstidspunkt)}
                    onClick={(e) => {
                        if (detteVilkårsgrunnlaget) toggleValgtTing(e, detteVilkårsgrunnlaget)
                    }}>
                    {tekst}
                </Timeline.Pin>)
            })}
            {person.arbeidsgivere
                .filter((arbeidsgiver) => arbeidsgiver.vedtaksperioder.length > 0 || arbeidsgiver.forkastede.length > 0)
                .map((arbeidsgiver) => {
                    return <Timeline.Row label={arbeidsgiver.organisasjonsnummer} icon={<BriefcaseIcon aria-hidden/>}
                                         className={styles.tidslijerad}>
                        {[...arbeidsgiver.vedtaksperioder.flatMap((vedtaksperiode) => {
                            const vedtaksperiodeTimelineMedArbeidsgiverperiode = getArbeidsgiverperiodeTimeLine(vedtaksperiode)

                            return vedtaksperiodeTimelineMedArbeidsgiverperiode.map((periodeIVedtaksperiode, index) => {

                                let klassenavn: string
                                if (index == 0) {
                                    klassenavn = styles.firstElementInVedtaksperiode
                                } else if (index == vedtaksperiodeTimelineMedArbeidsgiverperiode.length - 1) {
                                    klassenavn = styles.lastElementInVedtaksperiode
                                } else {
                                    klassenavn = styles.elementInTheMiddleOfVedtaksperiode
                                }
                                if (periodeIVedtaksperiode.type == 'arbeidsgiverperiode') {
                                    klassenavn += " " + styles.arbeidsgiverperiode
                                }
                                const erValgt = typeof valgteTing.find((it) => it == vedtaksperiode.id) !== 'undefined'
                                if (erValgt) {
                                    klassenavn += " " + styles.aktiv
                                }

                                return <Timeline.Period
                                    key={vedtaksperiode.id}
                                    start={periodeIVedtaksperiode.fom}
                                    end={periodeIVedtaksperiode.tom}
                                    status={statusForVedtaksperiode((vedtaksperiode.tilstand))}
                                    className={klassenavn}
                                    onSelectPeriod={(e) => {
                                        toggleValgtTing(e, vedtaksperiode.id)
                                    }}
                                >
                                    {periodeIVedtaksperiode.type == 'arbeidsgiverperiode' ?
                                        <div>
                                            Arbeidsgiverperiode: {format(periodeIVedtaksperiode.fom, 'dd.MM.yyyy')} - {format(periodeIVedtaksperiode.tom, 'dd.MM.yyyy')}
                                            <br></br>
                                            Tilstand: {vedtaksperiode.tilstand}
                                        </div>
                                        :
                                        <div>
                                            Vedtaksperiode: {somNorskDato(vedtaksperiode.fom)} - {somNorskDato(vedtaksperiode.tom)}
                                            <br></br>
                                            Tilstand: {vedtaksperiode.tilstand}
                                        </div>

                                    }

                                </Timeline.Period>
                            })
                        }), ...arbeidsgiver.forkastede.map((forkastet) => {
                            const fom = new Date(forkastet.vedtaksperiode.fom)
                            const tom = new Date(forkastet.vedtaksperiode.tom)
                            return <Timeline.Period
                                key={forkastet.vedtaksperiode.id}
                                start={fom}
                                end={tom}
                                status="danger"
                                icon={<TrashIcon aria-hidden/>}
                                onSelectPeriod={(e) => {
                                    toggleValgtTing(e, forkastet.vedtaksperiode.id)
                                }}
                            >
                                <div>
                                    {somNorskDato(forkastet.vedtaksperiode.fom)} - {somNorskDato(forkastet.vedtaksperiode.tom)}
                                    <br></br>
                                    Tilstand: {forkastet.vedtaksperiode.tilstand}
                                </div>
                            </Timeline.Period>
                        })]}
                    </Timeline.Row>
                })
            }
            <Timeline.Row onClick={() => {
                setSkalViseInfotrygdUtvalg((førverdi) => !førverdi)
            }} label="Infotrygd" icon={<PackageIcon aria-hidden/>} className={styles.tidslijerad}>
                {person.infotrygdhistorikk.length > 0 ? (
                    [...person.infotrygdhistorikk[infotrygdhistorikkElementIndex].ferieperioder.map((it) => {
                        return <Timeline.Period start={new Date(it.fom)} end={new Date(it.tom)} status="neutral"
                                                icon={<ParasolBeachIcon aria-hidden/>}>
                            <div>{it.fom} - {it.tom}</div>
                        </Timeline.Period>
                    }), ...person.infotrygdhistorikk[infotrygdhistorikkElementIndex].personutbetalingsperioder.map((it) => {
                        return <Timeline.Period start={new Date(it.fom)} end={new Date(it.tom)} status="success"
                                                icon={<PiggybankIcon aria-hidden/>}>
                            <div>{it.fom} - {it.tom} - brukerutbetaling</div>
                        </Timeline.Period>
                    }), ...person.infotrygdhistorikk[infotrygdhistorikkElementIndex].arbeidsgiverutbetalingsperioder.map((it) => {
                        return <Timeline.Period start={new Date(it.fom)} end={new Date(it.tom)} status="success"
                                                icon={<Buldings3Icon aria-hidden/>}>
                            <div>{it.fom} - {it.tom} - refusjon
                                til {it.hasOwnProperty("orgnr") ? it.orgnr : "N/A"}</div>
                        </Timeline.Period>
                    })]
                ) : null}
            </Timeline.Row>
            <Timeline.Zoom>
                <Zoom step={48} onClick={oppdaterZoom}/>
                <Zoom step={24} onClick={oppdaterZoom}/>
                <Zoom step={12} onClick={oppdaterZoom}/>
                <Zoom step={6} onClick={oppdaterZoom}/>
                <Zoom step={2} onClick={oppdaterZoom}/>

                <button onClick={() => {
                    setTidslinjeperiode((current) => scroll(current, add))
                }}>« fremover {scrollStep(tidslinjeperiode)} mnd
                </button>
                <button onClick={() => {
                    setTidslinjeperiode((current) => scroll(current, sub))
                }}>tilbake {scrollStep(tidslinjeperiode)} mnd »
                </button>
            </Timeline.Zoom>
        </Timeline>
        {
            skalViseInfotrygdUtvalg && (<>
                <p>Hvilken Infotrygdhistorikk vil du basere visningen på?</p>
                <select onChange={(e) => {
                    setInfotrygdhistorikkElementIndex(e.target.selectedIndex)
                    setSkalViseInfotrygdUtvalg(false)
                }}>{person.infotrygdhistorikk.map((a, i) => <option value={i}
                                                                    selected={i == infotrygdhistorikkElementIndex}>index {i}</option>)}</select>
            </>)
        }
    </div>)
        ;
}

const SorterNyesteVedtakØverst = (input: VedtakDto[]): VedtakDto[] =>
    input.sort((a, b) => (new Date(b.tom).getTime() - new Date(a.tom).getTime()))

function statusForVedtaksperiode(tilstand: string): "success" | "warning" | "danger" | "info" | "neutral" {
    if (tilstand.endsWith("AVSLUTTET")) return "success"
    if (tilstand.endsWith("AVSLUTTET_UTEN_UTBETALING")) return "neutral"
    if (["AVVENTER_GODKJENNING", "AVVENTER_GODKJENNING_REVURDERING"].includes(tilstand)) return "warning"
    return "info"
}

function nextDay(date: Date) {
    return new Date(date.setDate(date.getDate() + 1));
}

function previousDay(tempFom: Date) {
    return new Date(tempFom.setDate(tempFom.getDate() - 1));
}

function getArbeidsgiverperiodeTimeLine(vedtak: VedtakDto): { fom: Date, tom: Date, type: string }[] {
    let vedtaksperiodeFom = new Date(vedtak.fom)

    let vedtaksperiodeTom = new Date(vedtak.tom)

    const listeOverDager: { fom: Date, tom: Date, type: string }[] = []
    vedtak.gjeldende.arbeidsgiverperiode.map((arbeidsgiverperiode) => {
        const agpFom = new Date(arbeidsgiverperiode.fom)
        const agpTom = new Date(arbeidsgiverperiode.tom)
        const liggerArbeidsgiverperiodenUtenforVedtaksperioden = agpFom.getTime() > new Date(vedtak.tom).getTime() || agpTom.getTime() < new Date(vedtak.fom).getTime();
        if (liggerArbeidsgiverperiodenUtenforVedtaksperioden) {
        } else {
            if (agpFom.getTime() > vedtaksperiodeFom.getTime()) {
                const tempFom = new Date(agpFom)
                const tempTom = new Date(agpTom)
                listeOverDager.push({
                    fom: vedtaksperiodeFom,
                    tom: previousDay(tempFom),
                    type: 'syk',
                })
                listeOverDager.push({
                    fom: agpFom,
                    tom: agpTom,
                    type: 'arbeidsgiverperiode',
                })
                vedtaksperiodeFom = nextDay(tempTom)

            } else if (agpFom.getTime() == vedtaksperiodeFom.getTime()) {
                const tempTom = new Date(agpTom)
                listeOverDager.push({
                    fom: agpFom,
                    tom: agpTom,
                    type: 'arbeidsgiverperiode',
                })
                vedtaksperiodeFom = nextDay(tempTom)
            } else {
                // Startdatoet, ligger i en annen vedtaksperiode
                if(agpTom.getTime() >= vedtaksperiodeFom.getTime()){
                    const tempTom = new Date(agpTom)
                    listeOverDager.push({
                        fom: vedtaksperiodeFom,
                        tom: agpTom,
                        type: 'arbeidsgiverperiode',
                    })
                    vedtaksperiodeFom = nextDay(tempTom)
                }
                else {
                    // Arbeidsgiverperioden overlapper ikke i det heletatt med vedtaksperioden
                }
            }
        }
    })
    if (listeOverDager.length > 0 && listeOverDager[listeOverDager.length - 1].tom.getTime() < vedtaksperiodeTom.getTime()) {
        const tempTom = new Date(listeOverDager[listeOverDager.length - 1].tom)
        listeOverDager.push({
            fom: nextDay(tempTom),
            tom: vedtaksperiodeTom,
            type: 'syk',
        })
    }

    if (listeOverDager.length == 0) {
        listeOverDager.push({
            fom: new Date(vedtak.fom),
            tom: new Date(vedtak.tom),
            type: 'syk',
        })
    }
    return listeOverDager
}
