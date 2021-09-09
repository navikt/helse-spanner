import React from "react";
import {BackendContext} from "./external/backend";
import {hardCodedBackend} from "./external/jsonBackend";
import {QueryClient, QueryClientProvider} from "react-query";
import {App} from "./App";

const queryClient = new QueryClient()

export const TestApp = () =>
    <React.StrictMode>
        <BackendContext.Provider value={hardCodedBackend}>
            <QueryClientProvider client={queryClient}>
                <App />
            </QueryClientProvider>
        </BackendContext.Provider>
    </React.StrictMode>