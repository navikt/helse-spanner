import { MeldingDto, PersonDto } from '../state/dto'
import { createContext, useContext } from '../state/contexts'

export type Backend = {
    personForFnr: (fnr: string) => Promise<PersonDto>
    personForAktørId: (aktørId: string) => Promise<PersonDto>
    personForUUID: (maskertId: string) => Promise<PersonDto>
    hendelseForRef: (meldingsreferanse: string) => Promise<MeldingDto>
}

export const BackendContext = createContext<Backend>()

export const useBackend = () => useContext(BackendContext)

export const personRequestFactory = (personId: string, backend: Backend) => {
    if (personId.length == 11) {
        return () => backend.personForFnr(personId)
    } else if (personId.length == 13 || personId.length == 2) {
        return () => backend.personForAktørId(personId)
    } else if (personId.length == 36) {
        return () => backend.personForUUID(personId)
    } else {
        throw Error('Ikke gyldig fnr eller aktør-id')
    }
}
