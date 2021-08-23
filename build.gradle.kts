plugins {
    kotlin("multiplatform") version "1.5.10" apply false
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

tasks {
    withType<Wrapper> {
        gradleVersion = "7.1"
    }
}