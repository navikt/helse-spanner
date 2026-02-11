// Mock data for Spiskammerset API testing
export const mockSpiskammersetData: Record<string, any> = {
    'test': {
        forsikring: {
            dekningsgrad: 100,
            dag1Eller17: 1,
            versjon: 1,
        },
    },
    'error': {
        error: 'Dette er et eksempel på feilhåndtering',
        behandlingId: 'error-example',
        detaljer: 'Bruk denne for å teste error-scenarioer',
    },
}

// Get mock data for a behandlingId
export function getMockSpiskammersetData(behandlingId: string): any {
    return mockSpiskammersetData[behandlingId] || null
}

