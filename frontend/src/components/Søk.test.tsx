import React from 'react'
import UserEvent from '@testing-library/user-event'
import {testApp} from '../testApp'
import {createTestPerson} from "../testData";
import {finnesIkke} from "../external/feil";

test('bruker søker opp en person', async () => {
  const testRender = testApp([createTestPerson()])
  UserEvent.type(testRender.getByTestId('søkefelt'), '42')
  UserEvent.click(testRender.getByTestId('søkeknapp'))
  await testRender.findByTestId('personTittel')
  const title = testRender.getByTestId('personTittel').textContent

  expect(title).toEqual('42')
})

test('bruker søker opp en person som ikke finnes', async () => {
  const testRender = testApp([], {"40": new finnesIkke})
  UserEvent.type(testRender.getByTestId('søkefelt'), '40')
  UserEvent.click(testRender.getByTestId('søkeknapp'))
  await testRender.findByTestId('feil-melding')
  const melding = testRender.getByTestId('feil-melding').textContent

  expect(melding).toContain('Feil fra server')
})
