import {Backend} from './backend'
import {MeldingDto, PersonDto} from '../state/dto'
import {jsonHendelse} from "./jsonHendelse";
import {jsonPerson} from "./jsonPerson1";
import {jsonPerson2} from "./jsonPerson2";

export let hardCodedBackend: Backend = {
    personForAktørId(ignore: string): Promise<PersonDto> {
        if (ignore === "43") {
            return Promise.resolve(jsonPerson2)
        }
        return Promise.resolve(jsonPerson)
    },
    personForFnr(ignore: string): Promise<PersonDto> {
        return Promise.resolve(jsonPerson)
    },
    hendelseForRef: function (_: string): Promise<MeldingDto> {
        return Promise.resolve(jsonHendelse)
    }
}

