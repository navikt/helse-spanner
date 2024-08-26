# Lokal Spanner

Dette er en readme i startgropen.

```
gcloud auth configure-docker europe-north1-docker.pkg.dev
gcloud auth print-access-token | docker login -u oauth2accesstoken --password-stdin https://europe-north1-docker.pkg.dev
docker europe-north1-docker.pkg.dev/nais-management-233d/tbd/helse-spanner-lokal:latest
```