import React from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { App } from './components/App'
import { Environment } from './external/environment'
import { hardCodedBackend } from './external/jsonBackend'
import { restBackend } from './external/restBackend'
import { BackendContext } from './external/backend'
import * as query from '@tanstack/react-query'

const backend = Environment.isDevelopment ? hardCodedBackend : restBackend(false)

const queryClient = new query.QueryClient({
    defaultOptions: {
        queries: {
            retryDelay: 500,
            retry: (failureCount: number, error: any) => error.status != 404 && failureCount < 1,
            refetchOnWindowFocus: false,
            gcTime: 0,
        },
    },
})

const container = document.getElementById('root')
if (!container) throw new Error('Failed to find the root element')

const root = createRoot(container)
root.render(
    <React.StrictMode>
        <BackendContext.Provider value={backend}>
            <query.QueryClientProvider client={queryClient}>
                <App />
            </query.QueryClientProvider>
        </BackendContext.Provider>
    </React.StrictMode>
)
