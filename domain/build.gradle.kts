plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.mx.liftechnology.domain"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.junit.test)
    implementation(libs.bundles.koin)

    implementation(project(":core"))
    implementation(project(":data"))
}