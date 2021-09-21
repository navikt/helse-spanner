import React from 'react'
import styles from './Header.module.css'
import classNames from 'classnames'

export type SøkProps = {
    setPersonId: (personId?: string) => void
}

export const Søk = React.memo((props: SøkProps) => {
    const [søketekst, setSøketekst] = React.useState('')
    const sendSøk = () => {
        if (søketekst.trim() === '') {
            props.setPersonId(undefined)
        } else {
            props.setPersonId(søketekst.trim())
            setSøketekst("")
        }
    }

    return (
        <div className={classNames(styles.Søk)}>
            <input placeholder="fnr/aktør-id" type={"number"} autoFocus={true} value={søketekst} onChange={e => setSøketekst(e.target.value)} data-testid="søkefelt" />
            <button onClick={sendSøk} data-testid="søkeknapp">
                Søk
            </button>
        </div>
    )
})
Søk.displayName="Søk"