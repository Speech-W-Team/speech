import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.domain.get().pluginId)
    alias(libs.plugins.compose)
}

android.namespace = "wtf.speech.features.passcode.ui"

kotlin {
    commonDependencies {
        implementation(libs.voyager.navigator)
        implementation(projects.core.ui)
        implementation(projects.features.passcode.domain)
        implementation(projects.core.design)
        implementation(projects.compass.core)
    }

    androidDependencies {
        implementation(libs.bouncycastle)
    }

    sourceSets {
        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        getByName("androidUnitTest") {
            dependencies {
                implementation(libs.junit)
            }
        }
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

android {
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}
