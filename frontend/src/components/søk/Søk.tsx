import React, {useEffect, useRef, useState} from 'react'
import {useLocation, useNavigate} from 'react-router-dom'
import {maskertRequestFactory, useBackend} from "../../external/backend";
import {Alert, Box, HStack, Search} from "@navikt/ds-react";

export const Søk = () => {
    const navigate = useNavigate()
    const location = useLocation()
    const [søketekst, setSøketekst] = useState('')
    const [isLoading, setIsLoading] = useState(false)
    const [feilmelding, setFeilmelding] = useState('')
    const inputRef = useRef<HTMLInputElement>(null)

    const updatePath = (uuid: string) => {
        setIsLoading(false)
        const newPath = `/person/${uuid}`
        console.log(`oppdaterer pathname med ${newPath}`)
        if (newPath === location.pathname) return
        setSøketekst('')
        navigate({ pathname: newPath })
    }

    useEffect(() => {
        if (location.pathname === '/') inputRef.current && inputRef.current.focus()
    }, [location.pathname])

    const backend = useBackend()

    const sendSøk = async () => {
        if (søketekst === '') return
        if (søketekst.length == 36) return updatePath(søketekst)
        try {
            setIsLoading(true)
            maskertRequestFactory(søketekst, backend)
                .catch((e: Error) => {
                    setIsLoading(false)
                    setFeilmelding(e.message)
                })
                .then ((dto) => {
                    if (dto) updatePath(dto.id)
                })
        } catch (error: unknown) {
            setIsLoading(false)
            if (error instanceof Error) setFeilmelding(error.message)
            else if (typeof error === 'string') setFeilmelding(error)
        }
    }

    return (
        <Box paddingBlock="4" paddingInline="2 0">
            <HStack gap="5">
                <form onSubmit={(e) => {
                    e.preventDefault()
                    sendSøk()
                }}>
                    <Search
                        ref={inputRef}
                        id="person-search"
                        label="Personsøk"
                        size="small"
                        value={søketekst}
                        variant="primary"
                        placeholder="fnr/aktør-id"
                        onChange={(value) => {
                            setFeilmelding('')
                            setSøketekst(value.trim())
                        }}
                        data-testid="søkefelt"
                    >
                        <Search.Button type="submit" loading={ isLoading } />
                    </Search>
                </form>
                <div>
                    { !!feilmelding && <Alert style={{ color: "var(--a-white)" }} inline variant="error">{ feilmelding }</Alert> }
                </div>
            </HStack>
        </Box>
    )
}
Søk.displayName = 'Søk'
