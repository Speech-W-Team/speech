plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.android.library)
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
        podfile = project.file("../../../vaultIos/Podfile")
        framework {
            baseName = "crypto-domain"
            isStatic = true
        }
        extraSpecAttributes["resources"] =
            "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            //create custom compilation for commonMain
            dependencies {
                implementation(project(":shared:core:domain"))

                val ktorVersion = "2.3.3"

                api("com.ionspin.kotlin:bignum:0.3.8")
                implementation("com.ionspin.kotlin:multiplatform-crypto-libsodium-bindings:0.8.9")
                implementation("com.github.komputing.khash:sha256:1.1.3")
                implementation("com.github.komputing.khash:keccak:1.1.3")
                implementation("com.github.komputing.khash:ripemd160:1.1.3")
                api(libs.ktor.core)
                api(libs.ktor.json)
                api(libs.ktor.serialization)

                api("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            }
        }
        val commonMainnet by creating {
            dependsOn(commonMain)
        }
        val commonTestnet by creating {
            dependsOn(commonMain)
        }
        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(libs.bouncycastle)

                implementation("junit:junit:4.13.2")
                implementation("org.mockito:mockito-core:3.11.0")
                implementation("org.mockito:mockito-android:3.11.0")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }

    // create custom compilation for commonMain
}

android {
    compileSdk = config.versions.android.compileSdk.get().toInt()
    namespace = "wtf.speech.shared.crypto.domain"

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

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}