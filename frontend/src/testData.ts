import { PersonDto } from './external/dto'

export const createTestPerson = (aktørId: string = '42', fødselsnummer: string = '42'): PersonDto => ({
    aktørId,
    fødselsnummer
})
