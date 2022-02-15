import React from 'react'
import { PersonHeader } from './PersonHeader'
import classNames from 'classnames'
import styles from './PersonView.module.css'
import { PersonTree } from '../tree/PersonTree'
import { Content } from '../content/Content'
import {Card, HeaderCard} from "../Card";

export const PersonView = React.memo(() => {
    return (
        <div className={classNames(styles.PersonView)} data-testid="person">
            <HeaderCard style={{gridArea: "header"}}><PersonHeader /></HeaderCard>
            <Card style={{gridArea: "nav", marginLeft: 0, width: "17.5rem"}}><PersonTree/></Card>
            <Content />
        </div>
    )
})
PersonView.displayName="PersonView"
