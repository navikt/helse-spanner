import React from 'react'
import {InternalHeader, Spacer} from "@navikt/ds-react";
import styles from './Header.module.css'
import classNames from "classnames";
import {useQuery} from "react-query";
import {Spinner} from "../Spinner";
import {Feilmelding} from "../Feilmelding";

export const Header: React.FC<object> = ({ children }) => {
    const hentBrukerinfo = async() => {
        return await fetch("/api/meg")
            .then(response => response.json())
    }
    const { isLoading, isError, data, error } = useQuery(['brukerinfo'], hentBrukerinfo)

    const navn = isLoading ? '[laster]' : isError ? '[feil]' : data.navn
    const ident = isLoading ? '[laster]' : isError ? '[feil]' : data.ident
    return (
        <InternalHeader data-theme="spanner">
            <InternalHeader.Title href="/">
                <span className={classNames(styles.SpannSpan)}>🪣</span>er
            </InternalHeader.Title>
            {children}
            <Spacer/>
            <InternalHeader.User name={ navn } description={ ident }/>
        </InternalHeader>
    )
}
Header.displayName = 'Header'
