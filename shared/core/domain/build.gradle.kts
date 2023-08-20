plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Core Domain Module"
        homepage = "Link to the Core Domain Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("/Users/usman/StudioProjects/speech/speech/vaultIos/Podfile")
        framework {
            baseName = "core-domain"
            isStatic = true
        }
        extraSpecAttributes["resources"] =
            "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                api("com.ionspin.kotlin:bignum:0.3.8")
            }
        }
    }
}



android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "wtf.speech.shared.core.domain"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}