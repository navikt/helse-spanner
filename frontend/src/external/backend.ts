import { PersonDto } from './dto'
import * as Utils from '../Utils'

export type Backend = {
  personForFnr: (fnr: String) => Promise<PersonDto>
  personForAktørId: (aktørId: String) => Promise<PersonDto>
}

export const BackendContext = Utils.createContext<Backend>()

export const useBackend = () => Utils.useContext(BackendContext)
