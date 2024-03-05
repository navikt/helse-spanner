import React, {useEffect, useState} from 'react'
import {PersonHeader} from './PersonHeader'
import {PersonTree} from '../tree/PersonTree'
import {Content} from '../content/Content'
import {useResetRecoilState} from 'recoil'
import {åpneHendelseDokumentState} from '../../state/state'
import {usePerson} from '../../state/contexts'
import {Box, HGrid, Page, Timeline} from "@navikt/ds-react";
import {BriefcaseIcon, Buldings3Icon, PackageIcon, ParasolBeachIcon, PiggybankIcon,} from "@navikt/aksel-icons";
import {InfotrygdhistorikkDto} from "../../state/dto";
import {add, sub} from "date-fns";

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
                <Tidslinjer />
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

const Tidslinjer = () => {
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
        startDate: sub(nå, { months: 11 })
    })

    console.log(`current zoom is: `, tidslinjeperiode)

    return (<div className="min-w-[800px]">
        <Timeline direction={"right"} startDate={tidslinjeperiode.startDate} endDate={tidslinjeperiode.endDate} >
            {person.arbeidsgivere.map((arbeidsgiver) => {
                return <Timeline.Row label={ arbeidsgiver.organisasjonsnummer } icon={<BriefcaseIcon aria-hidden />}>
                    { arbeidsgiver.vedtaksperioder.map((vedtaksperiode) => {
                        let status = "info"
                        if (vedtaksperiode.tilstand == "AVSLUTTET") status = "success"
                        else if (vedtaksperiode.tilstand == "AVSLUTTET_UTEN_UTBETALING") status = "neutral"
                        else if (vedtaksperiode.tilstand in ["AVVENTER_GODKJENNING", "AVVENTER_GODKJENNING_REVURDERING"]) status = "warning"

                        // @ts-ignore
                        return <Timeline.Period key={vedtaksperiode.id} start={new Date(vedtaksperiode.fom)} end={new Date(vedtaksperiode.tom)} status={status}>
                            <div>{vedtaksperiode.fom} - {vedtaksperiode.tom} : { vedtaksperiode.tilstand}</div>
                        </Timeline.Period>
                    }) }
                </Timeline.Row>
            })}
            <Timeline.Row label="Infotrygd" icon={<PackageIcon aria-hidden />}>
                <Infotrygdperioder historikk={person.infotrygdhistorikk} />
            </Timeline.Row>
            <Timeline.Zoom>
                <button onClick={() => {
                    setTidslinjeperiode((current) => {
                        return {
                            endDate: add(current.endDate, { months: 6 }),
                            startDate: add(current.startDate, { months: 6 })
                        }
                    })
                }}>« fremover 6 mnd</button>
                <button onClick={() => {
                    setTidslinjeperiode((current) => {
                        return {
                            endDate: sub(current.endDate, { months: 6 }),
                            startDate: sub(current.startDate, { months: 6 })
                        }
                    })
                }}>tilbake 6 mnd »</button>
            </Timeline.Zoom>
        </Timeline>
    </div>);
}

const Infotrygdperioder = ({historikk}: { historikk: InfotrygdhistorikkDto[] }) => {
    if (historikk.length == 0) return <></>
    const siste = historikk[0]
    const ferie = siste.ferieperioder.map((it) => {
        return <Timeline.Period start={new Date(it.fom)} end={new Date(it.tom)} status="neutral" icon={<ParasolBeachIcon aria-hidden/>}>
            <div>{it.fom} - {it.tom}</div>
        </Timeline.Period>
    })
    const refusjon = siste.arbeidsgiverutbetalingsperioder.map((it) => {
        return <Timeline.Period start={new Date(it.fom)} end={new Date(it.tom)} status="neutral" icon={<Buldings3Icon aria-hidden />}>
            <div>{it.fom} - {it.tom}</div>
        </Timeline.Period>
    })
    const bruker = siste.personutbetalingsperioder.map((it) => {
        return <Timeline.Period start={new Date(it.fom)} end={new Date(it.tom)} status="neutral" icon={<PiggybankIcon aria-hidden />}>
            <div>{it.fom} - {it.tom}</div>
        </Timeline.Period>
    })
    return (<>
        { [...ferie, ...refusjon, ...bruker].map((it) => it) }
    </>)
}

PersonView.displayName = 'PersonView'
