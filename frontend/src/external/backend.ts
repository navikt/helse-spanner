import { PersonDto } from './dto'
import * as Utils from '../utils'

export type Backend = {
  personForFnr: (fnr: string) => Promise<PersonDto>
  personForAktørId: (aktørId: string) => Promise<PersonDto>
}

export const BackendContext = Utils.createContext<Backend>()

export const useBackend = () => Utils.useContext(BackendContext)
