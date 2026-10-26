import {MeldingDto, PersonDto, PersonPseudoIdDto} from '../state/dto'
import {createContext, useContext} from '../state/contexts'

export type Backend = {
    personPseudoIdForFnr: (fnr: string) => Promise<PersonPseudoIdDto>
    personPseudoIdForAktørId: (fnr: string) => Promise<PersonPseudoIdDto>
    personForPersonPseudoId: (personPseudoId: string) => Promise<PersonDto>
    hendelseForRef: (meldingsreferanse: string) => Promise<MeldingDto>
}

export const BackendContext = createContext<Backend>()

export const useBackend = () => useContext(BackendContext)

export const personPseudoIdRequestFactory = (personId: string, backend: Backend) => {
    if (personId.length == 11) {
        return backend.personPseudoIdForFnr(personId)
    } else if (personId.length == 13 || personId.length == 2 || personId==="lokal") {
        return backend.personPseudoIdForAktørId(personId)
    } else {
        throw Error('Ikke gyldig fnr eller aktør-id')
    }
}
