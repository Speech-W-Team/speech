plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":shared:core:domain"))

    implementation(dependencyNotation = libs.ktor.core)
    implementation(dependencyNotation = libs.ktor.json)
    implementation(dependencyNotation = libs.ktor.serialization)

    testImplementation(dependencyNotation = libs.ktor.mock)
    testImplementation(dependencyNotation = libs.kotlin.test)
    testImplementation(dependencyNotation = libs.coroutines.test)

}