import {usePerson} from "../../state/contexts";
import parseISO from "date-fns/parseISO";
import compareAsc from "date-fns/compareAsc";
import React, {useState} from "react";
import {somNorskDato} from "../i18n";
import SelectableTreeNode from "./SelectableTreeNode";
import styles from "./PersonTree.module.css";
import SporingLenke from "./SporingLenke";
import {tilstandsmaskinSporingUrl} from "./links";
import KopierVedtaksperiodePåminnelseJson from "./KopierVedtaksperiodePåminnelseJson";
import classNames from "classnames";
import {
    ArbeidsgiverDto,
    BehandlingDto,
    DagDto,
    EndringDto,
    FokastetVedtaksperiodeDto, SykdomstidslinjeDto,
    VedtakDto
} from "../../state/dto";
import KopierAnmodningOmForkastingJson from "./KopierAnmodningOmForkastingJson";


interface VedtaksperioderProps {
    arbeidsgiver: ArbeidsgiverDto,
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
    visForkastede: boolean
    visBehandlinger: boolean
}

export const Vedtaksperioder = ({ arbeidsgiver, valgteTing, visForkastede, visBehandlinger, toggleValgtTing } : VedtaksperioderProps) => {
    let vedtaksperioder: [JSX.Element, Date][] = arbeidsgiver.vedtaksperioder.map((vedtak) => [
        <VedtaksNode key={vedtak.id} vedtak={vedtak} organisasjonsnummer={arbeidsgiver.organisasjonsnummer} visBehandlinger={visBehandlinger} valgteTing={valgteTing} vedValg={ toggleValgtTing }/>,
        parseISO(vedtak.fom),
    ])
    let forkastedeVedtaksperioder: [JSX.Element, Date][] = []
    if (visForkastede) {
        forkastedeVedtaksperioder = arbeidsgiver.forkastede.map((forkastelse) => [
            <ForkastetVedtaksNode key={forkastelse.vedtaksperiode.id} vedtak={forkastelse.vedtaksperiode} valgteTing={valgteTing} vedValg={ toggleValgtTing }/>,
            parseISO(forkastelse.vedtaksperiode.fom),
        ])
    }
    const sorterteVedtak = [...vedtaksperioder, ...forkastedeVedtaksperioder].sort(([_, a], [ignore, b]) =>
        compareAsc(a, b)
    )
    return <>{sorterteVedtak.map(([vedtak]) => vedtak)}</>
}
Vedtaksperioder.displayName = 'Vedtaksperioder'


const VedtaksNode = ({ vedtak, organisasjonsnummer, visBehandlinger, valgteTing, vedValg }: { vedtak: VedtakDto, organisasjonsnummer: string, visBehandlinger: Boolean, valgteTing: string[], vedValg: (e: React.MouseEvent, ting: string) => void }) => {
    const [fom, tom] = [vedtak.fom, vedtak.tom].map(somNorskDato)
    return (
        <div>
            <SelectableTreeNode valgteTing={valgteTing} ting={vedtak.id} indent={1.2} className={styles.LøvNode}
                                vedValg={vedValg}>
                <div className={styles.VedtakNodeHeader}>
                <span>
                    {fom} - {tom}
                </span>
                    <div className={styles.Knapper}>
                        <SporingLenke url={tilstandsmaskinSporingUrl(vedtak.id)}/>
                        <KopierVedtaksperiodePåminnelseJson person={usePerson()}
                                                            organisasjonsnummer={organisasjonsnummer} vedtak={vedtak}/>
                        {vedtak.tilstand == "AVSLUTTET_UTEN_UTBETALING" ?
                            <KopierAnmodningOmForkastingJson person={usePerson()}
                                                             organisasjonsnummer={organisasjonsnummer}
                                                             vedtak={vedtak}/> : null}
                    </div>
                </div>
                <span className={styles.TilstandText}>{vedtak.tilstand}</span>
            </SelectableTreeNode>
            {visBehandlinger && vedtak.behandlinger?.map((behandling: BehandlingDto, index) =>
                <BehandlingsNode key={behandling.id} behandling={behandling} valgteTing={valgteTing} vedValg={vedValg}/>)}
        </div>
    )
}
VedtaksNode.displayName = 'VedtaksNode'

const ForkastetVedtaksNode = ( { vedtak, valgteTing, vedValg } : { vedtak: FokastetVedtaksperiodeDto, valgteTing: string[], vedValg: (e: React.MouseEvent, ting: string) => void }) => {
    const [fom, tom] = [vedtak.fom, vedtak.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.Forkastet, styles.LøvNode)} indent={1.2} valgteTing={valgteTing} ting={vedtak.id} vedValg={vedValg }>
            <div className={styles.ForkastetLabel}>
                {fom} - {tom}
            </div>
            <span className={styles.TilstandText}>{vedtak.tilstand}</span>
        </SelectableTreeNode>
    )
}
ForkastetVedtaksNode.displayName = 'ForkastetVedtaksNode'

