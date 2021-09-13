import React from 'react'
import { Søk } from './Søk'
import { PersonData } from './PersonData'
import { Header } from './Header'
import styles from './App.module.css'
import classNames from 'classnames'

export const App = React.memo(() => {
    const [aktørId, setAktørId] = React.useState<string | undefined>(undefined)
    return (
        <div className="App">
            <Header>
                <Søk setAktørId={setAktørId} />
            </Header>
            <div className={classNames(styles.App)}>{aktørId && <PersonData aktørId={aktørId} />}</div>
        </div>
    )
})
