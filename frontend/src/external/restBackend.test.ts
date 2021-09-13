import { restBackend } from './restBackend'
import fetchMock from 'fetch-mock'
import { backendFeil, httpFeil } from './feil'

test('ok fra backend er ok', async () => {
    fetchMock.get('http://localhost:8080/api/personer/', { foo: 'bar' })
    expect(await restBackend.personForAktørId('foo')).toEqual({ foo: 'bar' })
    fetchMock.restore()
})

test('Feil fra backend kan legge ved feildto i body for mer detaljer', async () => {
    fetchMock.get('http://localhost:8080/api/personer/', {
        status: 418,
        body: JSON.stringify({ error_id: 'FOO', description: 'Bad' })
    })
    let thrownError = await getBackendFeil(async () => restBackend.personForAktørId('foo'))
    expect(thrownError?.feilId).toBe('FOO')
    expect(thrownError?.message).toContain('Feil fra server')
    expect(thrownError?.message).toContain('Bad')
    fetchMock.restore()
})

test('Feil fra backend uten gyldig feildto blir fortsatt backendfeil', async () => {
    fetchMock.get('http://localhost:8080/api/personer/', {
        status: 418
    })

    let thrownError = await getBackendFeil(async () => restBackend.personForAktørId('foo'))

    expect(thrownError?.feilId).toBe(undefined)
    expect(thrownError?.message).toContain('Feil fra server')
    fetchMock.restore()
})

test('Feil fra nettverksstacke reject gir httpError', async () => {
    fetchMock.get('http://localhost:8080/api/personer/', {
        throws: Error('Connection refused')
    })
    let thrownError = await getHttpFeil(async () => restBackend.personForAktørId('foo'))

    expect(thrownError?.message).toBe('Feil under nettverkskall: Connection refused')
    expect(thrownError?.cause).toBeInstanceOf(Error)
    fetchMock.restore()
})

async function getBackendFeil(f: () => Promise<any>): Promise<backendFeil> {
    try {
        await f()
    } catch (e) {
        if (e instanceof backendFeil) {
            return e
        }
        throw Error()
    }
    throw Error()
}

async function getHttpFeil(f: () => Promise<any>): Promise<httpFeil> {
    try {
        await f()
    } catch (e) {
        if (e instanceof httpFeil) {
            return e
        }
        console.log('haha', e)
        throw Error()
    }
    console.log('ha')
    throw Error()
}
