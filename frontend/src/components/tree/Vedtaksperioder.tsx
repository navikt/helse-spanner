import {
    ForkastetVedtaksperiodeContext,
    useArbeidsgiver, useForkastetVedtaksperiode,
    usePerson,
    useVedtak,
    VedtakContext
} from "../../state/contexts";
import parseISO from "date-fns/parseISO";
import compareAsc from "date-fns/compareAsc";
import React from "react";
import {somNorskDato} from "../i18n";
import SelectableTreeNode from "./SelectableTreeNode";
import styles from "./PersonTree.module.css";
import SporingLenke from "./SporingLenke";
import {tilstandsmaskinSporingUrl} from "./links";
import KopierVedtaksperiodePåminnelseJson from "./KopierVedtaksperiodePåminnelseJson";
import {useRecoilValue} from "recoil";
import {hideForkastedeVedtakState} from "../../state/state";
import classNames from "classnames";

export const Vedtaksperioder = () => {
    const arbeidsgiver = useArbeidsgiver()
    let vedtaksperioder: [JSX.Element, Date][] = arbeidsgiver.vedtaksperioder.map((vedtak) => [
        <VedtakContext.Provider value={vedtak} key={vedtak.id}>
            <VedtaksNode/>
        </VedtakContext.Provider>,
        parseISO(vedtak.fom),
    ])
    let forkastedeVedtaksperioder: [JSX.Element, Date][] = arbeidsgiver.forkastede.map((forkastelse) => [
        <ForkastetVedtaksperiodeContext.Provider value={forkastelse.vedtaksperiode} key={forkastelse.vedtaksperiode.id}>
            <ForkastetVedtaksNode/>
        </ForkastetVedtaksperiodeContext.Provider>,
        parseISO(forkastelse.vedtaksperiode.fom),
    ])
    const sorterteVedtak = [...vedtaksperioder, ...forkastedeVedtaksperioder].sort(([_, a], [ignore, b]) =>
        compareAsc(a, b)
    )
    return <>{sorterteVedtak.map(([vedtak]) => vedtak)}</>
}
Vedtaksperioder.displayName = 'Vedtaksperioder'


const VedtaksNode = () => {
    const vedtak = useVedtak()
    const [fom, tom] = [vedtak.fom, vedtak.tom].map(somNorskDato)
    return (
        <SelectableTreeNode indent={1.2} className={styles.LøvNode}>
            <div className={styles.VedtakNodeHeader}>
                <span>
                    {fom} - {tom}
                </span>
                <SporingLenke url={tilstandsmaskinSporingUrl(vedtak.id)} />
                <KopierVedtaksperiodePåminnelseJson person={usePerson()} arbeidsgiver={useArbeidsgiver()} vedtak={vedtak} />
            </div>
            <span className={styles.TilstandText}>{vedtak.tilstand}</span>
        </SelectableTreeNode>
    )
}
VedtaksNode.displayName = 'VedtaksNode'

const ForkastetVedtaksNode = () => {
    const hideForkastedeVedtak = useRecoilValue(hideForkastedeVedtakState)
    if (hideForkastedeVedtak) {
        return null
    }
    const vedtak = useForkastetVedtaksperiode()
    const [fom, tom] = [vedtak.fom, vedtak.tom].map(somNorskDato)
    return (
        <SelectableTreeNode className={classNames(styles.Forkastet, styles.LøvNode)} indent={1.2}>
            <div className={styles.ForkastetLabel}>
                {fom} - {tom}
            </div>
            <span className={styles.TilstandText}>{vedtak.tilstand}</span>
        </SelectableTreeNode>
    )
}
ForkastetVedtaksNode.displayName = 'ForkastetVedtaksNode'