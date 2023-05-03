import React from 'react'
import styles from './Header.module.css'
import classNames from 'classnames'
import { Link } from 'react-router-dom'

export const Header: React.FC<object> = ({ children }) => (
    <header className={classNames(styles.Header)}>
        <span>
            <Link to={'/'}>
                <span className={classNames(styles.SpannSpan)}>🪣</span>
            </Link>
            <span className={classNames(styles.SpannSpanText)}>er</span>
        </span>
        {children}
    </header>
)
Header.displayName = 'Header'
