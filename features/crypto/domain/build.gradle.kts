import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.domain.get().pluginId)
    alias(libs.plugins.compose)
}

android.namespace = "wtf.speech.features.crypto.domain"

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
    }

    androidDependencies {
        implementation(libs.bouncycastle)
    }

    sourceSets {
        getByName("androidUnitTest") {
            dependencies {
                implementation(libs.junit)
            }
        }
    }
}