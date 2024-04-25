import { Environment } from '../../external/environment'

const sporingHostname = Environment.isDevelopment
    ? 'localhost'
    : window.location.origin.includes('dev')
    ? 'sporing.ansatt.dev.nav.no'
    : 'sporing.ansatt.nav.no'
export const tilstandsmaskinSporingUrl = (id: string) => `https://${sporingHostname}/tilstandsmaskin/${id}`
export const personSporingUrl = (aktørId: string) => `https://${sporingHostname}/person/${aktørId}`

const speilHostname = Environment.isDevelopment
    ? 'localhost'
    : window.location.origin.includes('dev')
    ? 'speil.intern.dev.nav.no'
    : 'speil.intern.nav.no'
export const speilUrl = (aktørId: string) => `https://${speilHostname}/person/${aktørId}/dagoversikt`
