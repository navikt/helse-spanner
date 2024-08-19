import React, {useState} from 'react'
import {Søk} from './søk/Søk'
import {PersonData} from './person/PersonData'
import {Header} from './søk/Header'
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import "@navikt/ds-css";
import {Alert, Box, ErrorSummary, Page} from "@navikt/ds-react";

const EmptyState = () => <></>

export const App = () => {
    return (
        <BrowserRouter>
            <Page>
                <Header>
                    <Søk />
                </Header>
                <Routes>
                    <Route path="/person/:personId" element={<PersonData />} />
                    <Route index path="/" element={<EmptyState />} />
                    <Route index path="/hotkeys" element={<Hotkeys />} />
                </Routes>
            </Page>
        </BrowserRouter>
    )
}

export const Hotkeys = () => {
    return (<div style={{display: "grid", margin: "5em", placeContent: "center"}}>
        <h1>Hotkeys / Keyboard shortcuts her i spanner.</h1>
        <h2>Visste du at følgende shortcuts eksisterer?</h2>
        <h3>B: Ekspander behandlinger med endringer</h3>
        <h3>K: Vis kilden/dokumentsporingen til behandlingene og endringene (krever at behandlingene allerede er ekspandert)</h3>
    </div>)
}

App.displayName = 'App'
