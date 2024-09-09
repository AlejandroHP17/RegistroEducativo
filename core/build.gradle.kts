
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
}

dependencies {
    // Room para base de datos
    implementation(libs.bundles.androidx.room)

}