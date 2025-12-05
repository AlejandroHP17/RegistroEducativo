
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dokka) apply false
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

    // Configuración de tipos de build para diferentes entornos.
    // Permite tener diferentes URLs base y configuraciones según el entorno.
    // Se configuran URLs separadas para emulador y dispositivo, y la app detecta
    // automáticamente en cuál se está ejecutando.
    buildTypes {
        debug {
            // URL para emulador: 10.0.2.2 es la dirección especial que apunta al localhost de la máquina host
            buildConfigField("String", "EMULATOR_BASE_URL", "\"http://10.0.2.2:8000/api/\"")
            // URL para dispositivo real: usar la IP de tu máquina en la red local
            buildConfigField("String", "DEVICE_BASE_URL", "\"http://192.168.100.94:8000/api/\"")
            buildConfigField("String", "API_VERSION", "\"v1\"")
        }
        release {
            // En producción, ambas URLs apuntan al mismo servidor
            buildConfigField("String", "EMULATOR_BASE_URL", "\"https://api.example.com/api/\"")
            buildConfigField("String", "DEVICE_BASE_URL", "\"https://api.example.com/api/\"")
            buildConfigField("String", "API_VERSION", "\"v1\"")
        }
    }
}

dependencies {
    implementation(libs.bundles.junit.test)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.location)
    implementation(libs.bundles.androidx.security)
    implementation(libs.bundles.test)

    api(libs.bundles.timber)
    testImplementation(kotlin("test"))
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
}