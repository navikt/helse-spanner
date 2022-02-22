import { format } from 'date-fns'
import parseISO from 'date-fns/parseISO'

export const somNorskDato = (dato: string) => {
    try {
        const date = parseISO(dato)
        return format(date, 'dd.MM.yyyy')
    } catch (e) {
        return '[ugyldig dato]'
    }
}
