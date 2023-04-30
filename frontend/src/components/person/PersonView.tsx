import React, { useEffect } from 'react'
import { PersonHeader } from './PersonHeader'
import classNames from 'classnames'
import styles from './PersonView.module.css'
import { PersonTree } from '../tree/PersonTree'
import { Content } from '../content/Content'
import { Card, HeaderCard } from '../Card'
import { useResetRecoilState } from 'recoil'
import { åpneHendelseDokumentState } from '../../state/state'
import { usePerson } from '../../state/contexts'

export const PersonView = () => {
    const resetÅpneHendelser = useResetRecoilState(åpneHendelseDokumentState)
    const person = usePerson()
    useEffect(() => {
        resetÅpneHendelser()
    }, [person])

    return (
        <div className={classNames(styles.PersonView)} data-testid="person">
            <HeaderCard style={{ gridArea: 'header' }}>
                <PersonHeader />
            </HeaderCard>
            <Card style={{ gridArea: 'nav', marginLeft: 0, width: '17.5rem' }}>
                <PersonTree />
            </Card>
            <Content />
        </div>
    )
}
PersonView.displayName = 'PersonView'
