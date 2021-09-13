import ReactJson from 'react-json-view'
import React from 'react'
import {PersonHeader} from "./PersonHeader";
import classNames from "classnames";
import styles from "./PersonView.module.css";
import {PersonTree} from "./PersonTree";
import {usePerson} from "../contexts";
import {Content} from "./Content";



export const PersonView = () => {
    const person = usePerson()

    return (
        <div className={classNames(styles.PersonView)} data-testid="person">
            <PersonHeader/>
            <PersonTree/>
            <Content />
        </div>
    )
}
