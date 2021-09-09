import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import { App } from './App'
import { Environment } from './external/environment'
import { hardCodedBackend } from './external/jsonBackend'
import { restBackend } from './external/restBackend'
import { BackendContext } from './external/backend'
import {QueryClient, QueryClientProvider} from 'react-query'

const backend = Environment.isDevelopment ? hardCodedBackend : restBackend

const queryClient = new QueryClient()

ReactDOM.render(
  <React.StrictMode>
    <BackendContext.Provider value={backend}>
      <QueryClientProvider client={queryClient}>
        <App />
      </QueryClientProvider>
    </BackendContext.Provider>
  </React.StrictMode>,
  document.getElementById('root')
)
