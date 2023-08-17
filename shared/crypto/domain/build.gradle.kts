plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization") version "1.9.0"

}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":shared:core:domain"))
    testImplementation("org.testng:testng:6.9.6")

    val ktor_version = "2.3.3"

    implementation("com.ionspin.kotlin:bignum:0.3.8")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-json:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0-RC")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")

}