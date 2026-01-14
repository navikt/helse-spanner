import { Environment } from '../../external/environment'

const sporingHostname = Environment.isDevelopment
    ? 'localhost'
    : window.location.origin.includes('dev')
    ? 'sporing.ansatt.dev.nav.no'
    : 'sporing.ansatt.nav.no'
export const tilstandsmaskinSporingUrl = (id: string) => `https://${sporingHostname}/tilstandsmaskin/${id}`
export const personSporingUrl = (pid: string) => `https://${sporingHostname}/person/${pid}`

const speilHostname = Environment.isDevelopment
    ? 'localhost'
    : window.location.origin.includes('dev')
    ? 'speil.ansatt.dev.nav.no'
    : 'speil.ansatt.nav.no'
export const speilUrl = () => `https://${speilHostname}/person`
