import {PersonDto} from "./dto";

export type Backend = {
    personForFnr: (fnr: String) => Promise<PersonDto>
    personForAktørId: (aktørId: String) => Promise<PersonDto>
}
