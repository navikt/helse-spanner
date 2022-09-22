import React from 'react'
import styles from './Hendelser.module.css'
import commonStyles from '../../Common.module.css'
import { Hendelse } from './Hendelse'
import compareAsc from 'date-fns/compareAsc'
import classNames from 'classnames'
import { useRecoilState } from 'recoil'
import { skjulPåminnelserState, visBareFeilState } from '../../../state/state'
import {Hendelsekontekst} from '../../../state/model'

const erPåminnelse = (kontekst: Hendelsekontekst) =>
    kontekst.kontekstType == 'Påminnelse' || kontekst.kontekstType == 'Utbetalingshistorikk'

const harFeil = (kontekst: Hendelsekontekst) => kontekst.harError || kontekst.harWarning

export const Hendelser = React.memo(({ hendelser }: { hendelser: Hendelsekontekst[] }) => {
    const [visBareFeil, setVisBareFeil] = useRecoilState(visBareFeilState)
    const [skjulPåminnelser, setSkjulPåminnelser] = useRecoilState(skjulPåminnelserState)
    const toggleVisBareFeil = () => setVisBareFeil(!visBareFeil)
    const toggleVisPåminnelser = () => setSkjulPåminnelser(!skjulPåminnelser)

    const filtrerteHendelser = hendelser
        .filter((it) => !skjulPåminnelser || !erPåminnelse(it) || harFeil(it))
        .filter((it) => !visBareFeil || harFeil(it))

    const sorterteHendelser = filtrerteHendelser.sort((a, b) => compareAsc(a.opprettet, b.opprettet))

    return (
        <>
            <div className={styles.Header}>
                <button onClick={toggleVisBareFeil} className={classNames(visBareFeil && commonStyles.AktivKnapp)}>
                    Vis bare feil
                </button>
                <button
                    onClick={toggleVisPåminnelser}
                    className={classNames(skjulPåminnelser && commonStyles.AktivKnapp)}
                >
                    Skjul påminnelser og utbetalingshistorikk
                </button>
            </div>
            {sorterteHendelser.map((it) => {
                return (!visBareFeil || it.harError || it.harWarning) && <Hendelse kontekst={it} key={it.id} />
            })}
        </>
    )
})

Hendelser.displayName = 'Hendelser'
