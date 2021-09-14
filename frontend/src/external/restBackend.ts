import { Backend } from './backend'
import { PersonDto } from '../state/dto'
import { feilVedDårligRespons, wrapNnettverksFeil } from './feil'
import {Environment} from "./environment";

export const restBackend: Backend = {
    personForAktørId(aktørId: string): Promise<PersonDto> {
        return fetch(`${baseUrl}/api/personer/`, {
            method: 'get',
            headers: {
                Accept: 'application/json',
                aktorId: `${aktørId}`
            }
        })
            .catch(wrapNnettverksFeil)
            .then(feilVedDårligRespons)
            .then(response => response.json())
    },
    personForFnr(fnr: string): Promise<PersonDto> {
        return fetch(`${baseUrl}/api/personer/`, {
            method: 'get',
            headers: {
                Accept: 'application/json',
                fnr: `${fnr}`
            }
        })
            .catch(wrapNnettverksFeil)
            .then(feilVedDårligRespons)
            .then(response => response.json())
    }
}

const baseUrl: string =
    Environment.isDevelopment ? "http://localhost:8080" : "";
