plugins {
    id("no.nav.helse.sas.sas-deployable")
}

sasDeployable {
    mainClass = "no.nav.spanner.AppKt"
}

dependencies {
    implementation(libs.tbd.libs.azure.token.client.default)
    implementation(libs.tbd.libs.spurtedu.client)
    implementation(libs.tbd.libs.speed.client)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.call.id)
    implementation(libs.ktor.server.forwarded.header)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.sessions)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)

    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)

    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.logback.syslog4j)
    implementation(libs.konfig)

    implementation(libs.logback.classic)
    implementation(libs.logstash.logback.encoder) {
        exclude("com.fasterxml.jackson.core")
        exclude("com.fasterxml.jackson.dataformat")
    }

    implementation(libs.jjwt.api)
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)

    testImplementation(libs.mockk)
    testImplementation(libs.mock.oauth2.server)
    testImplementation(libs.ktor.server.test.host)
}

tasks {
    processResources {
        from("${rootProject.projectDir}/frontend/dist") {
            into("static")
        }
    }
}
