import React from 'react'
import {useQuery} from 'react-query'
import {useBackend} from '../../external/backend'
import {PersonView} from './PersonView'
import {PersonContext} from '../../state/contexts'
import {Spinner} from '../Spinner'
import {Feilmelding} from '../Feilmelding'
import {useParams} from 'react-router-dom'

export const PersonData = () => {
    const {personId} = useParams<string>()
    if (personId === undefined) return null
    const backend = useBackend()
    const request = () => backend.personForUUID(personId)
    const {isLoading, isError, data, error} = useQuery(['person', personId], request)
    if (isLoading) return <div style={{display: "grid", placeContent: "center"}}><Spinner/></div>
    if (isError) return <Feilmelding feil={error}/>
    return (
        <PersonContext.Provider value={data}>
            <PersonView/>
        </PersonContext.Provider>
    )
}
