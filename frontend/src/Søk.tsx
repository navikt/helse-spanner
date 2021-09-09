import React from 'react'

export type SøkProps = {
  setAktørId: (aktørId?: string) => void
}

export const Søk = React.memo((props: SøkProps) => {
  const [søketekst, setSøketekst] = React.useState('')
  const sendSøk = () => {
    if (søketekst.trim() === '') {
      props.setAktørId(undefined)
    } else {
      props.setAktørId(søketekst.trim())
    }
  }

  return (
    <>
      <input value={søketekst} onChange={(e) => setSøketekst(e.target.value)} />
      <button onClick={sendSøk}>Søk</button>
    </>
  )
})
