import React from 'react'
import './App.css'
import {Søk} from "./Søk";
import {PersonDto} from "./external/dto";
import {Person} from "./Person";

export const App = React.memo(() => {
    const [person, setPerson] = React.useState<PersonDto | undefined>(undefined)
    return (
        <div className="App">
            <header className="App-header">
                <Søk setPerson={setPerson}/>
                {person && <Person person={person}/>}
            </header>
        </div>
    )
})
