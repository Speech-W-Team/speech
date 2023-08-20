plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                val ktorVersion = "2.3.3"

                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                implementation(project(":shared"))
                implementation(project(":shared:crypto:domain"))
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "wtf.speech.vault.android.app"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "wtf.speech.vault.android.app"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
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
