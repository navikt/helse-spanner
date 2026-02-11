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

    test('renders form with input fields and button', () => {
        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        expect(screen.getByLabelText(/Behandling ID/i)).toBeTruthy()
        expect(screen.getByLabelText(/Fødselsnummer/i)).toBeTruthy()
        expect(screen.getByRole('button', { name: /Hent data/i })).toBeTruthy()
    })

    test('button is disabled when fields are empty', () => {
        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const button = screen.getByRole('button', { name: /Hent data/i }) as HTMLButtonElement
        expect(button.disabled).toBe(true)
    })

    test('button is enabled when both fields are filled', async () => {
        const user = userEvent.setup()
        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const behandlingIdInput = screen.getByLabelText(/Behandling ID/i) as HTMLInputElement
        const fnrInput = screen.getByLabelText(/Fødselsnummer/i) as HTMLInputElement
        const button = screen.getByRole('button', { name: /Hent data/i }) as HTMLButtonElement

        await user.type(behandlingIdInput, '123-456-789')
        await user.type(fnrInput, '12345678901')

        expect(button.disabled).toBe(false)
    })

    test('successfully fetches and displays data from backend', async () => {
        const user = userEvent.setup()
        const mockData = {
            forsikring: {
                dekningsgrad: 100,
                dag1Eller17: 1,
                versjon: 1,
            },
        }
        fetchMock.get('path:/api/spiskammerset/behandling/123-456-789', {
            status: 200,
            body: mockData,
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const behandlingIdInput = screen.getByLabelText(/Behandling ID/i) as HTMLInputElement
        const fnrInput = screen.getByLabelText(/Fødselsnummer/i) as HTMLInputElement
        const button = screen.getByRole('button', { name: /Hent data/i })

        await user.type(behandlingIdInput, '123-456-789')
        await user.type(fnrInput, '12345678901')
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/Resultat:/i)).toBeTruthy()
        })

        // Verify that the data was successfully retrieved and displayed
        expect(screen.getByText(/Resultat:/i)).toBeTruthy()
    })

    test('displays error when backend returns 404', async () => {
        const user = userEvent.setup()

        fetchMock.get('path:/api/spiskammerset/behandling/999', {
            status: 404,
            body: { error: 'Not found' },
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const behandlingIdInput = screen.getByLabelText(/Behandling ID/i) as HTMLInputElement
        const fnrInput = screen.getByLabelText(/Fødselsnummer/i) as HTMLInputElement
        const button = screen.getByRole('button', { name: /Hent data/i })

        await user.type(behandlingIdInput, '999')
        await user.type(fnrInput, '12345678901')
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/HTTP error! status: 404/i)).toBeTruthy()
        })
    })

    test('displays error when network fails', async () => {
        const user = userEvent.setup()

        fetchMock.get('path:/api/spiskammerset/behandling/500', {
            throws: new Error('Network error'),
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const behandlingIdInput = screen.getByLabelText(/Behandling ID/i) as HTMLInputElement
        const fnrInput = screen.getByLabelText(/Fødselsnummer/i) as HTMLInputElement
        const button = screen.getByRole('button', { name: /Hent data/i })

        await user.type(behandlingIdInput, '500')
        await user.type(fnrInput, '12345678901')
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/Network error/i)).toBeTruthy()
        })
    })

    test('successfully makes backend request with different behandlingId', async () => {
        const user = userEvent.setup()
        const behandlingId = 'test-behandling-123'
        const fnr = '11223344556'

        fetchMock.get(`path:/api/spiskammerset/behandling/${behandlingId}`, {
            status: 200,
            body: { success: true, data: 'test' },
        })

        render(
            <TestWrapper>
                <SpiskammersetView />
            </TestWrapper>
        )

        const behandlingIdInput = screen.getByLabelText(/Behandling ID/i) as HTMLInputElement
        const fnrInput = screen.getByLabelText(/Fødselsnummer/i) as HTMLInputElement
        const button = screen.getByRole('button', { name: /Hent data/i })

        await user.type(behandlingIdInput, behandlingId)
        await user.type(fnrInput, fnr)
        await user.click(button)

        await waitFor(() => {
            expect(screen.getByText(/Resultat:/i)).toBeTruthy()
        })

        // Verify the result is displayed
        expect(screen.getByText(/Resultat:/i)).toBeTruthy()
    })
})
