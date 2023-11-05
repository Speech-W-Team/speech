plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    androidTarget()
}

android {
    compileSdk = config.versions.android.compileSdk.get().toInt()
    namespace = "wtf.speech.vault.app"

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

    packaging {
        resources.excludes.add("META-INF/*")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    implementation(dependencyNotation = projects.shared)
    implementation(dependencyNotation = projects.core.cryptoKt)

    implementation(dependencyNotation = libs.compose.activity)
    implementation(dependencyNotation = libs.appCompat)
    implementation(dependencyNotation = libs.coreKtx)

    implementation(projects.features.passcode.ui)
    implementation(projects.features.passcode.domain)
}