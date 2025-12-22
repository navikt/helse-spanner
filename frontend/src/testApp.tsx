import React from 'react'
import { Backend, BackendContext } from './external/backend'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { App } from './components/App'
import { PersonDto } from './state/dto'
import testingLibrary from '@testing-library/react'
import { testBackend } from './external/testBackend'

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            retryDelay: 0,
            retry: false,
        },
    },
})

type TestAppProps = {
    backend: Backend
}

export const TestApp = (props: TestAppProps) => (
    <React.StrictMode>
        <BackendContext.Provider value={props.backend}>
            <QueryClientProvider client={queryClient}>
                <App />
            </QueryClientProvider>
        </BackendContext.Provider>
    </React.StrictMode>
)

export const testApp = (testPersoner: PersonDto[] = [], errorPersoner: Record<string, Error> = {}) => {
    let backend = testBackend(testPersoner, errorPersoner)
    return testingLibrary.render(<TestApp backend={backend} />)
}
