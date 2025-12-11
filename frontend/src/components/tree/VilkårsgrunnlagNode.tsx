import styles from "./PersonTree.module.css";
import SelectableTreeNode from "./SelectableTreeNode";
import React from "react";
import {VilkårsgrunnlaghistorikkDto} from "../../state/dto";

interface VilkårsgrunnlagNodeProps {
    vilkårsgrunnlagHistorikkInnslag: VilkårsgrunnlaghistorikkDto,
    valgteTing: string[],
    toggleValgtTing: (e: React.MouseEvent | React.KeyboardEvent, ting: string) => void
}

export const VilkårsgrunnlagHistorikkNode = ({ vilkårsgrunnlagHistorikkInnslag, valgteTing, toggleValgtTing } : VilkårsgrunnlagNodeProps) => {
    return (
        <>
            {vilkårsgrunnlagHistorikkInnslag.vilkårsgrunnlag.map((vilkårsgrunnlag) => (
                <SelectableTreeNode className={styles.VilkårsgrunnlagNode}
                                    indent={0}
                                    valgteTing={valgteTing}
                                    ting={vilkårsgrunnlag.vilkårsgrunnlagId}
                                    vedValg={toggleValgtTing}
                                    key={vilkårsgrunnlag.vilkårsgrunnlagId}>
                    <span key={vilkårsgrunnlag.vilkårsgrunnlagId}>{`📍${vilkårsgrunnlag.skjæringstidspunkt}`} </span>
                </SelectableTreeNode>
            ))
            }
        </>
    )
}

