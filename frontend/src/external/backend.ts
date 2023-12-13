import {MaskertDto, MeldingDto, PersonDto} from '../state/dto'
import {createContext, useContext} from '../state/contexts'

export type Backend = {
    uuidForFnr: (fnr: string) => Promise<MaskertDto>
    uuidForAktørId: (fnr: string) => Promise<MaskertDto>
    personForUUID: (maskertId: string) => Promise<PersonDto>
    hendelseForRef: (meldingsreferanse: string) => Promise<MeldingDto>
}

export const BackendContext = createContext<Backend>()

export const useBackend = () => useContext(BackendContext)

export const maskertRequestFactory = (personId: string, backend: Backend) => {
    if (personId.length == 11) {
        return backend.uuidForFnr(personId)
    } else if (personId.length == 13 || personId.length == 2) {
        return backend.uuidForAktørId(personId)
    } else {
        throw Error('Ikke gyldig fnr eller aktør-id')
    }
}
