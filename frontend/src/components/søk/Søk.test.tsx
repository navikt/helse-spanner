import { test, expect } from 'vitest'
import userEvent from '@testing-library/user-event'
import {testApp} from '../../testApp'
import {createTestPerson} from '../../state/testData'
import {lagfinnesIkkeFeil} from '../../external/feil'
import testingLibrary, {getByTestId} from '@testing-library/react'

async function søk(text: string = '42') {
    const user = userEvent.setup()
    await user.type(søkefelt(), text)
    await user.click(søkeknapp())
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

test.skip('bruker søker opp en person', async () => {
    const per = createTestPerson()
    console.log(`per aktor = ${per.aktørId}`)
    testApp([per])

    await søk(per.aktørId)
    console.log(location.pathname)
    await respons()
    console.log(`body!! ${document.body}`)
    expect(person().textContent).toEqual(per.fødselsnummer)
})

test.skip('bruker søker opp en person som ikke finnes', async () => {
    testApp([], { '43': lagfinnesIkkeFeil() })
    await søk('43')
    await respons()
    expect(feilmelding().textContent).toContain('Ressursen finnes ikke i spleis')
})
