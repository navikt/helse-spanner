rootProject.name = "helse-spanner"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
include("frontend")
//include("common")//, "frontend")
include("common")
