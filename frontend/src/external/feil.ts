export class backendFeil extends Error {
    constructor(status: number, errorId: string, description: string) {
        super(`Http status: ${status} error_id: ${errorId} decription: ${description}`)
        this.name = 'backendFeil'
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
    constructor() {
        super(404, "asdasd2323", "Fant ikke person")
        this.name = 'notFoundFeil'
    }
}