import co.touchlab.skie.configuration.DefaultArgumentInterop
import co.touchlab.skie.configuration.EnumInterop
import co.touchlab.skie.configuration.SealedInterop
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.skie)
    alias(libs.plugins.ksp)
    alias(libs.plugins.sqldelight)
    id("dev.icerock.mobile.multiplatform-resources")
}

skie{
    features{
        group {
            SealedInterop.Enabled(true)
            DefaultArgumentInterop.Enabled(true)
            EnumInterop.Enabled(true)
        }
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Scrumdinger KMM Library"
        homepage = "https://github.com/haqim007"
        version = "1.1.0"
        ios.deploymentTarget = "15.0"
        framework {
            baseName = "ScrumdingerKMMLib"
            isStatic = true
            binaryOptions["bundleId"] = "dev.haqim.scrumdingerKMMLib"
            export(libs.mokoresources)
            export(libs.mokographics) // toUIColor here
        }
        specRepos {
            name = "ScrumdingerKMMLib"
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.skie.configuration.annotations)

            implementation(libs.sqldelight.runtime)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)
            implementation(libs.sqldelight.coroutines.extensions)
            api(libs.mokoresources)

        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "dev.haqim.scrumdingerkmmlib"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sqldelight {
    databases {
        create("MyScrumdingerDB") {
            packageName.set("dev.haqim.scrumdingerkmmlib.data.cache")
        }
    }
}

multiplatformResources {
    resourcesPackage.set("dev.haqim.scrumdingerKMMLib") // required
    resourcesClassName.set("SharedRes") // optional, default MR
}

tasks.register<Exec>("buildAndExport") {
    group = "Custom"
    description = "Build and export XCFramework locally"

    commandLine("sh", "../scripts/local_build.sh")
}
