import { Backend } from './backend'
import {MeldingDto, PersonDto} from '../state/dto'
import { feilVedDårligRespons, wrapNnettverksFeil } from './feil'

export const restBackend = (development: boolean): Backend => {
    const baseUrl: string =
        development ? "http://localhost:8080" : "";
    return {
        personForAktørId(aktørId: string): Promise<PersonDto> {
        return fetch(`${baseUrl}/api/person/`, {
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
        return fetch(`${baseUrl}/api/person/`, {
            method: 'get',
            headers: {
                Accept: 'application/json',
                fnr: `${fnr}`
            }
        })
            .catch(wrapNnettverksFeil)
            .then(feilVedDårligRespons)
            .then(response => response.json())
    },
        hendelseForRef(meldingsreferanse: string): Promise<MeldingDto>  {
            return fetch(`${baseUrl}/api/hendelse/${meldingsreferanse}`, {
                method: 'get',
                headers: {
                    Accept: 'application/json',
                }
            })
                .catch(wrapNnettverksFeil)
                .then(feilVedDårligRespons)
                .then(response => response.json())
        }
    }
}


