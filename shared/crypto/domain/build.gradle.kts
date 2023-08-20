plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.9.0"

}
kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Crypto Domain Module"
        homepage = "Link to the Crypto Domain Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("/Users/usman/StudioProjects/speech/speech/vaultIos/Podfile")
        framework {
            baseName = "crypto-domain"
            isStatic = true
        }
        extraSpecAttributes["resources"] =
            "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:core:domain"))

                val ktorVersion = "2.3.3"

                api("com.ionspin.kotlin:bignum:0.3.8")
                api("io.ktor:ktor-client-core:$ktorVersion")
                api("io.ktor:ktor-client-json:$ktorVersion")
//                api("io.ktor:ktor-client-serialization:$ktorVersion")

                api("io.ktor:ktor-client-logging:$ktorVersion")
                api("io.ktor:ktor-client-content-negotiation:$ktorVersion")
//                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0-RC")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "wtf.speech.shared.crypto.domain"

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

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}