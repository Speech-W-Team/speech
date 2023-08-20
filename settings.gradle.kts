@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("config") {
            from(files("gradle/config.version.toml"))
        }
    }
}

include(":vaultAndroid")
include(":shared")
include(":shared:core:domain")
include(":shared:core:ui")
include(":shared:compose-kit")
include(":shared:crypto:domain")

rootProject.name = "speech"