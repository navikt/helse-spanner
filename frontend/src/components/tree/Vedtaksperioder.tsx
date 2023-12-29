import {usePerson} from "../../state/contexts";
import parseISO from "date-fns/parseISO";
import compareAsc from "date-fns/compareAsc";
import React from "react";
import {somNorskDato} from "../i18n";
import SelectableTreeNode from "./SelectableTreeNode";
import styles from "./PersonTree.module.css";
import SporingLenke from "./SporingLenke";
import {tilstandsmaskinSporingUrl} from "./links";
import KopierVedtaksperiodePåminnelseJson from "./KopierVedtaksperiodePåminnelseJson";
import classNames from "classnames";
import {ArbeidsgiverDto, FokastetVedtaksperiodeDto, VedtakDto} from "../../state/dto";


interface VedtaksperioderProps {
    arbeidsgiver: ArbeidsgiverDto,
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent, ting: string) => void
    visForkastede: boolean
}

export const Vedtaksperioder = ({ arbeidsgiver, valgteTing, visForkastede, toggleValgtTing } : VedtaksperioderProps) => {
    let vedtaksperioder: [JSX.Element, Date][] = arbeidsgiver.vedtaksperioder.map((vedtak) => [
        <VedtaksNode key={vedtak.id} vedtak={vedtak} organisasjonsnummer={arbeidsgiver.organisasjonsnummer} valgteTing={valgteTing} vedValg={ toggleValgtTing }/>,
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


const VedtaksNode = ({ vedtak, organisasjonsnummer, valgteTing, vedValg }: { vedtak: VedtakDto, organisasjonsnummer: string, valgteTing: string[], vedValg: (e: React.MouseEvent, ting: string) => void }) => {
    const [fom, tom] = [vedtak.fom, vedtak.tom].map(somNorskDato)
    return (
        <SelectableTreeNode valgteTing={valgteTing} ting={vedtak.id} indent={1.2} className={styles.LøvNode} vedValg={vedValg}>
            <div className={styles.VedtakNodeHeader}>
                <span>
                    {fom} - {tom}
                </span>
                <SporingLenke url={tilstandsmaskinSporingUrl(vedtak.id)} />
                <KopierVedtaksperiodePåminnelseJson person={usePerson()} organisasjonsnummer={organisasjonsnummer} vedtak={vedtak} />
            </div>
            <span className={styles.TilstandText}>{vedtak.tilstand}</span>
        </SelectableTreeNode>
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