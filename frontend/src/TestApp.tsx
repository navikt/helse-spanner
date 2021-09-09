import React from "react";
import {Backend, BackendContext} from "./external/backend";
import {QueryClient, QueryClientProvider} from "react-query";
import {App} from "./App";
import {PersonDto} from "./external/dto";
import TestingLibrary from '@testing-library/react';
import fn = jest.fn;

const queryClient = new QueryClient()


type TestAppProps = {
    backend: Backend
}

export const TestApp = (props: TestAppProps) =>
    <React.StrictMode>
        <BackendContext.Provider value={props.backend}>
            <QueryClientProvider client={queryClient}>
                <App />
            </QueryClientProvider>
        </BackendContext.Provider>
    </React.StrictMode>


export const testApp = (testPersoner: PersonDto[] = [], errorPersoner: Record<string, Error> = {}) => {
    let backend = testBackend(testPersoner, errorPersoner)
    return TestingLibrary.render(<TestApp backend={backend} />)
}



export let testBackend = (testPersoner: PersonDto[] = [], errorPersoner: Record<string, Error> = {}): Backend => {
    let aktorPersoner: Record<string, PersonDto> = testPersoner.reduce( (old, person) => {
        return {
            ...old,
            [person.aktørId]: person
        }
    }, {})

    let fnrPersoner: Record<string, PersonDto> = testPersoner.reduce( (old, person) => {
        return {
            ...old,
            [person.fødselsnummer]: person
        }
    }, {})

    return  {
        personForAktørId(aktørId: string): Promise<PersonDto> {
            let person = aktorPersoner[aktørId]
            if (!person) {
                let testError = errorPersoner[aktørId]
                if (!testError) {
                    throw Error(`No test fixture with ${aktørId}`)
                }
                return Promise.reject(testError)
            }
            return Promise.resolve(person)
        },

        personForFnr(fnr: string): Promise<PersonDto> {
            let person = fnrPersoner[fnr]
            if (!!person) {
                let person = errorPersoner[fnr]
                if (!!person) {
                    throw Error(`No test fixture with ${fnr}`)
                }
                return Promise.resolve(person)
            }
            return Promise.resolve(person)
    },
}}