
# 🪣 Spanner 🪣

## Beskrivelse
Parser og viser Spleis-JSON på en mer oversiktlig måte

## Henvendelser
Spørsmål knyttet til koden eller prosjektet kan stilles som issues her på GitHub.
### For NAV-ansatte
Interne henvendelser kan sendes via Slack i kanalen #område-helse.

## Lokal utvikling
Det er mulig å teste lokalt med OAuth, audit-logging og ekte kall til backend.

#### Hva trenger du?
- docker, docker compose

1. `./gradlew build`
2. `docker compose up` (eller `docker-compose up`)

Da når du spanner på http://localhost:8080.

Hvis du har gjort endringer i backend-koden må du selv sørge for bygging av nytt image, enten eksplisitt med
`docker build .` eller ved å slette det forrige imaget med
`docker image rm --force helse-spanner-spanner`.
