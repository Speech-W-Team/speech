plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Core UI Module"
        homepage = "Link to the Core UI Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../../../vaultIos/Podfile")
        framework {
            baseName = "core-ui"
            isStatic = true
        }
        extraSpecAttributes["resources"] =
            "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":shared:core:domain"))
                api(project(":shared:compose-kit"))

                api("com.ionspin.kotlin:bignum:0.3.8")
                implementation("org.jetbrains.kotlinx:atomicfu:0.21.0")
            }
        }
    }
}



android {
    compileSdk = config.versions.android.compileSdk.get().toInt()
    namespace = "wtf.speech.shared.core.ui"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = config.versions.android.minSdk.get().toInt()
        targetSdk = config.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}