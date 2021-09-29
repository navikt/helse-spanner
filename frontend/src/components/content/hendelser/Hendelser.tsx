import React from 'react'
import styles from './Hendelser.module.css'
import { Hendelse } from './Hendelse'
import compareAsc from 'date-fns/compareAsc'
import classNames from 'classnames'
import { useRecoilState } from 'recoil'
import { skjulPåminnelserState, visBareFeilState } from '../../../state/state'
import { Kontekst } from '../../../state/model'

const erPåminnelse = (kontekst: Kontekst) =>
    kontekst.kontekstType == 'Påminnelse' || kontekst.kontekstType == 'Utbetalingshistorikk'

const harFeil = (kontekst: Kontekst) => kontekst.harError || kontekst.harWarning

export const Hendelser = React.memo(({ hendelser }: { hendelser: Kontekst[] }) => {
    const [visBareFeil, setVisBareFeil] = useRecoilState(visBareFeilState)
    const [skjulPåminnelser, setSkjulPåminnelser] = useRecoilState(skjulPåminnelserState)
    const toggleVisBareFeil = () => setVisBareFeil(!visBareFeil)
    const toggleVisPåminnelser = () => setSkjulPåminnelser(!skjulPåminnelser)

    const filtrerteHendelser = hendelser
        .filter((it) => !skjulPåminnelser || !erPåminnelse(it))
        .filter((it) => !visBareFeil || harFeil(it))
    skjulPåminnelser ? hendelser.filter((it) => !erPåminnelse(it)) : hendelser

    const sorterteHendelser = filtrerteHendelser.sort((a, b) => compareAsc(a.opprettet, b.opprettet))

    return (
        <div className={styles.Hendelser}>
            <button onClick={toggleVisBareFeil} className={classNames(visBareFeil && styles.ViewButtonSelected)}>
                Vis bare feil
            </button>
            <button
                onClick={toggleVisPåminnelser}
                className={classNames(skjulPåminnelser && styles.ViewButtonSelected)}
            >
                Skjul påminnelser og utbetalingshitorikk
            </button>
            {sorterteHendelser.map((it) => {
                return (!visBareFeil || it.harError || it.harWarning) && <Hendelse kontekst={it} key={it.id} />
            })}
        </div>
    )
})

Hendelser.displayName = 'Hendelser'
