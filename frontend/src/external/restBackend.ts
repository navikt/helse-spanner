import {Backend} from './backend'
import {MeldingDto, PersonDto, PersonPseudoIdDto} from '../state/dto'
import {feilVedDårligRespons, wrapNnettverksFeil} from './feil'

export const restBackend = (development: boolean): Backend => {
    const baseUrl: string = development ? 'http://localhost:8080' : ''
    return {
        personPseudoIdForAktørId(aktørId: string): Promise<PersonPseudoIdDto> {
            return fetchPersonPseudoId(baseUrl, {aktorId: `${aktørId}` })
        },
        personPseudoIdForFnr(fnr: string): Promise<PersonPseudoIdDto> {
            return fetchPersonPseudoId(baseUrl, { fnr: `${fnr}` })
        },
        personForPersonPseudoId(personPseudoId: string): Promise<PersonDto> {
            let headers: { [key: string]: string } = {
                Accept: 'application/json',
            }
            if (personPseudoId.length == 11) {
                headers = {...headers, fnr: personPseudoId }
            } else if (personPseudoId.length == 13) {
                headers = {...headers, aktorId: personPseudoId }
            } else {
                headers = {...headers, personPseudoId: personPseudoId }
            }

            return fetch(`${baseUrl}/api/person/`, {
                method: 'get',
                headers: headers,
            })
                .catch(wrapNnettverksFeil)
                .then(feilVedDårligRespons)
                .then((response) => response.json())
        },
        hendelseForRef(meldingsreferanse: string): Promise<MeldingDto> {
            return fetch(`${baseUrl}/api/hendelse/${meldingsreferanse}`, {
                method: 'get',
                headers: {
                    Accept: 'application/json',
                },
            })
                .catch(wrapNnettverksFeil)
                .then(feilVedDårligRespons)
                .then((response) => response.json())
        },
    }
}

const fetchPersonPseudoId = (baseUrl: string, headervalue: Record<string,string>): Promise<PersonPseudoIdDto> => {
    return fetch(`${baseUrl}/api/person-pseudo-id/`, {
        method: 'post',
        headers: {
            Accept: 'application/json',
            ...headervalue
        },
    })
        .catch(wrapNnettverksFeil)
        .then(feilVedDårligRespons)
        .then((response) => response.json())
}
