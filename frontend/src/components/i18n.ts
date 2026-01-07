import { format } from 'date-fns'
import { parseISO } from 'date-fns/parseISO'

function parseDate(dato: string, dateFormat: string) {
    try {
        const date = parseISO(dato)
        return format(date, dateFormat)
    } catch (e) {
        return '[ugyldig dato]'
    }
}

export const somNorskDato = (dato: string) => parseDate(dato, 'dd.MM.yyyy')

export const somNorskKlokkeslett = (dato: string) => parseDate(dato, 'HH.mm.ss,SSS')
