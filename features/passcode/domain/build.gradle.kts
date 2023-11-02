import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.domain.get().pluginId)
    alias(libs.plugins.compose)
}

android.namespace = "wtf.speech.features.passcode.domain"

kotlin {
    commonDependencies {
        implementation(compose.foundation)
        implementation(compose.ui)
        implementation(compose.runtime)
        implementation(libs.kotlinx.datetime)
        implementation(libs.ionspin.bignum)
        implementation(libs.ionspin.cryptolibsodium)
        implementation(libs.khash.keccak)
        implementation(libs.khash.ripemd160)
        implementation(libs.khash.sha256)

        api(projects.core.domain)
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
