import com.android.build.api.dsl.LibraryExtension
import extensions.configureAndroid
import extensions.configureJvmToolchain
import extensions.configureKotlin
import extensions.configureKotlinJvm
import extensions.configureTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for setting up base settings for any kmp module
 */
class MultiPlatformCoreConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)
            }

            configureKotlinJvm()
            configureKotlin()

            extensions.configure<KotlinMultiplatformExtension> {
                configureTarget()
                configureJvmToolchain()
            }
        }
    }
}