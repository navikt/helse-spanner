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
                </Routes>
            </Page>
        </BrowserRouter>
    )
}

App.displayName = 'App'
