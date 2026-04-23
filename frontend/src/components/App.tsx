import React, {useState} from 'react'
import {Søk} from './søk/Søk'
import {PersonData} from './person/PersonData'
import {Header} from './søk/Header'
import {BrowserRouter, Route, Routes, useNavigate} from 'react-router-dom'
import "@navikt/ds-css";
import {Page} from "@navikt/ds-react";
import {SpiskammersetView} from './content/spiskammerset/SpiskammersetView'

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
                    <Route path="/spiskammerset" element={<SpiskammersetView />} />
                </Routes>
            </Page>
        </BrowserRouter>
    )
}
App.displayName = 'App'

export const Hotkeys = () => {
    const [harTrykketOk   , setHarTrykketOk] = useState(false)
    const navigate = useNavigate();
    const gåTilbake = () => {
        navigate(-1)
    }
    return (
        <div>
            <div style={{display: "grid", margin: "5em", placeContent: "center"}}>
                <h1>Hotkeys / Keyboard shortcuts her i spanner.</h1>
                <h2>Visste du at følgende shortcuts eksisterer?</h2>
                <h3>B: Ekspander behandlinger med endringer</h3>
                <h3>K: Vis kilden/dokumentsporingen til behandlingene og endringene (krever at behandlingene allerede er
                    ekspandert)</h3>
            </div>
            <div style={{display: "grid", margin: "2em", placeContent: "center"}}>
                {!harTrykketOk && <button onClick={() => setHarTrykketOk(true)}>Ok</ button>}
                {harTrykketOk &&
                    <TilbakeKnapp onClose={gåTilbake}>
                        <div> Ok, den er grei</div>
                    </TilbakeKnapp>
                }
            </div>
        </div>)
}

const TilbakeKnapp= ({onClose, children}: {onClose: () => void, children: React.ReactNode}) => {
    return (
        <div>
            {children}
            <button onClick={onClose}>
                Gå tilbake
            </button>
        </div>
    );
}
