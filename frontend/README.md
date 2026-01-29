# Spanner Frontend

## Beskrivelse

Viser Spleis-JSON på en mer oversiktlig måte

## Lokal kjøring og utvikling

1. installer pnpm `brew install pnpm`
2. Når man oppdaterer pakker bruk `pnpm install`
3. Se til at du bruker node-versjonen som er satt i package.json under engines
4. Kjør `pnpm start`for å starte appen lokalt

## Tester
Det finnes et par tester som kan kjøres med `pnpm test`

## FAQ: 

### Hvordan setter jeg node-versjon?  

Endre i IntelliJ hvis man kjører kommandoer via intelliJ. Da må man inn i
settings -> languages & framworks -> Node.js
sette node interpreter -> node 24
sette package manager -> pnpm 16 (edited)

Hvis man bruker nvm, kjør `nvm use` i terminalen i prosjektmappen.

### Hvordan får jeg opp en lokal person?
For å åpne personer lokalt så kan man bruke `lokal`, `42` og `43` i søkefeltet i spanner.
Disse ligger lagret som json og kan finnes i mappen `./src/external/`
Det går fint at modde jsonPersonLokalFraSpleis helt vilkårligt så at man kan kjapt se hvordan ting ser ut
uten å måte ha en gyldig person.  

### Hva med farger, komponenter og styling?
Vi prøver i stort sett å bruke farger, komponenter og styling fra [aksel](https://aksel.nav.no/) sitt designsystem. 
