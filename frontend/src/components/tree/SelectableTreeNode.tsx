import classNames from "classnames";
import styles from "./PersonTree.module.css";
import React from "react";
import {fargeForTing} from "../content/ContentCategory";

interface SelectableTreeNodeProps extends React.HTMLAttributes<HTMLDivElement> {
    indent: number,
    valgteTing: string[],
    ting: string,
    vedValg: (e: React.MouseEvent, ting: string) => void
}

export default function SelectableTreeNode({className, indent = 0, valgteTing, ting, vedValg = () => {}, children, ...rest}: SelectableTreeNodeProps) {
    const farge = fargeForTing(valgteTing, ting)
    return (
        <div
            style={{ marginLeft: `${indent * 0.9}em`, background: farge, cursor: 'pointer' }}
            className={classNames(styles.TreeNode, !!farge && styles.Highlighted, className)}
            onClick={(e) => {
                if (e.target instanceof HTMLAnchorElement) return
                vedValg(e, ting)
            }}
            {...rest}
        >
            {children}
        </div>
    )
}

SelectableTreeNode.displayName = 'SelectableTreeNode'
