import {Backend} from './backend'
import {MeldingDto, PersonDto} from '../state/dto'
import {jsonHendelse} from "./jsonHendelse";
import {jsonPerson} from "./jsonPerson1";
import {jsonPerson2} from "./jsonPerson2";
import {lagfinnesIkkeFeil} from "./feil";

export let hardCodedBackend: Backend = {
    personForAktørId(aktorid: string): Promise<PersonDto> {
        if (aktorid === "43") return Promise.resolve(jsonPerson2)
        if (aktorid === "42") return Promise.resolve(jsonPerson)
        else return Promise.reject(lagfinnesIkkeFeil())
    },
    personForFnr(ignore: string): Promise<PersonDto> {
        return Promise.resolve(jsonPerson)
    },
    hendelseForRef: function (_: string): Promise<MeldingDto> {
        return Promise.resolve(jsonHendelse)
    }
}

