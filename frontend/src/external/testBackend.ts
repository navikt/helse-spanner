import {MeldingDto, PersonDto, PersonPseudoIdDto} from '../state/dto'
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
        personPseudoIdForAktørId(aktørId: string): Promise<PersonPseudoIdDto> {
            return Promise.resolve({ id: aktørId })
        },

        personPseudoIdForFnr(fnr: string): Promise<PersonPseudoIdDto> {
            return Promise.resolve({ id: fnr })
        },

        personForPersonPseudoId(personPseudoId: string): Promise<PersonDto> {
            let person = aktorPersoner[personPseudoId]
            if (person) return Promise.resolve(person)
            let fnrperson = fnrPersoner[personPseudoId]
            if (fnrperson) return Promise.resolve(fnrperson)
            let testError = errorPersoner[personPseudoId]
            if (!testError) {
                throw Error(`No test fixture with ${personPseudoId}`)
            }
            return Promise.reject(testError)
        },
    }
}
