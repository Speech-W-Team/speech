@file:Suppress("UnstableApiUsage")

import org.gradle.api.internal.FeaturePreviews

pluginManagement {
    includeBuild("build-logic")

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenCentral {
            content {
                includeModule(
                    "org.jetbrains.kotlinx",
                    "kotlinx-collections-immutable-jvm"
                )
            }
        }
        maven {
            name = "komputing/KHash GitHub Packages"
            url = uri("https://maven.pkg.github.com/komputing/KHash")
            credentials {
                username = "token"
                password =
                    "\u0039\u0032\u0037\u0034\u0031\u0064\u0038\u0033\u0064\u0036\u0039\u0061\u0063\u0061\u0066\u0031\u0062\u0034\u0061\u0030\u0034\u0035\u0033\u0061\u0063\u0032\u0036\u0038\u0036\u0062\u0036\u0032\u0035\u0065\u0034\u0061\u0065\u0034\u0032\u0062"
            }
        }

    }
}

dependencyResolutionManagement {
    enableFeaturePreview(FeaturePreviews.Feature.STABLE_CONFIGURATION_CACHE.name)
    enableFeaturePreview(FeaturePreviews.Feature.TYPESAFE_PROJECT_ACCESSORS.name)

    repositories {
        google()
        mavenCentral()
        maven {
            name = "komputing/KHash GitHub Packages"
            url = uri("https://maven.pkg.github.com/komputing/KHash")
            credentials {
                username = "token"
                password =
                    "\u0039\u0032\u0037\u0034\u0031\u0064\u0038\u0033\u0064\u0036\u0039\u0061\u0063\u0061\u0066\u0031\u0062\u0034\u0061\u0030\u0034\u0035\u0033\u0061\u0063\u0032\u0036\u0038\u0036\u0062\u0036\u0032\u0035\u0065\u0034\u0061\u0065\u0034\u0032\u0062"
            }
        }
    }

    versionCatalogs {
        create("config") {
            from(files("gradle/config.version.toml"))
        }
    }
}


include(":vaultAndroid")
include(":shared")
include(":core:domain")
include(":core:ui")
include(":core:design")
include(":features:crypto")
include(":features:crypto:domain")

rootProject.name = "speech"