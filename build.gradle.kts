plugins {
    with(libs.plugins) {
        alias(kotlin.jvm) apply false
        alias(kotlin.android) apply false
        alias(android.library) apply false
        alias(kotlin.cocoapods) apply false
        alias(android.application) apply false
        alias(kotlin.multiplatform) apply false
    }
}