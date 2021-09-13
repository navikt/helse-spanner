import { PersonDto } from '../state/dto'
import { createContext, useContext } from '../state/contexts'

export type Backend = {
    personForFnr: (fnr: string) => Promise<PersonDto>
    personForAktørId: (aktørId: string) => Promise<PersonDto>
}

export const BackendContext = createContext<Backend>()

export const useBackend = () => useContext(BackendContext)
