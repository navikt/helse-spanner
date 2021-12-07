import React from 'react'
import {useQuery} from 'react-query'
import {personRequestFactory, useBackend} from '../../external/backend'
import {PersonView} from './PersonView'
import {PersonContext} from '../../state/contexts'
import {Spinner} from "../Spinner";
import {Feilmelding} from "../Feilmelding";

export type FetchPersonProps = {
    personId: { value: string }
}

export const PersonData = React.memo((props: FetchPersonProps) => {
    const backend = useBackend()
    try {
        const request = personRequestFactory(props.personId.value, backend)
        const { isLoading, isError, data, error } = useQuery(['person', props.personId.value], request)
        if (isLoading) {
            return <Spinner />
        }
        if (isError) {
            return <Feilmelding feil={error} />
        }
        return (
            <PersonContext.Provider value={data}>
                <PersonView />
            </PersonContext.Provider>
        )
    } catch (error) {
        return <Feilmelding feil={error} />
    }
})
PersonData.displayName="PersonData"
