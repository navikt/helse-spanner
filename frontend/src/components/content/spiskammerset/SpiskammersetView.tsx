import React, { useState } from 'react'
import { Button, TextField, Alert } from '@navikt/ds-react'
import ReactJson from '@microlink/react-json-view'
import { useAtomValue } from 'jotai'
import { themeAtom } from '../../../state/state'
import { writeToClipboard } from '../../../utils'
import { getMockSpiskammersetDataByFnr } from './spiskammersetMockData'

export const SpiskammersetView = () => {
    const [fnr, setFnr] = useState('')
    const [data, setData] = useState<any>(null)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)
    const [usedMockData, setUsedMockData] = useState(false)
    const [selectedOpplysninger, setSelectedOpplysninger] = useState<Set<string>>(new Set(['forsikring']))
    const theme = useAtomValue(themeAtom)

    const toggleOpplysning = (opplysning: string) => {
        const newSelected = new Set(selectedOpplysninger)
        if (newSelected.has(opplysning)) {
            newSelected.delete(opplysning)
        } else {
            newSelected.add(opplysning)
        }
        setSelectedOpplysninger(newSelected)
    }

    const handleFetch = async () => {
        if (!fnr) {
            setError('Fødselsnummer må fylles ut')
            return
        }

        setLoading(true)
        setError(null)
        setData(null)
        setUsedMockData(false)

        try {
            const requestBody = {
                personidentifikator: fnr,
                etterspurteOpplysninger: Array.from(selectedOpplysninger)
            }

            const response = await fetch(
                '/api/spiskammerset/hentAlt',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Accept: 'application/json'
                    },
                    body: JSON.stringify(requestBody)
                }
            )

            if (!response.ok) {
                // Try to use mock data as fallback
                const mockData = getMockSpiskammersetDataByFnr(fnr)
                if (mockData) {
                    setData(mockData)
                    setUsedMockData(true)
                    return
                }

                setError(`HTTP error! status: ${response.status}`)
                return
            }

            const result = await response.json()
            setData(result)
        } catch (err) {
            const errorMsg = err instanceof Error ? err.message : 'En ukjent feil oppstod'

            // Try to use mock data as fallback on network errors
            const mockData = getMockSpiskammersetDataByFnr(fnr)
            if (mockData) {
                setData(mockData)
                setUsedMockData(true)
                return
            }

            setError(errorMsg)
        } finally {
            setLoading(false)
        }
    }

    return (
        <div style={{ padding: '20px', maxWidth: '1200px' }}>
            <h2>Spiskammerset API</h2>

            <Alert variant="warning" style={{ marginBottom: '20px' }}>
                <strong>Kun tilgjengelig i dev-miljø</strong>
                <p style={{ marginTop: '4px', marginBottom: 0 }}>
                    Dette endepunktet er kun tilgjengelig i utviklingsmiljøet og vil ikke være tilgjengelig i produksjon.
                </p>
            </Alert>

            <div style={{ marginBottom: '20px', display: 'flex', gap: '10px', flexDirection: 'column', maxWidth: '500px' }}>
                <TextField
                    label="Fødselsnummer"
                    value={fnr}
                    onChange={(e) => setFnr(e.target.value)}
                    placeholder="Skriv inn fødselsnummer"
                />

                <div style={{ marginTop: '10px' }}>
                    <label style={{ display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '8px', cursor: 'pointer' }}>
                        <input
                            type="checkbox"
                            checked={selectedOpplysninger.has('forsikring')}
                            onChange={() => toggleOpplysning('forsikring')}
                        />
                        Forsikring
                    </label>
                </div>

                <Button
                    onClick={handleFetch}
                    loading={loading}
                    disabled={!fnr}
                >
                    Hent data
                </Button>
            </div>

            {error && (
                <Alert variant="error" style={{ marginBottom: '20px' }}>
                    {error}
                </Alert>
            )}

            {usedMockData && (
                <Alert variant="info" style={{ marginBottom: '20px' }}>
                    ℹ️ Viser mockdata (backend ikke tilgjengelig). Bruk følgende fnr for testing:
                    <ul style={{ marginTop: '8px', marginBottom: 0, paddingLeft: '20px' }}>
                        <li><code>12345678901</code> - Standard mock data forsikring</li>
                        <li><code>98765432100</code> - Kun forsikring data</li>
                    </ul>
                </Alert>
            )}

            {data && (
                <div>
                    <h3>Resultat:</h3>
                    <ReactJson
                        theme={theme === 'light' ? 'summerfruit:inverted' : 'chalk'}
                        src={data}
                        name={null}
                        collapsed={1}
                        enableClipboard={(clipboardData) => writeToClipboard(JSON.stringify(clipboardData.src))}
                        sortKeys={true}
                    />
                </div>
            )}
        </div>
    )
}
