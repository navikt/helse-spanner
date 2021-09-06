val ktorVersion = "1.6.2"

plugins {
    kotlin("multiplatform") version "1.5.30" apply false
}

subprojects {
    ext {
        set("ktorVersion", ktorVersion)
    }
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
    }
}

tasks {
    withType<Wrapper> {
        gradleVersion = "7.1"
    }
}