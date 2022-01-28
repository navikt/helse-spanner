import { format } from 'date-fns'
import parseISO from 'date-fns/parseISO'

export const somNorskDato = (dato: string) => format(parseISO(dato), 'dd.MM.yyyy')
