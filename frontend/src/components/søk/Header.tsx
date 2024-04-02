import React from 'react'
import {InternalHeader, Spacer} from "@navikt/ds-react";
import styles from './Header.module.css'
import {useQuery} from "react-query";

export const Header: React.FC<object> = ({ children }) => {
    const hentBrukerinfo = async() => {
        return await fetch("/api/meg")
            .then(response => response.json())
    }
    const { isLoading, isError, data } = useQuery(['brukerinfo'], hentBrukerinfo)

    const navn = isLoading ? '[laster]' : isError ? '[feil]' : data.navn
    const ident = isLoading ? '[laster]' : isError ? '[feil]' : data.ident
    return (
        <InternalHeader data-theme="spanner">
            <InternalHeader.Title href="/">
                <span className={styles.SpannSpan}>🪣</span>er
            </InternalHeader.Title>
            {children}
            <Spacer/>
            <InternalHeader.User className={styles.User} name={ navn } description={ ident }/>
        </InternalHeader>
    )
}
Header.displayName = 'Header'
