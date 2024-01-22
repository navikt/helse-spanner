import {Backend} from './backend'
import {MaskertDto, MeldingDto, PersonDto} from '../state/dto'
import {jsonHendelse} from './jsonHendelse'
import {jsonPerson} from './jsonPerson1'
import {jsonPerson2} from './jsonPerson2'
import {lagfinnesIkkeFeil} from './feil'

const person1 = "0c48a7da-dab6-4a6c-b116-b24eec896933"
const person2 = "ac427542-11e0-4f84-bb7e-1343b6103fa5"
export let hardCodedBackend: Backend = {
    uuidForFnr(fnr: string): Promise<MaskertDto> {
        return Promise.resolve({ id: person1 })
    },
    uuidForAktørId(aktorid: string): Promise<MaskertDto> {
        if (aktorid === '43') return Promise.resolve({ id: person2 })
        if (aktorid === '42') return Promise.resolve({ id: person1 })
        else return Promise.reject(lagfinnesIkkeFeil())
    },
    personForUUID(uuid: string): Promise<PersonDto> {
        if (uuid === person2) return Promise.resolve(jsonPerson2)
        if (uuid === person1) return Promise.resolve(jsonPerson)
        return Promise.reject(lagfinnesIkkeFeil())
    },
    hendelseForRef: function (_: string): Promise<MeldingDto> {
        return Promise.resolve(jsonHendelse)
    },
}
