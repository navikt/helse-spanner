import React from 'react'
import {PersonDto} from '../external/dto'
import {useQuery} from 'react-query'
import {useBackend} from '../external/backend'
import * as Utils from '../utils'
import {PersonVisning} from "./PersonVisning";
import classNames from "classnames";
import styles from "./Person.module.css";

export type FetchPersonProps = {
    aktørId: string
}

export const PersonContext = Utils.createContext<PersonDto>()
export const usePerson = () => Utils.useContext(PersonContext)

export const Person = React.memo((props: FetchPersonProps) => {
    const backend = useBackend()
    const {isSuccess, isLoading, isError, data, error} = useQuery(['person', props.aktørId], () =>
        backend.personForAktørId(props.aktørId)
    )
    if (isSuccess) {
        return <PersonContext.Provider value={data}>
            <PersonVisning/>
        </PersonContext.Provider>
    } else if (isError) {
        return (<div className={classNames(styles.FeilMelding)} data-testid="feil-melding">
            <p>
                {(error instanceof Error) ? error.message : "undefined error"}
            </p>
        </div>)
    } else if (isLoading) {
        return (<div >
            <p>
                Laster...
            </p>
        </div>)
    }
    throw Error("Burde ikke kunne havne her")
})
