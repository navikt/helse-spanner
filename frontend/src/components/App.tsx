import React from 'react'
import { Søk } from './søk/Søk'
import { PersonData } from './person/PersonData'
import { Header } from './søk/Header'
import styles from './App.module.css'
import classNames from 'classnames'



export const App = React.memo(() => {
    const [personId, setPersonId] = React.useState<string | undefined>(undefined)
    return (
        <div className="App">
            <Header>
                <Søk setPersonId={setPersonId} />
            </Header>
            <div className={classNames(styles.App)}>{personId && <PersonData personId={personId} />}</div>
        </div>
    )
})
