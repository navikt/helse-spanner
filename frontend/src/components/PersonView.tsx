import ReactJson from 'react-json-view'
import React from 'react'
import { usePerson } from './PersonData'
import {PersonHeader} from "./PersonHeader";
import classNames from "classnames";
import styles from "./PersonView.module.css";


export const PersonView = () => {
  const person = usePerson()
  return (
    <div className={classNames(styles.PersonView)}  data-testid="person">
        <PersonHeader ></PersonHeader>
        <div className="PersonMenu"></div>
        <div className={"DetailView"}></div>
      <ReactJson src={person} collapsed={true} />
    </div>
  )
}
