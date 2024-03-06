import React, {useState} from "react";
import {usePerson} from "../../state/contexts";
import {add, sub} from "date-fns";
import {Timeline} from "@navikt/ds-react";
import styles from "./Tidslinjer.module.css";
import {BriefcaseIcon, Buldings3Icon, PackageIcon, ParasolBeachIcon, PiggybankIcon} from "@navikt/aksel-icons";


type TidslinjeState = {
    endDate: Date,
    startDate: Date,
    currentZoom: number
}

const Zoom = ({ step, onClick }: { step: number, onClick: (step: number) => void }) => {
    return <button onClick={() => { onClick(step) }}>Zoom {step} mnd</button>
}

export const Tidslinjer = ({valgteTing, toggleValgtTing}: {
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
}) => {
    const person = usePerson()
    const nyestePeriode = person.arbeidsgivere
        .filter((it) => it.vedtaksperioder.length > 0)
        .map((it) => {
            const sorterte = it.vedtaksperioder.sort((a, b) => (new Date(b.tom).getTime() - new Date(a.tom).getTime()))
            return sorterte[0]
        })
        .sort((a, b) => (new Date(b.tom).getTime() - new Date(a.tom).getTime()))

    const nå = nyestePeriode.length > 0 ? new Date(nyestePeriode[0].tom) : new Date()
    const [tidslinjeperiode, setTidslinjeperiode] = useState({
        endDate: add(nå, {months: 1}),
        startDate: sub(nå, {months: 11}),
        currentZoom: 12
    } as TidslinjeState)
    const [skalViseInfotrygdUtvalg, setSkalViseInfotrygdUtvalg] = useState(false)
    const [infotrygdhistorikkElementIndex, setInfotrygdhistorikkElementIndex] = useState(0)

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


    return (<div className="min-w-[800px]">
        <Timeline className={styles.tidslinje} direction={"right"} startDate={tidslinjeperiode.startDate}
                  endDate={tidslinjeperiode.endDate}>
            {skjæringstidspunkter.map((it) => <Timeline.Pin date={new Date(it)}>
                <p>Skjæringstidspunkt: {it}</p>
            </Timeline.Pin>)}

            {person.arbeidsgivere
                .filter((arbeidsgiver) => arbeidsgiver.vedtaksperioder.length > 0)
                .map((arbeidsgiver) => {
                    return <Timeline.Row label={arbeidsgiver.organisasjonsnummer} icon={<BriefcaseIcon aria-hidden/>}
                                         className={styles.tidslijerad}>
                        {arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => {
                            const erValgt = typeof valgteTing.find((it) => it == vedtaksperiode.id) !== 'undefined'
                            const klassenavn = erValgt ? styles.aktiv : undefined
                            return <Timeline.Period key={vedtaksperiode.id} start={new Date(vedtaksperiode.fom)}
                                                    end={new Date(vedtaksperiode.tom)} status={statusForVedtaksperiode(vedtaksperiode.tilstand)}
                                                    className={ klassenavn }
                                                    onSelectPeriod={(e) => {
                                                        toggleValgtTing(e, vedtaksperiode.id)
                                                    }}>
                                <div>{vedtaksperiode.fom} - {vedtaksperiode.tom} : {vedtaksperiode.tilstand}</div>
                            </Timeline.Period>
                        })}
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
        {skalViseInfotrygdUtvalg && (<>
            <p>Hvilken Infotrygdhistorikk vil du basere visningen på?</p>
            <select onChange={(e) => {
                setInfotrygdhistorikkElementIndex(e.target.selectedIndex)
                setSkalViseInfotrygdUtvalg(false)
            }}>{person.infotrygdhistorikk.map((a, i) => <option value={i} selected={i == infotrygdhistorikkElementIndex}>index {i}</option>)}</select>
        </>)
        }
    </div>);
}

function statusForVedtaksperiode(tilstand: string): "success" | "warning" | "danger" | "info" | "neutral" {
    if (tilstand == "AVSLUTTET") return "success"
    if (tilstand == "AVSLUTTET_UTEN_UTBETALING") return "neutral"
    if (tilstand in ["AVVENTER_GODKJENNING", "AVVENTER_GODKJENNING_REVURDERING"]) return "warning"
    return "info"
}