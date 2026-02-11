// Mock data for Spiskammerset API testing
export const mockSpiskammersetData: Record<string, any> = {
    'test-123': {
        behandlingId: 'test-123',
        tilstand: 'AVSLUTTET',
        forsikring: {
            type: 'sykepenger',
            status: 'aktiv',
            grad: 100,
        },
        inntekt: {
            beløp: 500000,
            valuta: 'NOK',
        },
        metadata: {
            opprettet: '2026-02-01T08:00:00Z',
            sistEndret: '2026-02-11T10:00:00Z',
        },
    },
    'behandling-abc': {
        behandlingId: 'behandling-abc',
        tilstand: 'UNDER_BEHANDLING',
        forsikring: {
            type: 'foreldrepenger',
            status: 'søkt',
            grad: 100,
        },
        barn: {
            antall: 1,
        },
        metadata: {
            opprettet: '2026-02-05T10:00:00Z',
        },
    },
    'error-example': {
        error: 'Dette er et eksempel på feilhåndtering',
        behandlingId: 'error-example',
        detaljer: 'Bruk denne for å teste error-scenarioer',
    },
}

// Get mock data for a behandlingId
export function getMockSpiskammersetData(behandlingId: string): any {
    return mockSpiskammersetData[behandlingId] || null
}

