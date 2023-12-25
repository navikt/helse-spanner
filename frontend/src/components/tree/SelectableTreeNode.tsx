import {idEqual, useId, useIsSelected} from "../../state/contexts";
import classNames from "classnames";
import styles from "./PersonTree.module.css";
import React from "react";
import {useRecoilState} from "recoil";
import {selectedState} from "../../state/state";


const useSelect = () => {
    const [selected, setSelected] = useRecoilState(selectedState)
    const isSelected = useIsSelected()
    const id = useId()
    const toggleSelect = () => {
        if (!isSelected) setSelected([...selected, id])
        else setSelected(selected.filter((it) => !idEqual(it, id)))
    }
    const setOnlySelected = () => {
        setSelected([id])
    }
    return React.useMemo(
        () => (e: React.MouseEvent) => {
            if (e.ctrlKey || e.metaKey) toggleSelect()
            else setOnlySelected()
            e.stopPropagation()
        },
        [setSelected, id, selected]
    )
}

interface SelectableTreeNodeProps extends React.HTMLAttributes<HTMLDivElement> {
    indent: number
}

export default function SelectableTreeNode({className, indent = 0, children, ...rest}: SelectableTreeNodeProps) {
    const selected = useIsSelected()
    const select = useSelect()
    return (
        <div
            style={{marginLeft: `${indent * 0.9}em`, background: selected, cursor: 'pointer'}}
            className={classNames(styles.TreeNode, !!selected && styles.Highlighted, className)}
            onClick={select}
            {...rest}
        >
            {children}
        </div>
    )
}

SelectableTreeNode.displayName = 'SelectableTreeNode'