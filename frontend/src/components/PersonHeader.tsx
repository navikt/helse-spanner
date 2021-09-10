import React from "react";
import {usePerson} from "./PersonData";
import parseISO from 'date-fns/parseISO';
import classNames from "classnames";
import styles from "./PersonHeader.module.css";

export function PersonHeader() {
    const person = usePerson()
    return (
        <div className={classNames(styles.PersonHeader)} >
            <dl>
                <dt>fnr</dt>
                    <dd><p data-testid={"person-header-fnr"}>{person.fødselsnummer}</p></dd>
                <dt>aktørid</dt>
                    <dd><p>{person.aktørId}</p></dd>
                <dt>opprettet</dt>
                    <dd><p>{parseISO(person.opprettet).toDateString()}</p></dd>
            </dl>
        </div>
    )
}