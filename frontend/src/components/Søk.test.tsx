import React from 'react'
import UserEvent from '@testing-library/user-event'
import {testApp} from '../TestApp'
import {createTestPerson} from "../testData";

test('bruker søker opp en person', async () => {
  const testRender = testApp([createTestPerson()])
  UserEvent.type(testRender.getByTestId('søkefelt'), '42')
  UserEvent.click(testRender.getByTestId('søkeknapp'))
  await testRender.findByTestId('personTittel')
  const title = testRender.getByTestId('personTittel').textContent

  expect(title).toEqual('42')
})
