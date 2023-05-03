import React from 'react'
import { useQuery } from 'react-query'
import { personRequestFactory, useBackend } from '../../external/backend'
import { PersonView } from './PersonView'
import { PersonContext } from '../../state/contexts'
import { Spinner } from '../Spinner'
import { Feilmelding } from '../Feilmelding'
import { useParams } from 'react-router-dom'

export const PersonData = () => {
    const { personId } = useParams<string>()
    if (personId === undefined) return null
    const backend = useBackend()
    try {
        const request = personRequestFactory(personId, backend)
        const { isLoading, isError, data, error } = useQuery(['person', personId], request)
        return isLoading ? (
            <Spinner />
        ) : isError ? (
            <Feilmelding feil={error} />
        ) : (
            <PersonContext.Provider value={data}>
                <PersonView />
            </PersonContext.Provider>
        )
    } catch (error) {
        return <Feilmelding feil={error} />
    }
}
PersonData.displayName = 'PersonData'
