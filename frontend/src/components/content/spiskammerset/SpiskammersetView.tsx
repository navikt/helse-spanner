import React, { useState } from 'react'
import { Button, TextField, Alert } from '@navikt/ds-react'
import ReactJson from '@microlink/react-json-view'
import { useAtomValue } from 'jotai'
import { themeAtom } from '../../../state/state'
import { writeToClipboard } from '../../../utils'
import { getMockSpiskammersetData } from './spiskammersetMockData'

export const SpiskammersetView = () => {
    const [behandlingId, setBehandlingId] = useState('')
    const [fnr, setFnr] = useState('')
    const [data, setData] = useState<any>(null)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)
    const [usedMockData, setUsedMockData] = useState(false)
    const theme = useAtomValue(themeAtom)

    const handleFetch = async () => {
        if (!behandlingId || !fnr) {
            setError('Både behandlingId og fnr må fylles ut')
            return
        }

        setLoading(true)
        setError(null)
        setData(null)
        setUsedMockData(false)

        try {
            const response = await fetch(
                `/api/spiskammerset/behandling/${behandlingId}?opplysning=forsikring`,
                {
                    method: 'GET',
                    headers: {
                        Accept: 'application/json',
                        fnr: fnr
                    }
                }
            )

            if (!response.ok) {
                // Try mock data if backend fails
                const mockData = getMockSpiskammersetData(behandlingId)
                if (mockData) {
                    setData(mockData)
                    setUsedMockData(true)
                    return
                }
                throw new Error(`HTTP error! status: ${response.status}`)
            }

            const result = await response.json()
            setData(result)
        } catch (err) {
            // Try mock data as fallback
            const mockData = getMockSpiskammersetData(behandlingId)
            if (mockData) {
                setData(mockData)
                setUsedMockData(true)
            } else {
                setError(err instanceof Error ? err.message : 'En ukjent feil oppstod')
            }
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
                    label="Behandling ID"
                    value={behandlingId}
                    onChange={(e) => setBehandlingId(e.target.value)}
                    placeholder="Skriv inn behandling ID"
                />

                <TextField
                    label="Fødselsnummer"
                    value={fnr}
                    onChange={(e) => setFnr(e.target.value)}
                    placeholder="Skriv inn fødselsnummer"
                />

                <Button
                    onClick={handleFetch}
                    loading={loading}
                    disabled={!behandlingId || !fnr}
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
                    ℹ️ Viser mockdata (backend ikke tilgjengelig)
                </Alert>
            )}

            {!data && !error && !loading && (
                <Alert variant="info" style={{ marginBottom: '20px' }}>
                    <strong>Tilgjengelige mock behandling-IDer:</strong>
                    <ul style={{ marginTop: '8px', marginBottom: 0 }}>
                        <li><code>test</code> - Sykepenger (avsluttet)</li>
                        <li><code>error</code> - Feilhåndtering eksempel</li>
                    </ul>
                    <p style={{ marginTop: '8px', marginBottom: 0 }}>
                        Du kan bruke hvilket som helst fødselsnummer for testing.
                    </p>
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
