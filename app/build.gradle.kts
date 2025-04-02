plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidx.navigation.safeargs)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.mx.liftechnology.registroeducativo"

    kotlinOptions {
        jvmTarget = "11"  // Alinea la compatibilidad de la versión de Kotlin
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_21
        sourceCompatibility = JavaVersion.VERSION_21
    }
}


dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx.basic)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.timber)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.junit.test)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.animation)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.gson)
    implementation(libs.bundles.compose.unit)



    /* Libraries */
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core"))
}