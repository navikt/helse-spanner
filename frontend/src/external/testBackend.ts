import {MeldingDto, PersonDto} from '../state/dto'
import { Backend } from './backend'

export let testBackend = (testPersoner: PersonDto[] = [], errorPersoner: Record<string, Error> = {}, testMeldinger: MeldingDto[] = []): Backend => {
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
        }
    }
}
