import ReactJson from 'react-json-view'
import React from 'react'
import { usePerson } from './Person'

export const PersonVisning = () => {
  const person = usePerson()
  return (
    <>
      <h1 data-testid="personTittel">{person.aktørId}</h1>
      <ReactJson src={person} collapsed={true} />
    </>
  )
}