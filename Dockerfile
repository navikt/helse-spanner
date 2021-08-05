FROM navikt/java:15

ENV APP_JAR=spanner-backend-jvm.jar
COPY spanner-backend/build/libs/*.jar ./