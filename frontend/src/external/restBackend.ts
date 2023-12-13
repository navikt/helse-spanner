import {Backend} from './backend'
import {MaskertDto, MeldingDto, PersonDto} from '../state/dto'
import {feilVedDårligRespons, wrapNnettverksFeil} from './feil'

export const restBackend = (development: boolean): Backend => {
    const baseUrl: string = development ? 'http://localhost:8080' : ''
    return {
        uuidForAktørId(aktørId: string): Promise<MaskertDto> {
            return fetchUUID(baseUrl, {aktorId: `${aktørId}` })
        },
        uuidForFnr(fnr: string): Promise<MaskertDto> {
            return fetchUUID(baseUrl, { fnr: `${fnr}` })
        },
        personForUUID(maskertId: string): Promise<PersonDto> {
            return fetch(`${baseUrl}/api/person/`, {
                method: 'get',
                headers: {
                    Accept: 'application/json',
                    maskertId: `${maskertId}`,
                },
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

const fetchUUID = (baseUrl: string, headervalue: Record<string,string>): Promise<MaskertDto> => {
    return fetch(`${baseUrl}/api/uuid/`, {
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