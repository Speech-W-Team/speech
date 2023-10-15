import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
}

android {
    namespace = "wtf.speech.shared.core.cryptokt"
    sourceSets["main"].jniLibs.srcDir("src/androidMain/jniLibs")
}

val templates = mapOf(
    "arm64-v8a" to "aarch64-linux-android",
    "armeabi-v7a" to "armv7-linux-androideabi",
    "x86" to "i686-linux-android"
)

val rustBasePath = "$rootDir/crypto"
templates.forEach { (arch, target) ->
    tasks.create(
        name = "rustCryptoBuild_$arch",
        type = Exec::class,
        configuration = {
            description = "Building target for $arch"
            workingDir(rustBasePath)
            commandLine("cargo", "build", "--target=$target", "--release")
        }
    )

    tasks.create(
        name = "rustCryptoDeploy_$arch",
        type = Copy::class,
        configuration = {
            from("$rustBasePath/target/$target/release")
            include("*.so")
            into("src/androidMain/jniLibs/$arch")
            dependsOn("rustCryptoBuild_$arch")
        }
    )

    tasks.withType<JavaCompile> {
        dependsOn("rustCryptoDeploy_$arch")
    }
}

tasks.preBuild {
    dependsOn(
        templates.map { "rustCryptoDeploy_${it.key}" }
    )
}

tasks.register(
    name = "rustCryptoClean",
    type = Delete::class,
    configurationAction = {
        delete("$rustBasePath/target", "src/androidMain/jniLibs")
    }
)

tasks.clean.dependsOn(taskName = "rustCryptoClean")


