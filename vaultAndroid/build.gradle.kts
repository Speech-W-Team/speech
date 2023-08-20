plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                val ktorVersion = "2.3.3"

                implementation(libs.ktor.client.okhttp)
                implementation(libs.ktor.ktor.serialization.kotlinx.json)

                implementation(project(":shared"))
                implementation(project(":shared:crypto:domain"))
            }
        }
    }
}

android {
    compileSdk = config.versions.android.compileSdk.get().toInt()
    namespace = "wtf.speech.vault.android.app"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        with(config.versions) {
            applicationId = android.id.get()
            minSdk = android.minSdk.get().toInt()
            targetSdk = android.minSdk.get().toInt()
            versionCode = android.versionCode.get().toInt()
            versionName = android.versionName.get()
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    buildFeatures {
        compose = true
    }


    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}
