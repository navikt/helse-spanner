import React from 'react'
import { parseISO } from 'date-fns/parseISO'
import styles from './PersonHeader.module.css'
import {usePerson} from '../../state/contexts'
import {speilUrl} from '../tree/links'
import {Box, Search} from "@navikt/ds-react";

export const PersonHeader = ({ toggleValgtTing }: { toggleValgtTing: (e: boolean, ting: string) => void }) => {
    const person = usePerson()
    return (
        <Box background="default" borderRadius="12">
            <dl className={styles.Ingress}>
                <dt>fnr</dt>
                <dd>
                    <p data-testid={'person-header-fnr'}>{person.fødselsnummer}</p>
                </dd>
                <dt>aktørid</dt>
                <dd>
                    <p>{person.aktørId}</p>
                </dd>
                <dt>opprettet</dt>
                <dd>
                    <p>{parseISO(person.opprettet).toDateString()}</p>
                </dd>
                <dt>🪞</dt>
                <dd>
                    <form action={speilUrl()} method="POST" target="_blank" rel="noreferrer">
                        <input type="hidden" name="aktørId" value={person.aktørId} />
                        <button type="submit">trykk her</button>
                    </form>
                </dd>

                <dt>Søk etter vedtaksperiode/utbetaling/behandling/vilkårsgrunnlag</dt>
                <dd>
                    <VedtaksperiodeSøk toggleValgtTing={toggleValgtTing} />
                </dd>
            </dl>
        </Box>
    )
}



const VedtaksperiodeSøk = ({ toggleValgtTing }: { toggleValgtTing: (e: boolean, ting: string) => void }) => {
    return <form onSubmit={(e) => {
                e.preventDefault()
            }}>
                <Search
                    id="vedtaksperiode-search"
                    label="Vedtaksperiodesøk"
                    size="small"
                    //value={søketekst}
                    variant="simple"
                    placeholder="<vedtaksperiodeID>"
                    onChange={(verdi) => {
                        toggleValgtTing(true, verdi)
                    }}
                    data-testid="vedtaksperiodeIdfelt"
                />
            </form>
}

