import React from 'react'
import { JsonView } from './JsonView'
import {AktivitetsloggView} from "./AktivitetsloggView";

export const Content = React.memo(() => {
    return (
        <div>
            <JsonView />
            <AktivitetsloggView/>
        </div>
    )
})
