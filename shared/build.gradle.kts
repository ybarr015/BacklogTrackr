import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room3)
    alias(libs.plugins.kotlinxSerialization)
}

abstract class GenerateBuildConfigTask : DefaultTask() {
    @get:InputFile
    @get:Optional
    abstract val localPropertiesFile: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val properties = Properties().apply {
            val file = localPropertiesFile.orNull?.asFile
            if (file?.exists() == true) {
                file.inputStream().use { load(it) }
            }
        }

        val clientId = properties.getProperty("CLIENT_ID") ?: ""
        val clientSecret = properties.getProperty("CLIENT_SECRET") ?: ""

        val outputFile = outputDir.file("com/x3vo/backlogtrackr/SharedBuildConfig.kt").get().asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText("""
            package com.x3vo.backlogtrackr

            object SharedBuildConfig {
                const val CLIENT_ID = "$clientId"
                const val CLIENT_SECRET = "$clientSecret"
            }
        """.trimIndent())
    }
}

val generateSharedBuildConfig by tasks.registering(GenerateBuildConfigTask::class) {
    localPropertiesFile.set(rootProject.file("local.properties"))
    outputDir.set(layout.buildDirectory.dir("generated/sharedBuildConfig/commonMain/kotlin"))
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    android {
       namespace = "com.x3vo.backlogtrackr.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
       withDeviceTestBuilder {
           sourceSetTreeName = "test"
       }.configure {
           instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)

            //ktor
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }
        commonMain {
            kotlin.srcDir(generateSharedBuildConfig)
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)

                //navigation
                implementation(libs.navigation.compose)

                //ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.auth)

                //coroutines
                implementation(libs.kotlinx.coroutines.core)

                //coil
                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor3)

                //room
                implementation(libs.androidx.room3.runtime)
                implementation(libs.androidx.sqlite.bundled)

                //koin
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)

                //datastore preferences
                implementation(libs.androidx.datastore)
                implementation(libs.androidx.datastore.preferences)

                //datetime
                implementation(libs.kotlinx.datetime)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
    add("kspAndroid", libs.androidx.room3.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room3.compiler)
    add("kspIosArm64", libs.androidx.room3.compiler)
}

room3 {
    schemaDirectory("$projectDir/schemas")
}
