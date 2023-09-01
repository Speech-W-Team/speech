import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
}

android.namespace = "wtf.speech.core.ui"

commonDependencies {
    api(projects.core.domain)
    api(projects.core.design)

    api(libs.ionspin.bignum)
    implementation(libs.kotlinx.atomicfu)
}