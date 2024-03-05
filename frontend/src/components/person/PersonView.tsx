import React, {useEffect, useState} from 'react'
import {PersonHeader} from './PersonHeader'
import {PersonTree} from '../tree/PersonTree'
import {Content} from '../content/Content'
import {useResetRecoilState} from 'recoil'
import {åpneHendelseDokumentState} from '../../state/state'
import {usePerson} from '../../state/contexts'
import {Box, HGrid, Page, Timeline} from "@navikt/ds-react";
import {BriefcaseIcon, Buldings3Icon, PackageIcon, ParasolBeachIcon, PiggybankIcon,} from "@navikt/aksel-icons";
import {add, sub} from "date-fns";
import styles from "./PersonView.module.css"

export const PersonView = () => {
    const resetÅpneHendelser = useResetRecoilState(åpneHendelseDokumentState)
    const person = usePerson()
    useEffect(() => {
        resetÅpneHendelser()
    }, [person])


    const [valgteTing, setValgteTing] = useState([person.aktørId])

    const toggleValgtTing = (e: React.MouseEvent, id: string) => {
        setValgteTing((previous) => {
            // fjern fra settet hvis iden er der fra før
            if (previous.includes(id)) return previous.filter((it) => it != id)
            // legg til iden i settet hvis ctrl/meta-key holdes inne
            if (e.ctrlKey || e.metaKey) return [...previous.filter((it) => it != id), id]
            return [id]
        })
    }

    return (<>
        <Box background="surface-alt-3-moderate" paddingBlock="5" paddingInline="8" as="header">
            <Page.Block>
                <PersonHeader />
                <Tidslinjer
                    valgteTing={valgteTing}
                    toggleValgtTing={toggleValgtTing}
                />
            </Page.Block>
        </Box>
        <Box
            background="bg-subtle"
            paddingBlock="10"
            paddingInline="8"
            as="main"
        >
            <Page.Block>
                <HGrid gap="6" columns="300px auto">
                    <PersonTree
                        valgteTing={valgteTing}
                        toggleValgtTing={toggleValgtTing}
                    />
                    <Content person={person} valgteTing={valgteTing} />
                </HGrid>
            </Page.Block>
        </Box>
    </>)
}

type TidslinjeState = {
    endDate: Date,
    startDate: Date,
    currentZoom: number
}

