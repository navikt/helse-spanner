import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import { App } from './components/App'
import { Environment } from './external/environment'
import { hardCodedBackend } from './external/jsonBackend'
import { restBackend } from './external/restBackend'
import { BackendContext } from './external/backend'
import * as query from 'react-query'
import { RecoilRoot } from 'recoil'

const backend = Environment.isDevelopment ? hardCodedBackend : restBackend(false)
// let backend = testBackend([], {"40": new finnesIkke})

const queryClient = new query.QueryClient({
    defaultOptions: {
        queries: {
            retryDelay: 500,
            retry: 1,
            refetchOnWindowFocus: false,
            cacheTime: 0
        },
    },
})

ReactDOM.render(
    <React.StrictMode>
        <RecoilRoot>
            <BackendContext.Provider value={backend}>
                <query.QueryClientProvider client={queryClient}>
                    <App />
                </query.QueryClientProvider>
            </BackendContext.Provider>
        </RecoilRoot>
    </React.StrictMode>,
    document.getElementById('root')
)
