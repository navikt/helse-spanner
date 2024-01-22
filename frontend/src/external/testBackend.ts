import {MaskertDto, MeldingDto, PersonDto} from '../state/dto'
import {Backend} from './backend'

export let testBackend = (
    testPersoner: PersonDto[] = [],
    errorPersoner: Record<string, Error> = {},
    testMeldinger: MeldingDto[] = []
): Backend => {
    let aktorPersoner: Record<string, PersonDto> = testPersoner.reduce((old, person) => {
        return {
            ...old,
            [person.aktørId]: person,
        }
    }, {})

    let fnrPersoner: Record<string, PersonDto> = testPersoner.reduce((old, person) => {
        return {
            ...old,
            [person.fødselsnummer]: person,
        }
    }, {})

    let meldinger: Record<string, MeldingDto> = testMeldinger.reduce((old, melding) => {
        return {
            ...old,
            [melding.id]: melding,
        }
    }, {})

    return {
        hendelseForRef(meldingsreferanse: string): Promise<MeldingDto> {
            let melding = meldinger[meldingsreferanse]
            if (!melding) {
                throw Error(`No test fixture with ${meldingsreferanse}`)
            }
            return Promise.resolve(melding)
        },
        uuidForAktørId(aktørId: string): Promise<MaskertDto> {
            return Promise.resolve({ id: aktørId })
        },

        uuidForFnr(fnr: string): Promise<MaskertDto> {
            return Promise.resolve({ id: fnr })
        },

        personForUUID(uuid: string): Promise<PersonDto> {
            let person = aktorPersoner[uuid]
            if (person) return Promise.resolve(person)
            let fnrperson = fnrPersoner[uuid]
            if (fnrperson) return Promise.resolve(fnrperson)
            let testError = errorPersoner[uuid]
            if (!testError) {
                throw Error(`No test fixture with ${uuid}`)
            }
            return Promise.reject(testError)
        },
    }
}
