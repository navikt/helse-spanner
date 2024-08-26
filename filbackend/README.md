# Spannerish

Hva er greia med `--platform linux/x86_64` ?
Jo, den trenger man på Mx-prosessorer fra Apple 🤷‍

```
gcloud auth configure-docker europe-north1-docker.pkg.dev
gcloud auth print-access-token | docker login -u oauth2accesstoken --password-stdin https://europe-north1-docker.pkg.dev
docker pull europe-north1-docker.pkg.dev/nais-management-233d/tbd/helse-spanner-ish:latest --platform linux/x86_64
docker run --name spannerish -p 3000:3000 -v $HOME/.spanner:/.spanner -e SPANNER_ROOT=/.spanner europe-north1-docker.pkg.dev/nais-management-233d/tbd/helse-spanner-ish:latest
```

Alle personer du har på `$HOME/.spanner/personer` kan du nå åpne (de må da hete `<uuid>.json` og være det du åpner i Spanner)
Det er også samme greia med `$HOME/.spanner/hendelser` - men det er ikke testet. Men burde funke.

Du kan også laste opp personer/hendelser ved POST mot `/api/{person/hendelse}/{uuid}`. Disse lagres in-memory, og appen vil slå opp her før den sjekker filområdet.

Nå kan du åpne [Spannerish](http://localhost:3000)