plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.5.30"
}

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser()
    }
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains:annotations:20.1.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}
