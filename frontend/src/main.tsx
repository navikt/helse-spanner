import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import {App} from './components/App'
import {Environment} from './external/environment'
import {hardCodedBackend} from './external/jsonBackend'
import {restBackend} from './external/restBackend'
import {BackendContext} from './external/backend'
import {QueryClient, QueryClientProvider} from 'react-query'
import {testBackend} from "./external/testBackend";
import {finnesIkke} from "./external/feil";
import {RecoilRoot} from "recoil";


const backend = Environment.isDevelopment ? hardCodedBackend : restBackend
// let backend = testBackend([], {"40": new finnesIkke})

const queryClient = new QueryClient()

ReactDOM.render(
    <React.StrictMode>
        <RecoilRoot>
            <BackendContext.Provider value={backend}>
                <QueryClientProvider client={queryClient}>
                    <App/>
                </QueryClientProvider>
            </BackendContext.Provider>
        </RecoilRoot>
    </React.StrictMode>,
    document.getElementById('root')
)
