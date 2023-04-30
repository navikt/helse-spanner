import React from 'react'
import parseISO from 'date-fns/parseISO'
import styles from './PersonHeader.module.css'
import { usePerson } from '../../state/contexts'
import { speilUrl } from '../tree/links'

export const PersonHeader = () => {
    const person = usePerson()
    return (
        <div className={styles.Header}>
            <dl className={styles.Ingress}>
                <dt>fnr</dt>
                <dd>
                    <p data-testid={'person-header-fnr'}>{person.fødselsnummer}</p>
                </dd>
                <dt>aktørid</dt>
                <dd>
                    <p>{person.aktørId}</p>
                </dd>
                <dt>opprettet</dt>
                <dd>
                    <p>{parseISO(person.opprettet).toDateString()}</p>
                </dd>
                <dt>🪞</dt>
                <dd>
                    <a href={speilUrl(person.aktørId)} target="_blank" className={styles.SpeilLink}>
                        trykk her
                    </a>
                </dd>
            </dl>
        </div>
    )
}
PersonHeader.displayName = 'PersonHeader'
