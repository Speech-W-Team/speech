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

    val file = project.file("../../")
    println("RELATIVEPATH" + file.absolutePath)
    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Core Domain Module"
        homepage = "Link to the Core Domain Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../../../vaultIos/Podfile")
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
                api(libs.kotlinx.datetime)
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                api("com.ionspin.kotlin:bignum:0.3.8")
            }
        }
    }
}



android {
    compileSdk = config.versions.android.compileSdk.get().toInt()
    namespace = "wtf.speech.shared.core.domain"

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
}