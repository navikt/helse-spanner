import { FeilDto } from './dto'

export class backendFeil extends Error {
    feilId: string | undefined
    status: number

    constructor(status: number, feilDto?: FeilDto) {
        !!feilDto
            ? super(`Feil fra server: ${status}. Feilkode: ${feilDto.error_id}. ${feilDto.description}`)
            : super(`Feil fra server: ${status}`)
        this.feilId = feilDto?.error_id
        this.name = 'backendFeil'
        this.status = status
    }
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
    constructor(feilDto?: FeilDto) {
        super(404, feilDto)
        this.name = 'notFoundFeil'
    }
}

const fraResponse = async (response: Response): Promise<backendFeil> => {
    let feilDto: FeilDto | undefined
    try {
        feilDto = await response.json()
    } catch (e) {
        console.log('Fikk svar fra server uten feilDto')
    }

    return response.status === 404 ? new finnesIkke(feilDto) : new backendFeil(response.status, feilDto)
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
