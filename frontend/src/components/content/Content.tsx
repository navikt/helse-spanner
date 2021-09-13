import React from 'react'
import { JsonView } from './JsonView'

export const Content = React.memo(() => {
    return (
        <div>
            <JsonView />
        </div>
    )
})
