import React from 'react'
import { JsonView } from './JsonView'
import { AktivitetsloggView } from './AktivitetsloggView'
import { useRecoilState } from 'recoil'
import { ContentView, displayViewState } from '../../state/state'
import classNames from 'classnames'
import styles from './Content.module.css'

export const Content = React.memo(() => {
    return (
        <div>
            <div>
                <ViewButton view={ContentView.Json} />
                <ViewButton view={ContentView.Hendelser} />
            </div>
            <JsonView />
            <AktivitetsloggView />
        </div>
    )
})

const ViewButton: React.FC<{ view: ContentView }> = React.memo(({ view }) => {
    const [displayViews, setDisplayViews] = useRecoilState(displayViewState)
    const isSelected = displayViews.includes(view)
    const toggleSelected = () => {
        if (isSelected) {
            setDisplayViews(displayViews.filter((it) => it !== view))
        } else {
            setDisplayViews([...displayViews, view])
        }
    }
    return (
        <button className={classNames(isSelected && styles.ViewButtonSelected)} onClick={toggleSelected}>
            {view}
        </button>
    )
})
