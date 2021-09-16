import { PersonDto } from '../state/dto'
import { Backend } from './backend'

export let testBackend = (testPersoner: PersonDto[] = [], errorPersoner: Record<string, Error> = {}): Backend => {
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

    return {
        personForAktørId(aktørId: string): Promise<PersonDto> {
            let person = aktorPersoner[aktørId]
            if (!person) {
                let testError = errorPersoner[aktørId]
                if (!testError) {
                    throw Error(`No test fixture with ${aktørId}`)
                }
                return Promise.reject(testError)
            }
            return Promise.resolve(person)
        },

        personForFnr(fnr: string): Promise<PersonDto> {
            let person = fnrPersoner[fnr]
            if (!person) {
                let testError = errorPersoner[fnr]
                if (!testError) {
                    throw Error(`No test fixture with ${fnr}`)
                }
                return Promise.reject(person)
            }
            return Promise.resolve(person)
        },
    }
}
