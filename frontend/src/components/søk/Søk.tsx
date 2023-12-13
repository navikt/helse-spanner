import React, {useCallback, useEffect, useRef} from 'react'
import styles from './Header.module.css'
import classNames from 'classnames'
import { useLocation, useNavigate } from 'react-router-dom'
import { maskertRequestFactory, useBackend} from "../../external/backend";
import {Feilmelding} from "../Feilmelding";
import {useQuery} from "react-query";
import {Spinner} from "../Spinner";
import {PersonContext} from "../../state/contexts";
import {PersonView} from "../person/PersonView";

export const Søk = () => {
    const navigate = useNavigate()
    const location = useLocation()
    const [søketekst, setSøketekst] = React.useState('')
    const inputRef = useRef<HTMLInputElement>(null)

    const updatePath = (uuid: string) => {
        const newPath = `/person/${uuid}`
        if (newPath === location.pathname) return
        setSøketekst('')
        navigate({ pathname: newPath })
    }

    useEffect(() => {
        if (location.pathname === '/') inputRef.current && inputRef.current.focus()
    }, [location.pathname])

    const backend = useBackend()

    const sendSøk = useCallback(() => {
        console.log(`sendSøk1 ${søketekst}`)
        if (søketekst === '') return
        console.log(`sendSøk2 ${søketekst}`)
        if (søketekst.length == 36) return updatePath(søketekst)
        console.log(`sendSøk mot backend ${søketekst}`)
        try {
            maskertRequestFactory(søketekst, backend)
                .catch((e: Error) => {
                    // todo: hvordan får vi vist denne feilmeldingen i frontend?
                    console.error(`Fikk feil ved søk: ${e}`)
                })
                .then ((dto) => {
                    if (dto) updatePath(dto.id)
                })
        } catch (error) {
            // todo: hvordan får vi vist denne feilmeldingen i frontend?
            console.error(`Fikk feil ved søk: ${error}`)
        }

    }, [backend, søketekst])

    return (
        <div className={classNames(styles.Søk)}>
            <input
                ref={inputRef}
                placeholder="fnr/aktør-id"
                id="person-search"
                type={'text'}
                value={søketekst}
                onChange={(e) => setSøketekst(e.target.value.trim())}
                data-testid="søkefelt"
                onKeyDown={(event) => event.key == 'Enter' && sendSøk()}
            />
            <button onClick={sendSøk} data-testid="søkeknapp">
                Søk
            </button>
        </div>
    )
}
Søk.displayName = 'Søk'
