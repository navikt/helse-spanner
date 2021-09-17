import { FeilDto } from '../state/dto'

export class backendFeil extends Error {
    feilId: string | undefined
    status: number

    constructor(message: string, status: number, feilId: string | undefined) {
        super(message)
        this.feilId = feilId
        this.name = 'backendFeil'
        this.status = status
    }
}

export const lagBackendFeil = (status: number, feilDto?: FeilDto) => {
    const feilId = feilDto?.error_id
    const message = !!feilDto
        ? `Feil fra server: ${status}. Feilkode: ${feilDto.error_id}. ${feilDto.description}`
        : `Feil fra server: ${status}`
    return new backendFeil(message, status, feilId)
}

export class httpFeil extends Error {
    cause: Error

    constructor(cause: Error) {
        super(`Feil under nettverkskall: ${cause.message}`)
        this.name = 'httpFeil'
        this.cause = cause
    }
}

export class finnesIkke extends backendFeil {
    constructor(feilId: string | undefined) {
        super('Backend fant ikke element', 404, feilId)
        this.name = 'notFoundFeil'
    }
}
export const lagfinnesIkkeFeil = (feilDto?: FeilDto) => {
    const feilId = feilDto?.error_id
    return new finnesIkke(feilId)
}

const fraResponse = async (response: Response): Promise<backendFeil> => {
    let feilDto: FeilDto | undefined
    try {
        feilDto = await response.json()
    } catch (e) {
        console.log('Fikk svar fra server uten feilDto')
    }

    return response.status === 404 ? lagfinnesIkkeFeil(feilDto) : lagBackendFeil(response.status, feilDto)
}

export const feilVedDårligRespons = async (response: Response): Promise<Response> => {
    if (!response.ok) {
        throw await fraResponse(response)
    }
    return response
}

export const wrapNnettverksFeil = (e: Error) => {
    throw new httpFeil(e)
}
