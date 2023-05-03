import React from 'react'
import { Søk } from './søk/Søk'
import { PersonData } from './person/PersonData'
import { Header } from './søk/Header'
import styles from './App.module.css'
import classNames from 'classnames'
import { BrowserRouter, Route, Routes } from 'react-router-dom'

const EmptyState = () => <></>

export const App = () => {
    return (
        <BrowserRouter>
            <Header>
                <Søk />
            </Header>
            <div className={classNames(styles.App)}>
                <Routes>
                    <Route path="/person/:personId" element={<PersonData />} />
                    <Route index path="/" element={<EmptyState />} />
                </Routes>
            </div>
        </BrowserRouter>
    )
}

App.displayName = 'App'
