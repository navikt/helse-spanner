import React from 'react'
import url from '../../../Påskebilder/GodPåske.svg'

export const GodPåske = ({ height = '4rem' }: { height?: string }) => (
    <img src={url} alt="GODPÅSKE" style={{ height, width: 'auto', alignSelf: 'center', marginLeft: '5px' }} />
)

