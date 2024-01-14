import React from 'react'
import styles from './PersonTree.module.css'

export default function SporingLenke({ url }: { url: string }) {
    return (
        <a className={styles.SporingLenkeKnapp} href={url} target="_blank">
            🔎
        </a>
    )
}
