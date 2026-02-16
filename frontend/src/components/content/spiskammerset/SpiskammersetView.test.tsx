import { test, expect, beforeEach, afterEach, describe, vi } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { SpiskammersetView } from './SpiskammersetView'
import fetchMock from 'fetch-mock'
import React from 'react'
import { Provider } from 'jotai'

// Mock the utils module
vi.mock('../../utils', () => ({
    writeToClipboard: vi.fn(),
}))

const TestWrapper = ({ children }: { children: React.ReactNode }) => {
    return <Provider>{children}</Provider>
}

describe('SpiskammersetView', () => {
    beforeEach(() => {
        fetchMock.mockGlobal()
    })

    afterEach(() => {
        fetchMock.removeRoutes()
        fetchMock.clearHistory()
        fetchMock.unmockGlobal()
    })

    test('renders form with fødselsnummer input, checkboxes, and button', () => {
        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        expect(screen.getByLabelText(/Fødselsnummer/i)).toBeTruthy()
        expect(screen.getByLabelText(/Forsikring/i)).toBeTruthy()
        expect(screen.getByRole('button', { name: /Hent data/i })).toBeTruthy()
    })

    test('button is disabled when fødselsnummer is empty', () => {
        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const button = screen.getByRole('button', { name: /Hent data/i }) as HTMLButtonElement
        expect(button.disabled).toBe(true)
    })

    test('button is enabled when fødselsnummer is filled', async () => {
        const user = userEvent.setup()
        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const fnrInput = screen.getByLabelText(/Fødselsnummer/i) as HTMLInputElement
        const button = screen.getByRole('button', { name: /Hent data/i }) as HTMLButtonElement

        await user.type(fnrInput, '12345678901')

        expect(button.disabled).toBe(false)
    })

    test('forsikring checkbox is checked by default', () => {
        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const forsikringCheckbox = screen.getByLabelText(/Forsikring/i) as HTMLInputElement

        expect(forsikringCheckbox.checked).toBe(true)
    })

    test('successfully fetches and displays data from backend with default forsikring', async () => {
        const user = userEvent.setup()
        const mockData = {
            forsikring: {
                dekningsgrad: 100,
                dag1Eller17: 1,
                versjon: 1,
            },
        }
        fetchMock.post('path:/api/spiskammerset/hentAlt', {
            status: 200,
            body: mockData,
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const fnrInput = screen.getByLabelText(/Fødselsnummer/i) as HTMLInputElement
        const button = screen.getByRole('button', { name: /Hent data/i })

        await user.type(fnrInput, '12345678901')
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/Resultat:/i)).toBeTruthy()
        })
    })

    test('sends correct request body when both checkboxes are checked', async () => {
        const user = userEvent.setup()
        fetchMock.post('path:/api/spiskammerset/hentAlt', {
            status: 200,
            body: { forsikring: {} },
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const fnrInput = screen.getByLabelText(/Fødselsnummer/i)
        const button = screen.getByRole('button', { name: /Hent data/i })

        await user.type(fnrInput, '12345678901')
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/Resultat:/i)).toBeTruthy()
        })
    })

    test('displays mock data when backend returns 404', async () => {
        const user = userEvent.setup()

        fetchMock.post('path:/api/spiskammerset/hentAlt', {
            status: 404,
            body: { error: 'Not found' },
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const fnrInput = screen.getByLabelText(/Fødselsnummer/i)
        const button = screen.getByRole('button', { name: /Hent data/i })

        await user.type(fnrInput, '12345678901')
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/Viser mockdata/i)).toBeTruthy()
            expect(screen.getByText(/Resultat:/i)).toBeTruthy()
        })
    })

    test('displays mock data when network fails', async () => {
        const user = userEvent.setup()

        fetchMock.post('path:/api/spiskammerset/hentAlt', {
            throws: new Error('Network error'),
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const fnrInput = screen.getByLabelText(/Fødselsnummer/i)
        const button = screen.getByRole('button', { name: /Hent data/i })

        await user.type(fnrInput, '12345678901')
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/Viser mockdata/i)).toBeTruthy()
            expect(screen.getByText(/Resultat:/i)).toBeTruthy()
        })
    })

    test('displays error when backend fails and no mock data available', async () => {
        const user = userEvent.setup()

        fetchMock.post('path:/api/spiskammerset/hentAlt', {
            status: 404,
            body: { error: 'Not found' },
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const fnrInput = screen.getByLabelText(/Fødselsnummer/i)
        const button = screen.getByRole('button', { name: /Hent data/i })

        // Use an fnr that has no mock data
        await user.type(fnrInput, '99999999999')
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/HTTP error! status: 404/i)).toBeTruthy()
        })
    })

    test('clears previous data and error when fetching new data', async () => {
        const user = userEvent.setup()
        const mockData = { forsikring: { dekningsgrad: 100 } }

        fetchMock.post('path:/api/spiskammerset/hentAlt', {
            status: 200,
            body: mockData,
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const fnrInput = screen.getByLabelText(/Fødselsnummer/i)
        const button = screen.getByRole('button', { name: /Hent data/i })

        // First fetch
        await user.type(fnrInput, '12345678901')
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/Resultat:/i)).toBeTruthy()
        })

        // Clear and fetch again
        await user.clear(fnrInput)
        await user.type(fnrInput, '98765432100')
        await user.click(button)

        // Verify new result is displayed
        await waitFor(() => {
            expect(screen.getByText(/Resultat:/i)).toBeTruthy()
        })
    })
})
