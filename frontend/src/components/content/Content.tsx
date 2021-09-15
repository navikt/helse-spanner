import React from 'react'
import { JsonView } from './JsonView'
import {AktivitetsloggView} from "./AktivitetsloggView";
import {useRecoilState} from 'recoil'
import {displayViewState} from '../../state/state'
import classNames from 'classnames'
import styles from './Content.module.css'


export const Content = React.memo(() => {


    return (
        <div>
            <div><ViewButton name={"Json"} /><ViewButton name={"Aktiviteter"} /></div>
            <JsonView />
            <AktivitetsloggView/>
        </div>
    )
})

const ViewButton: React.FC<{name: string}> = React.memo((props) => {
    const [useDisplayView, setDisplayView] = useRecoilState(displayViewState)
    const isSelected = useDisplayView.includes(props.name)

    return (
        <button className={ classNames(isSelected && styles.ViewButtonSelected)}  onClick={() => setDisplayView([props.name])}>{props.name}</button>
    )
})



