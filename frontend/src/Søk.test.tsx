import React from 'react'
import TestingLibrary from '@testing-library/react'
import UserEvent from '@testing-library/user-event'
import { TestApp } from './main'
import {PersonVisning} from "./PersonVisning";

test('bruker søker opp en person', async () => {

  const testRender = TestingLibrary.render(<TestApp />)
  UserEvent.type(testRender.getByTestId('søkefelt'), 'banan')
  UserEvent.click(testRender.getByTestId('søkeknapp'))
  await testRender.findByTestId('personTittel')
  const title = testRender.getByTestId('personTittel').textContent

  expect(title).toEqual('42')
})
