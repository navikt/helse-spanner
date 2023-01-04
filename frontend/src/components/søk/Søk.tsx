import React from 'react'
import styles from './Header.module.css'
import classNames from 'classnames'

export type SøkProps = {
    onSearch: (personId: string | undefined) => void
}

export const Søk = React.memo((props: SøkProps) => {
    const [søketekst, setSøketekst] = React.useState('')
    const sendSøk = () => {
        if (søketekst.trim() === '') {
            props.onSearch(undefined)
        } else {
            props.onSearch(søketekst.trim())
        }
    }

    return (
        <div className={classNames(styles.Søk)}>
            <input
                placeholder="fnr/aktør-id"
                type={'text'}
                autoFocus={true}
                value={søketekst}
                onChange={(e) => setSøketekst(e.target.value)}
                data-testid="søkefelt"
                onKeyDown={(event) => event.key == 'Enter' && sendSøk()}
            />
            <button onClick={sendSøk} data-testid="søkeknapp">
                Søk
            </button>
        </div>
    )
})
Søk.displayName = 'Søk'
