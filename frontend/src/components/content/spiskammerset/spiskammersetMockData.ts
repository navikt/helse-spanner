// Mock data for Spiskammerset hentAlt API testing
export const mockSpiskammersetData = {
    default: {
        forsikring: {
            dekningsgrad: 100,
            dag1Eller17: 1,
            versjon: 1,
        },
    },
    forsikringOnly: {
        forsikring: {
            dekningsgrad: 80,
            dag1Eller17: 17,
            versjon: 2,
        },
    },
}

// Get mock data by fnr key (for demonstration/testing purposes)
export function getMockSpiskammersetDataByFnr(fnr: string): any {
    const mockMap: Record<string, any> = {
        '12345678901': mockSpiskammersetData.default,
        '98765432100': mockSpiskammersetData.forsikringOnly,
    }
    return mockMap[fnr] || null
}

