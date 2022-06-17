# 🪣 Spanner 🪣
[![master](https://github.com/navikt/helse-spanner/actions/workflows/master.yml/badge.svg?branch=master)](https://github.com/navikt/helse-spanner/actions/workflows/master.yml)

## Beskrivelse
Parser og viser Spleis-JSON på en mer oversiktlig måte

## Lokal utvikling
For å teste lokalt med OAuth, audit-logging og ekte kall til backend

**Hva trenger du?**
- docker, docker compose

1. `./gradlew build`
2. `docker compose up`

## Oppgradering av gradle wrapper
Finn nyeste versjon av gradle her: https://gradle.org/releases/

```./gradlew wrapper --gradle-version $gradleVersjon```

## Henvendelser
Spørsmål knyttet til koden eller prosjektet kan stilles som issues her på GitHub.

### For NAV-ansatte
Interne henvendelser kan sendes via Slack i kanalen #område-helse.