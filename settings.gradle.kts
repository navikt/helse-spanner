rootProject.name = "helse-spanner"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
include("spanner-frontend", "spanner-common", "spanner-backend")
