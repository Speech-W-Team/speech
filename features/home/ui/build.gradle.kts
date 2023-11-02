import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    alias(libs.plugins.compose)
    id(libs.plugins.speech.multiplatform.ui.get().pluginId)
}

android.namespace = "wtf.speech.features.home.ui"

commonDependencies {
    implementation(projects.compass.core)
}

kotlin {
    commonDependencies {
        implementation(projects.core.ui)

        implementation(projects.features.wallet.ui)
        implementation(projects.features.wallet.domain)
    }

    androidDependencies {
        implementation(libs.bouncycastle)
    }

    sourceSets {
        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        getByName("androidUnitTest") {
            dependencies {
                implementation(libs.junit)
            }
        }
        val commonMain by getting
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
}

android {
    compileSdk = config.versions.android.compileSdk.get().toInt()
    namespace = "wtf.speech.features.home.ui"

//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = config.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        androidDependencies {
            implementation(libs.bouncycastle)
        }
    }
}
