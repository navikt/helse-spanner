import React from "react";
import styles from "./person/Person.module.css";

export const Spinner = React.memo(() => (
    <div style={{padding: "10em"}}>
        <div className={styles.Spinner} data-testid="spinner"/>
        ️</div>
))
Spinner.displayName = "Spinner"