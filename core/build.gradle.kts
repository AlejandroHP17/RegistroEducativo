
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.mx.liftechnology.core"

    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_21
        sourceCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(libs.bundles.junit.test)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.location)
    implementation(libs.bundles.androidx.security)
}