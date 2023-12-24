import React from 'react'
import {InternalHeader, Spacer} from "@navikt/ds-react";
import styles from './Header.module.css'
import classNames from "classnames";

export const Header: React.FC<object> = ({ children }) => (
    <InternalHeader data-theme="spanner">
        <InternalHeader.Title href="/">
            <span className={classNames(styles.SpannSpan)}>🪣</span>er
        </InternalHeader.Title>
        {children}
        <Spacer/>
        <InternalHeader.User name="Ola Normann"/>
    </InternalHeader>
)
Header.displayName = 'Header'
