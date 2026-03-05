import java.nio.file.Paths

val logbackClassicVersion = "1.5.25"
val logbackEncoderVersion = "8.0"
val ktorVersion = "3.2.3"

plugins {
    kotlin("jvm") version "2.3.0"
}

// Sett opp repositories basert på om vi kjører i CI eller ikke
// Jf. https://github.com/navikt/utvikling/blob/main/docs/teknisk/Konsumere%20biblioteker%20fra%20Github%20Package%20Registry.md
repositories {
    mavenCentral()
    if (providers.environmentVariable("GITHUB_ACTIONS").orNull == "true") {
        maven {
            url = uri("https://maven.pkg.github.com/navikt/maven-release")
            credentials {
                username = "token"
                password = providers.environmentVariable("GITHUB_TOKEN").orNull!!
            }
        }
    } else {
        maven("https://repo.adeo.no/repository/github-package-registry-navikt/")
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion") {
        exclude("com.fasterxml.jackson.core")
        exclude("com.fasterxml.jackson.dataformat")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    jar {
        archiveFileName.set("app.jar")

        manifest {
            attributes["Main-Class"] = "FilbackendKt"
            attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
                it.name
            }
        }


        from("${rootProject.projectDir}/frontend/dist") {
            into("static")
        }

        doLast {
            configurations.runtimeClasspath.get()
                .filter { it.name != "app.jar" }
                .forEach {
                    val file = File("${layout.buildDirectory.get()}/libs/${it.name}")
                    if (!file.exists()) it.copyTo(file)
                }
        }
    }
}

