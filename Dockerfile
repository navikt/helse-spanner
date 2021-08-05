FROM navikt/java:15

COPY spanner-backend/build/libs/*.jar ./
COPY spanner-frontend/build/libs/*.jar ./