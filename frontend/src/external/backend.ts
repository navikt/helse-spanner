import {PersonDto} from './dto'
import {createContext, useContext} from "../contexts";

export type Backend = {
  personForFnr: (fnr: string) => Promise<PersonDto>
  personForAktørId: (aktørId: string) => Promise<PersonDto>
}

export const BackendContext = createContext<Backend>()

export const useBackend = () => useContext(BackendContext)
