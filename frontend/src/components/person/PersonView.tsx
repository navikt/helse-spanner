import React from 'react'
import { PersonHeader } from './PersonHeader'
import classNames from 'classnames'
import styles from './PersonView.module.css'
import { PersonTree } from '../tree/PersonTree'
import { Content } from '../content/Content'

export const PersonView = React.memo(() => {
    return (
        <div className={classNames(styles.PersonView)} data-testid="person">
            <PersonHeader />
            <PersonTree />
            <Content />
        </div>
    )
})
