import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
}

android.namespace = "wtf.speech.compass.core"

commonDependencies {
    api(projects.core.domain)
    api(projects.core.design)

    api(libs.ionspin.bignum)
    implementation(libs.kotlinx.atomicfu)
}

androidDependencies {
    implementation("androidx.compose.ui:ui-test-junit4:1.5.4")
}

android {
    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.5.0"
}