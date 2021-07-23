plugins {
    kotlin("multiplatform") version "1.5.10" apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.multiplatform")

    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
