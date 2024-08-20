
# 🪣 Spanner 🪣

## Beskrivelse
Parser og viser Spleis-JSON på en mer oversiktlig måte

## Koding
Bruker husky for å kjøre en commit hook som formaterer og linter koden.
Det er mulig man må kjøre `npm prepare` i frontend-mappa for å installere dette.
Også mulig man må kjøre `git config core.hooksPath .husky/_`.

## Henvendelser
Spørsmål knyttet til koden eller prosjektet kan stilles som issues her på GitHub.
### For NAV-ansatte
Interne henvendelser kan sendes via Slack i kanalen #område-helse.

## Lokal utvikling
Det er mulig å teste lokalt med OAuth, audit-logging og ekte kall til backend.

#### Hva trenger du?
- docker, docker compose

1. `./gradlew build`
2. `docker build -t helse-spanner-spanner -f Dockerfile backend` (docker compose klarer ikke å bygge image selv, pga. layouten i repoet)
3. `docker compose up`, legg på `-d` hvis du vil at den skal kjøre detached (altså i bakgrunnen)
4. Stoppes ved å kjøre `docker compose down` i samme mappe man kjørte `up` fra

Da når du spanner på http://localhost:8080.

Hvis du har gjort endringer i backend-koden må du selv sørge for bygging av nytt image, enten eksplisitt med
`docker build -t helse-spanner-spanner -f Dockerfile backend`, eller ved å slette det forrige imaget med
`docker image rm --force helse-spanner-spanner`.

Hele runden: `./gradlew build && docker build -t helse-spanner-spanner -f Dockerfile backend && docker compose up -d`

![Eventuelt sånn](https://github.com/navikt/helse-spanner/blob/master/docs/spanner-instruksjoner.png?raw=true)
