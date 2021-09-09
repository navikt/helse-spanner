import { Backend } from './backend'
import { PersonDto } from './dto'

export const restBackend: Backend = {
  personForAktørId(aktørId: string): Promise<PersonDto> {
    return fetch(`${baseUrl}/personer/`, {
      method: 'get',
      headers: {
        Accept: 'application/json',
        aktorId: `${aktørId}`,
      },
    }).then((response) => response.json())
  },
  personForFnr(fnr: string): Promise<PersonDto> {
    return fetch(`${baseUrl}/personer/`, {
      method: 'get',
      headers: {
        Accept: 'application/json',
        fnr: `${fnr}`,
      },
    }).then((response) => response.json())
  },
}
const baseUrl = 'http://0.0.0.0:8080/api'
