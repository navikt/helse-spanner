import React from 'react'
import styles from "./Header.module.css";
import classNames from "classnames";

export const Header: React.FC<object> = React.memo((
    {children,}) => (
    <header className={classNames(styles.Header)}>
        <span className={classNames(styles.SpannSpan)}>🪣</span>
        {children}
    </header>
))