const BehandlingsNode = ({behandling, valgteTing, vedValg}: {
    behandling: BehandlingDto,
    valgteTing: string[],
    vedValg: (e: React.MouseEvent, ting: string) => void
}) => {
    return (
        <div>
            <SelectableTreeNode key={behandling.id} indent={2.2} valgteTing={valgteTing} ting={behandling.id } className={styles.BehandlingNode}
                                vedValg={vedValg}>
                <span className={styles.TilstandText}>{behandling.tilstand}</span>
            </SelectableTreeNode>
            {behandling.endringer?.map((endring: EndringDto) =>
                <Endringsnode key={endring.id} endring={endring} valgteTing={valgteTing} vedValg={vedValg}/>
            )}
        </div>
    )
}
BehandlingsNode.displayName = 'Behandlinger'

const Endringsnode = ({endring, valgteTing, vedValg}: {
    endring: EndringDto,
    valgteTing: string[],
    vedValg: (e: React.MouseEvent, ting: string) => void
}) => {
    const [hover, setHover] = useState(false)
    const sykdomstidslinje: string = sykdomstidslinjeShortString(endring.sykdomstidslinje)
    const dato: string = somNorskDato(endring.tidsstempel)
    const emoji = medEmoji(sykdomstidslinje)
    return (
        <div onMouseOver={() => setHover(true)} onMouseLeave={() => setHover(false)}>
            <SelectableTreeNode indent={2.7} valgteTing={valgteTing} ting={endring.id} vedValg={vedValg} className={styles.EndringNode}>
                {!hover && <span className={styles.SykdomstidslinjeText}>{sykdomstidslinje + " (" + dato + ")  " + emoji}</span>}
                {hover && <span className={styles.SykdomstidslinjeTextHover}>{sykdomstidslinje + " (" + dato + ")  " + emoji}</span>}
            </SelectableTreeNode>
        </div>
    )
}
Endringsnode.displayName = 'Endringer'

function sykdomstidslinjeShortString(tidslinje: SykdomstidslinjeDto): string {
    var sykdomstidslinje = ""
    var ukjentTidslinje = false
    if (tidslinje.dager.length == 0) return "Tom tidslinje"
    tidslinje.dager.forEach((dag: DagDto) => {
            const antallDager: number = dagerMellom(dag.fom, dag.tom, dag.dato)
            for (let i=0; i<=antallDager; i++) {
                var shortChar: string | null = toShortChar(dag.type)
                if (shortChar == null){
                    console.log("har ikke fungerende mapping for sykdosmtidslinjedag ", dag.type)
                    ukjentTidslinje = true
                }
                const søndag = erSøndag(dag, i)
                if (søndag) shortChar += " "
                sykdomstidslinje += shortChar
            }
        }
    )
    return ukjentTidslinje ? "Ukjent sykdomstidslinje for spanner🤕 (se console)" : sykdomstidslinje
}

function medEmoji(tidslinje: string): string {
    if (tidslinje.endsWith("A")) return "🏴"
    if (tidslinje.includes("F")) return "🏝️"
    if (tidslinje.includes("K")) return "️👵"
    return ""
}

function erSøndag(dag: DagDto, i: number): boolean {
    if (dag.dato !== null) return parseISO(dag.dato).getDay() == 0
    return addDays(parseISO(dag.fom!!), i).getDay() == 0
}

function addDays(date: Date, days: number) {
    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}

function dagerMellom(fom: string | null, tom: string | null, dagen: string | null): number {
    if (dagen !== null) return 0 // enkeltdag
    const fomDate = parseISO(fom!!)
    const tomDate = parseISO(tom!!)
    /**
     * Take the difference between the dates and divide by milliseconds per day.
     * Round to nearest whole number to deal with DST.
     */
    return Math.round((tomDate.valueOf() - fomDate.valueOf()) / (1000 * 60 * 60 * 24));
}

function toShortChar(dagtype: string): string | null {
    switch (dagtype) {
        case "SYKEDAG": return "S";
        case "ARBEIDSDAG": return "A"
        case "UKJENTDAG": return "?"
        case "PROBLEMDAG": return "X"
        case "SYK_HELGEDAG": return "H"
        case "ARBEIDSGIVERDAG": return "U"
        case "ARBEIDSGIVERHELGEDAG": return "G"
        case "FERIEDAG": return "F"
        case "ARBEID_IKKE_GJENOPPTATT_DAG": return "J"
        case "PERMISJONSDAG": return "P"
        case "FRISK_HELGEDAG": return "R"
        case "FORELDET_SYKEDAG": return "K"
        case "SYKEDAG_NAV": return "N"
        case "ANDRE_YTELSER": return "Y"
        case "ANDRE_YTELSER_FORELDREPENGER": return "Y"
        case "ANDRE_YTELSER_AAP": return "Y"
        case "ANDRE_YTELSER_OMSORGSPENGER": return "Y"
        case "ANDRE_YTELSER_PLEIEPENGER": return "Y"
        case "ANDRE_YTELSER_SVANGERSKAPSPENGER": return "Y"
        case "ANDRE_YTELSER_OPPLÆRINGSPENGER": return "Y"
        case "ANDRE_YTELSER_DAGPENGER": return "Y"
        default: return null
    }
}