const Tidslinjer = ({ valgteTing, toggleValgtTing }: { valgteTing: string[], toggleValgtTing: (e: React.MouseEvent, ting: string) => void }) => {
    const person = usePerson()
    const nyestePeriode = person.arbeidsgivere
        .filter((it) => it.vedtaksperioder.length > 0)
        .map((it) => {
            const sorterte = it.vedtaksperioder.sort((a, b) => (new Date(b.tom).getTime() - new Date(a.tom).getTime()) )
            return sorterte[0]
        })
        .sort((a, b) => (new Date(b.tom).getTime() - new Date(a.tom).getTime()) )

    const nå = nyestePeriode.length > 0 ? new Date(nyestePeriode[0].tom) : new Date()
    const [tidslinjeperiode, setTidslinjeperiode] = useState({
        endDate: add(nå, { months: 1 }),
        startDate: sub(nå, { months: 11 }),
        currentZoom: 12
    } as TidslinjeState)
    const [skalViseInfotrygdUtvalg, setSkalViseInfotrygdUtvalg] = useState(false)
    const [infotrygdhistorikkElementIndex, setInfotrygdhistorikkElementIndex] = useState(0)

    const scrollStep = (tidslinjeperiode : TidslinjeState) => Math.max(1, Math.round(tidslinjeperiode.currentZoom / 3))
    const scroll = (tidslinjeperiode : TidslinjeState, strategy: (d: Date, dd: Duration) => Date): TidslinjeState => {
        return {
            endDate: strategy(tidslinjeperiode.endDate, { months: scrollStep(tidslinjeperiode) }),
            startDate: strategy(tidslinjeperiode.startDate, { months: scrollStep(tidslinjeperiode) }),
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

    console.log(`current zoom is: `, tidslinjeperiode)
    console.log(person.infotrygdhistorikk)
    return (<div className="min-w-[800px]">
        <Timeline className={styles.tidslinje} direction={"right"} startDate={tidslinjeperiode.startDate} endDate={tidslinjeperiode.endDate} >
            {person.arbeidsgivere.map((arbeidsgiver) => {
                return <Timeline.Row label={ arbeidsgiver.organisasjonsnummer } icon={<BriefcaseIcon aria-hidden />} className={styles.tidslijerad}>
                    { arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => {
                        let status = "info"
                        if (vedtaksperiode.tilstand == "AVSLUTTET") status = "success"
                        else if (vedtaksperiode.tilstand == "AVSLUTTET_UTEN_UTBETALING") status = "neutral"
                        else if (vedtaksperiode.tilstand in ["AVVENTER_GODKJENNING", "AVVENTER_GODKJENNING_REVURDERING"]) status = "warning"

                        // @ts-ignore
                        return <Timeline.Period key={vedtaksperiode.id} start={new Date(vedtaksperiode.fom)} end={new Date(vedtaksperiode.tom)} status={status} onSelectPeriod={(e) => {
                            toggleValgtTing(e, vedtaksperiode.id)
                        }}>
                            <div>{vedtaksperiode.fom} - {vedtaksperiode.tom} : { vedtaksperiode.tilstand}</div>
                        </Timeline.Period>
                    }) }
                </Timeline.Row>
            })}
            <Timeline.Row onClick={() => { setSkalViseInfotrygdUtvalg((førverdi) => !førverdi) } } label="Infotrygd" icon={<PackageIcon aria-hidden />} className={styles.tidslijerad}>
                { person.infotrygdhistorikk.length > 0 ? (
                    [...person.infotrygdhistorikk[infotrygdhistorikkElementIndex].ferieperioder.map((it) => {
                        return <Timeline.Period start={new Date(it.fom)} end={new Date(it.tom)} status="neutral" icon={<ParasolBeachIcon aria-hidden/>}>
                            <div>{it.fom} - {it.tom}</div>
                        </Timeline.Period>
                    }), ...person.infotrygdhistorikk[infotrygdhistorikkElementIndex].personutbetalingsperioder.map((it) => {
                        return <Timeline.Period start={new Date(it.fom)} end={new Date(it.tom)} status="success" icon={<PiggybankIcon aria-hidden />}>
                            <div>{it.fom} - {it.tom} - brukerutbetaling</div>
                        </Timeline.Period>
                    }), ...person.infotrygdhistorikk[infotrygdhistorikkElementIndex].arbeidsgiverutbetalingsperioder.map((it) => {
                        return <Timeline.Period start={new Date(it.fom)} end={new Date(it.tom)} status="success" icon={<Buldings3Icon aria-hidden />}>
                            <div>{it.fom} - {it.tom} - refusjon til { it.hasOwnProperty("orgnr") ? it.orgnr : "N/A" }</div>
                        </Timeline.Period>
                    }) ]
                ) : null}
            </Timeline.Row>
            <Timeline.Zoom>
                <Zoom step={12} onClick={ oppdaterZoom } />
                <Zoom step={6} onClick={ oppdaterZoom } />
                <Zoom step={2} onClick={ oppdaterZoom } />

                <button onClick={() => {
                    setTidslinjeperiode((current) => scroll(current, add))
                }}>« fremover { scrollStep(tidslinjeperiode) } mnd</button>
                <button onClick={() => {
                    setTidslinjeperiode((current) => scroll(current, sub))
                }}>tilbake { scrollStep(tidslinjeperiode) } mnd »</button>
            </Timeline.Zoom>
        </Timeline>
        { skalViseInfotrygdUtvalg && (<>
            <p>Hvilken Infotrygdhistorikk vil du basere visningen på?</p>
            <select onChange={(e) => {
                setInfotrygdhistorikkElementIndex(e.target.selectedIndex)
                setSkalViseInfotrygdUtvalg(false)
            }}>{person.infotrygdhistorikk.map((a, i) => <option value={i} selected={i == infotrygdhistorikkElementIndex }>index {i}</option>)}</select>
        </>)
        }
    </div>);
}

const Zoom = ({ step, onClick }: { step: number, onClick: (step: number) => void }) => {
    return <button onClick={() => { onClick(step) }}>Zoom {step} mnd</button>
}

PersonView.displayName = 'PersonView'
