plugins {
    with(libs.plugins) {
        alias(kotlin.multiplatform) apply false
        alias(kotlin.cocoapods) apply false
        alias(kotlin.android) apply false
        alias(kotlin.jvm) apply false
        alias(android.library) apply false
        alias(android.application) apply false
    }
}