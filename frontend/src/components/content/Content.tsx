import React, { PropsWithChildren } from 'react'
import { JsonView } from './JsonView'
import { HendelseView } from './hendelser/HendelseView'
import { useRecoilState } from 'recoil'
import { ContentView, displayViewState } from '../../state/state'
import classNames from 'classnames'
import styles from './Content.module.css'
import { useIsOnlySelected, useIsSelected } from '../../state/contexts'
import { Card } from '../Card'

export const Content = React.memo(() => {
    return (
        <div>
            <div>
                <ViewButton view={ContentView.Json} />
                <ViewButton view={ContentView.Hendelser} />
            </div>
            <div className={classNames(styles.ContentCards)}>
                <JsonView />
                <HendelseView />
            </div>
        </div>
    )
})

const ViewButton: React.FC<{ view: ContentView }> = React.memo(({ view }) => {
    const [displayViews, setDisplayViews] = useRecoilState(displayViewState)
    const isSelected = displayViews.includes(view)

    const selectCategory = React.useMemo(
        () => (e: React.MouseEvent) => {
            const toggleSelect = () => {
                if (displayViews.includes(view)) {
                    setDisplayViews(displayViews.filter((it) => it !== view))
                } else {
                    setDisplayViews([...displayViews, view])
                }
            }
            const setOnlySelected = () => {
                setDisplayViews([view])
            }

            if (e.ctrlKey || e.metaKey) toggleSelect()
            else setOnlySelected()
            e.stopPropagation()
        },
        [displayViews, setDisplayViews]
    )

    return (
        <button className={classNames(isSelected && styles.ViewButtonSelected)} onClick={selectCategory}>
            {view}
        </button>
    )
})
export const ShowIfSelected: React.FC<PropsWithChildren<any>> = React.memo(({ children }) => {
    const selectedColor = useIsSelected()
    const onlySelected = useIsOnlySelected()
    if (!selectedColor) return null
    return (
        <Card
            style={{ borderStyle: onlySelected ? `solid` : 'none', borderWidth: '7px', borderColor: selectedColor }}
            className={classNames(styles.ContentCard)}
        >
            {children}
        </Card>
    )
})
