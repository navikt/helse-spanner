import React from 'react'
import lightUrl from '../../../Påskebilder/PåskeTekstLightmode.svg'
import darkUrl from '../../../Påskebilder/PåskeTekstDarkmode.svg'
import { useAtomValue } from 'jotai'
import { themeAtom } from '../../../state/state'

export const GodPåske = ({ height = '4rem' }: { height?: string }) => {
    const theme = useAtomValue(themeAtom)

    return (
        <img
            src={theme === 'dark' ? darkUrl : lightUrl}
            alt="GODPÅSKE"
            style={{ height, width: 'auto', alignSelf: 'center', marginLeft: '5px' }}
        />
    )
}

