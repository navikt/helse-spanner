import React, { useEffect, useRef } from 'react'
import styles from './Header.module.css'
import classNames from 'classnames'
import { useLocation, useNavigate } from 'react-router-dom'

export const Søk = () => {
    const navigate = useNavigate()
    const location = useLocation()
    const [søketekst, setSøketekst] = React.useState('')
    const inputRef = useRef<HTMLInputElement>(null)

    const sendSøk = () => {
        if (søketekst === '') return
        const newPath = `/person/${søketekst}`
        if (newPath === location.pathname) return
        setSøketekst('')
        navigate({ pathname: newPath })
    }

    useEffect(() => {
        if (location.pathname === '/') inputRef.current && inputRef.current.focus()
    }, [location.pathname])

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
