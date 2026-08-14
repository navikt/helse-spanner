plugins {
    id("no.nav.helse.sas.sas-deployable")
}

sasDeployable {
    mainClass = "FilbackendKt"
    imageName = "${rootProject.name}-ish"
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.logback.classic)
    implementation(libs.logstash.logback.encoder) {
        exclude("com.fasterxml.jackson.core")
        exclude("com.fasterxml.jackson.dataformat")
    }
}

tasks {
    processResources {
        from("${rootProject.projectDir}/frontend/dist") {
            into("static")
        }
    }
}
