import React, { PropsWithChildren } from 'react'
import {InternalHeader, Spacer} from "@navikt/ds-react";
import styles from './Header.module.css'
import {useQuery} from "@tanstack/react-query";
import {Link} from "react-router-dom";

export const Header = ({ children }: PropsWithChildren) => {
    const hentBrukerinfo = async() => {
        return await fetch("/api/meg")
            .then(response => response.json())
    }
    const { isLoading, isError, data } = useQuery({ queryKey: ['brukerinfo'], queryFn: hentBrukerinfo })

    const navn = isLoading ? '[laster]' : isError ? '[feil]' : data?.navn ?? '[ukjent]'
    const ident = isLoading ? '[laster]' : isError ? '[feil]' : data?.ident ?? '[ukjent]'
    return (
        <InternalHeader data-theme="spanner">
            <InternalHeader.Title href="/">
                <span className={styles.SpannSpan}>🪣</span>er
            </InternalHeader.Title>
            {children}
            <Spacer/>
            <Link to={"/hotkeys"} className={styles.Lenke}>Hotkeys</Link>
            <InternalHeader.User className={styles.User} name={ navn } description={ ident }/>
        </InternalHeader>
    )
}
