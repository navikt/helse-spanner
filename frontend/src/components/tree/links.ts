const sporingHostname = window.location.origin.includes('dev')
    ? 'sporing.dev.intern.nav.no'
    : 'sporing.intern.nav.no'

export const tilstandsmaskinSporingUrl = (id: string) => `https://${sporingHostname}/tilstandsmaskin/${id}`
export const personSporingUrl = (aktørId: string) => `https://${sporingHostname}/person/${aktørId}`
