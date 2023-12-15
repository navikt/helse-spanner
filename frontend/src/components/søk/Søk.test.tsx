import React from 'react'
import UserEvent from '@testing-library/user-event'
import { testApp } from '../../testApp'
import { createTestPerson } from '../../state/testData'
import { lagfinnesIkkeFeil } from '../../external/feil'
import testingLibrary, { getByTestId } from '@testing-library/react'
import {act} from "react-dom/test-utils";
import {util} from "prettier";
import skip = util.skip;

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

function uuidRespons(container: HTMLElement = document.body) {
    return console.log(testingLibrary.prettyDOM())
}

function respons(container: HTMLElement = document.body) {
    return testingLibrary.waitForElementToBeRemoved(() => getByTestId(container, 'spinner'))
}

test.skip('bruker søker opp en person', async () => {
    const per = createTestPerson()
    console.log(`per aktor = ${per.aktørId}`)
    testApp([per])

    act(() => søk(per.aktørId))
    console.log(location.pathname)
    await respons()
    console.log(`body!! ${document.body}`)
    expect(person().textContent).toEqual(per.fødselsnummer)
})

test.skip('bruker søker opp en person som ikke finnes', async () => {
    testApp([], { '43': lagfinnesIkkeFeil() })
    søk('43')
    await respons()
    expect(feilmelding().textContent).toContain('Ressursen finnes ikke i spleis')
})
