import React from 'react'
import { Søk } from './søk/Søk'
import { PersonData } from './person/PersonData'
import { Header } from './søk/Header'
import styles from './App.module.css'
import classNames from 'classnames'

export const App = () => {
    const [searchData, setSearchData] = React.useState<{ searchTerm: string | undefined, cacheBuster: number }>({ searchTerm: undefined, cacheBuster: 0 })
    const updateSearchTerms = (searchTerm: string | undefined) => setSearchData({ searchTerm, cacheBuster: Date.now() })
    return (
        <div className="App">
            <Header>
                <Søk onSearch={updateSearchTerms} />
            </Header>
            <div className={classNames(styles.App)}>{searchData.searchTerm && <PersonData personId={searchData.searchTerm} cacheBuster={searchData.cacheBuster} />}</div>
        </div>
    )
}

App.displayName="App"
