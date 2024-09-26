pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://www.jitpack.io" ) }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) }
    }
}

rootProject.name = "Android-UI-Test"
include(":app")
include(":custom-ui:sample")
include(":custom-ui:chart")
include(":screen:planfit")
include(":screen:ticket-booking-app:presentation")
include(":screen:ticket-booking-app:data")
include(":screen:ticket-booking-app:domain")
include(":screen:cat-image-provider:presentation")
include(":screen:cat-image-provider:data")
include(":screen:cat-image-provider:domain")
