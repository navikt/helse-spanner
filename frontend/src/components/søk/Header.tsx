import React, {PropsWithChildren} from 'react'
import {Button, InternalHeader, Spacer} from "@navikt/ds-react";
import styles from './Header.module.css'
import {useQuery} from "@tanstack/react-query";
import {Link} from "react-router-dom";
import {SunIcon} from "@navikt/aksel-icons";
import {useAtom} from "jotai";
import {themeAtom} from "../../state/state";
import {GodPåske} from './påskebilder/GodPåske'
import {isEasterPeriod} from '../../utils/isEaster'

const imgBg = '/background/DoomsdayBakgrunn.svg'
const easterLightBg = '/easter/PåskeLightmodeBakgrunn.svg'
const easterDarkBg = '/easter/PåskeDarkmodeBakgrunn.svg'

export const Header = ({ children }: PropsWithChildren) => {
    const [theme, setTheme] = useAtom(themeAtom)
    const toggleTheme = () => setTheme(theme === 'dark' ? 'light' : 'dark')
    const isPåske = isEasterPeriod()

    const hentBrukerinfo = async() => {
        return await fetch("/api/meg")
            .then(response => response.json())
    }
    const { isLoading, isError, data } = useQuery({ queryKey: ['brukerinfo'], queryFn: hentBrukerinfo })

    const navn = isLoading ? '[laster]' : isError ? '[feil]' : data?.navn ?? '[ukjent]'
    const ident = isLoading ? '[laster]' : isError ? '[feil]' : data?.ident ?? '[ukjent]'
    const backgroundImage = isPåske
        ? `url(${theme === 'dark' ? easterDarkBg : easterLightBg})`
        : `url(${imgBg})`

    return (
        <InternalHeader className={styles.InternalHeader} style={{ backgroundImage }}>
            <InternalHeader.Title href="/">
                <span className={styles.SpannSpan}>🪣</span>er
            </InternalHeader.Title>
            {children}
            {isPåske && <GodPåske />}
            <Spacer />
            <Button
                variant="tertiary-neutral"
                icon={
                    theme === 'dark' ? (
                        <SunIcon title="Bytt til lyst tema" />
                    ) : (
                        <img rel="icon" src="/darkmode.png" title={'Bytt til mørkt tema'} alt={""}/>
                    )
                }
                onClick={toggleTheme}
            />
            <Link to={'/hotkeys'} className={styles.Lenke}>
                Hotkeys
            </Link>
            <Link to={'/spiskammerset'} className={styles.Lenke}>
                Spiskammerset
            </Link>
            <InternalHeader.User className={styles.User} name={navn} description={ident} />
        </InternalHeader>
    )
}
