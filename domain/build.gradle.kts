plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dokka) apply false
}

android {
    namespace = "com.mx.liftechnology.domain"

    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_21
        sourceCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(libs.bundles.test)
    implementation(libs.bundles.koin)

    implementation(project(":core"))
    implementation(project(":data"))
}