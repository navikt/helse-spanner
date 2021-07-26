plugins {
    id("org.jetbrains.compose") version "0.5.0-build228"
}

kotlin {
    js(IR) {
        useCommonJs()
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation(npm("json-formatter-js", "2.3.4"))
                implementation(project(":spanner-common"))
            }
        }
    }
}
