import React from 'react'
import UserEvent from '@testing-library/user-event'
import { testApp } from '../../testApp'
import { createTestPerson } from '../../state/testData'
import { finnesIkke } from '../../external/feil'
import testingLibrary, { getByTestId } from '@testing-library/react'

function søk(text: string = '42') {
    UserEvent.type(søkefelt(), text)
    UserEvent.click(søkeknapp())
}

function søkefelt(container: HTMLElement = document.body) {
    return testingLibrary.getByTestId(container, 'søkefelt')
}

function søkeknapp(container: HTMLElement = document.body) {
    return testingLibrary.getByTestId(container, 'søkeknapp')
}

function feilmelding(container: HTMLElement = document.body) {
    return testingLibrary.getByTestId(container, 'feil-melding')
}

function person(container: HTMLElement = document.body) {
    return testingLibrary.getByTestId(container, 'person-header-fnr')
}

function respons(container: HTMLElement = document.body) {
    return testingLibrary.waitForElementToBeRemoved(() => getByTestId(container, 'spinner'))
}

test('bruker søker opp en person', async () => {
    const per = createTestPerson()
    console.log(`per aktor = ${per.aktørId}`)
    testApp([per])

    søk(per.aktørId)
    await respons()
    expect(person().textContent).toEqual(per.fødselsnummer)
})

test('bruker søker opp en person som ikke finnes', async () => {
    testApp([], { '43': new finnesIkke() })
    søk('43')
    await respons()
    expect(feilmelding().textContent).toContain('Personen finnes ikke i spleis')
})
