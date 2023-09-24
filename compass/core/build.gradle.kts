import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
    kotlin("plugin.serialization") version "1.9.0"
    alias(libs.plugins.compose)
}

android.namespace = "wtf.speech.compass.core"

kotlin {
    sourceSets {
        val commonMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

kotlin {
    commonDependencies {
        api(compose.runtime)
        api(compose.foundation)
        api(compose.material3)
        api(compose.ui)

        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        api(compose.components.resources)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.atomicfu)
    }

    androidDependencies {
        api(compose.uiTooling)
        api(compose.preview)
    }
}