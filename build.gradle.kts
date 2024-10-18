import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.android.library) apply false
    kotlin("jvm")
}

subprojects {

    afterEvaluate {

        extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions>()?.apply {
            jvmTarget.set(JvmTarget.JVM_21)
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }

        tasks.withType<JavaCompile> {
            sourceCompatibility = JavaVersion.VERSION_21.toString()
            targetCompatibility = JavaVersion.VERSION_21.toString()

        }
        extensions.findByType<com.android.build.gradle.BaseExtension>()?.apply {
            compileSdkVersion(34)

            defaultConfig {
                minSdk = 28
                targetSdk = 34
                versionCode = 1
                versionName = "0.0.1"

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }


            if (plugins.hasPlugin("com.android.application")) {
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }
                getByName("debug") {
                    isDebuggable = true
                }
            }

            buildTypes {
                getByName("debug") {
                    isMinifyEnabled = false
                    buildConfigField("boolean", "LOG_TAG", "true")
                }
                getByName("release") {
                    isMinifyEnabled = false
                    buildConfigField("boolean", "LOG_TAG", "false")
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            // Corregir flavorDimensions
            flavorDimensions("version")

            productFlavors {
                create("dev") {
                    versionCode = 5
                    applicationIdSuffix = ".dev" // Solo aplica para módulos de aplicación
                    versionNameSuffix = "-dev"
                    dimension = "version"
                }
                create("qa") {
                    applicationIdSuffix = ".qa" // Solo aplica para módulos de aplicación
                    versionNameSuffix = "-qa"
                    dimension = "version"
                }
                create("prod") {
                    versionNameSuffix = "-prod"
                    dimension = "version"
                }
            }
        }
    }
    }
}
