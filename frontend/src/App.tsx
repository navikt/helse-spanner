import React from 'react'
import './App.css'
import {Søk} from './Søk'
import {Person} from './Person'

export const App = React.memo(() => {
    const [aktørId, setAktørId] = React.useState<string | undefined>(undefined)
    return (
        <div className="App">
            <header className="App-header">
                <Søk setAktørId={setAktørId}/>
                {aktørId && <Person aktørId={aktørId}/>}
            </header>
        </div>
    )
})
