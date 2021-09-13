import React from 'react'
import { Backend, BackendContext } from './external/backend'
import query from 'react-query'
import { App } from './components/App'
import { PersonDto } from './external/dto'
import testingLibrary from '@testing-library/react'
import { testBackend } from './external/testBackend'
import { RecoilRoot } from 'recoil'

const queryClient = new query.QueryClient({
    defaultOptions: {
        queries: {
            retryDelay: 0,
        },
    },
})
query.setLogger({
    log: () => {},
    warn: () => {},
    error: () => {},
})

type TestAppProps = {
    backend: Backend
}

export const TestApp = (props: TestAppProps) => (
    <React.StrictMode>
        <RecoilRoot>
            <BackendContext.Provider value={props.backend}>
                <query.QueryClientProvider client={queryClient}>
                    <App />
                </query.QueryClientProvider>
            </BackendContext.Provider>
        </RecoilRoot>
    </React.StrictMode>
)

export const testApp = (testPersoner: PersonDto[] = [], errorPersoner: Record<string, Error> = {}) => {
    let backend = testBackend(testPersoner, errorPersoner)
    return testingLibrary.render(<TestApp backend={backend} />)
}
