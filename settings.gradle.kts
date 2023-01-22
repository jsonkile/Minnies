pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Minnies"
include(":app")
include(":shared")
include(":notifications")
include(":auth")
include(":cart")
include(":orders")
include(":products")
include(":database")
