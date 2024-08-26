# Lokal Spanner

Hva er greia med `--platform linux/x86_64` ?
Jo, den trenger man på Mx-prosessorer fra Apple 🤷‍

```
gcloud auth configure-docker europe-north1-docker.pkg.dev
gcloud auth print-access-token | docker login -u oauth2accesstoken --password-stdin https://europe-north1-docker.pkg.dev
docker pull europe-north1-docker.pkg.dev/nais-management-233d/tbd/helse-spanner-lokal:latest --platform linux/x86_64
docker run --name lokal-spanner -p 3000:3000 -v $HOME/.spanner:/.spanner -e SPANNER_ROOT=/.spanner europe-north1-docker.pkg.dev/nais-management-233d/tbd/helse-spanner-lokal:latest
```

Alle personer du har på `$HOME/.spanner/personer` kan du nå åpne (de må da hete `<uuid>.json` og være det du åpner i Spanner)
Det er også samme greia med `$HOME/.spanner/hendelser` - men det er ikke testet. Men burde funke.

Nå kan du åpne [Lokal Spanner](http://localhost:3000)