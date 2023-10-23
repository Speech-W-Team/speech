import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
}

android.namespace = "wtf.speech.shared.core.domain"

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

commonDependencies {
    api(libs.kotlinx.datetime)
    api(libs.coroutines.core)
    api(libs.ionspin.bignum)
}