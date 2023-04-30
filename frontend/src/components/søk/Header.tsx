import React from 'react'
import styles from './Header.module.css'
import classNames from 'classnames'

export const Header: React.FC<object> = ({ children }) => (
    <header className={classNames(styles.Header)}>
        <span>
            <span className={classNames(styles.SpannSpan)}>🪣</span>
            <span className={classNames(styles.SpannSpanText)}>er</span>
        </span>
        {children}
    </header>
)
Header.displayName = 'Header'
