import React from 'react'
import parseISO from 'date-fns/parseISO'
import classNames from 'classnames'
import styles from './PersonHeader.module.css'
import { usePerson } from '../contexts'

export const PersonHeader = React.memo(() => {
    const person = usePerson()
    return (
        <div className={classNames(styles.PersonHeader)}>
            <dl>
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
            </dl>
        </div>
    )
})