const sporingHostname = window.location.origin.includes('dev')
    ? 'sporing.dev.intern.nav.no'
    : 'sporing.intern.nav.no'
export const tilstandsmaskinSporingUrl = (id: string) => `https://${sporingHostname}/tilstandsmaskin/${id}`
export const personSporingUrl = (aktørId: string) => `https://${sporingHostname}/person/${aktørId}`

const speilHostname = window.location.origin.includes('dev')
    ? 'speil.dev.intern.nav.no'
    : 'speil.intern.nav.no'
export const speilUrl = (aktørId: string) => `https://${speilHostname}/person/${aktørId}`
