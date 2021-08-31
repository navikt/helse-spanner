plugins {
    kotlin("js")
}

kotlin {
    js {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }
}


dependencies {

    //React, React DOM + Wrappers (chapter 3)
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.239-kotlin-1.5.30")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.239-kotlin-1.5.30")
    implementation(npm("react", "17.0.2"))
    implementation(npm("react-dom", "17.0.2"))

    //Kotlin Styled (chapter 3)
//    implementation("org.jetbrains:kotlin-styled:latest.release")

    //Coroutines (chapter 8)
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:latest.release")
}
