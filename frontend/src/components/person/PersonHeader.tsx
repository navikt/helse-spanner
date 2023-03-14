import React from 'react'
import parseISO from 'date-fns/parseISO'
import styles from './PersonHeader.module.css'
import { usePerson } from '../../state/contexts'

export const PersonHeader = React.memo(() => {
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
                    <a href={`https://speil.dev.intern.nav.no/person/${person.aktørId}`} target="_blank" className={styles.SpeilLink}>
                        trykk her
                    </a>
                </dd>
            </dl>
        </div>
    )
})
PersonHeader.displayName = 'PersonHeader'
