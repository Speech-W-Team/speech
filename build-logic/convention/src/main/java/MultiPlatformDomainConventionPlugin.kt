import com.android.build.api.dsl.LibraryExtension
import extensions.commonDependencies
import extensions.configureAndroid
import extensions.configureJvmToolchain
import extensions.configureKotlin
import extensions.configureKotlinJvm
import extensions.configureTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for setting up base settings for domain feature module
 */
class MultiPlatformDomainConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }

            val libraryExtension = extensions.getByType<LibraryExtension>()
            val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()

            configureAndroid(extension = libraryExtension)

            configureKotlinJvm()
            configureKotlin()

            with(kmpExtension) {
                configureTarget()
                configureJvmToolchain()
            }

            commonDependencies {
                implementation(project(":core:domain"))
            }
        }
    }
}