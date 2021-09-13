import { Backend } from './backend'
import { PersonDto } from './dto'
import { feilVedDårligRespons, wrapNnettverksFeil } from './feil'

export const restBackend: Backend = {
    personForAktørId(aktørId: string): Promise<PersonDto> {
        return fetch(`${baseUrl}/personer/`, {
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
        return fetch(`${baseUrl}/personer/`, {
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
const baseUrl = 'http://localhost:8080/api'
