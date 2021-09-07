import React from "react";
import {externals} from "./external/externals";
import {PersonDto} from "./external/dto";

export type SøkProps = {
    setPerson: (p: PersonDto) => void
}

export const Søk = React.memo((props: SøkProps) => {
    const [søketekst, setSøketekst] = React.useState("")
    return (
        <>
            <input
                value={søketekst}
                onChange={e => setSøketekst(e.target.value)}
            />
            <button onClick={e =>
                externals.backend.personForFnr(søketekst)
                    .then(props.setPerson)
            }>Søk
            </button>
        </>

    )
})