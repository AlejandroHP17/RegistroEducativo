/**
 * @file Contiene las constantes para los endpoints de la API y la URL base.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.environment

import android.os.Build
import com.mx.liftechnology.core.BuildConfig
import com.mx.liftechnology.core.util.extension.logInfo

/**
 * Objeto que contiene las constantes para los endpoints de la API y la URL base.
 * 
 * **Configuración de entorno:**
 * La URL base se obtiene desde BuildConfig, permitiendo diferentes configuraciones
 * para debug y release sin modificar el código. Además, detecta automáticamente
 * si la app se está ejecutando en un emulador o en un dispositivo real para
 * usar la URL apropiada.
 * 
 * **BuildConfig:**
 * Los valores se configuran en `build.gradle.kts` del módulo core mediante
 * `buildConfigField` en los diferentes `buildTypes` (debug, release, etc.).
 * Se configuran dos URLs:
 * - `EMULATOR_BASE_URL`: URL para cuando se ejecuta en emulador
 * - `DEVICE_BASE_URL`: URL para cuando se ejecuta en dispositivo real
 * 
 * **Detección de emulador:**
 * La app detecta automáticamente si se está ejecutando en un emulador usando
 * propiedades del sistema Android (Build.FINGERPRINT, Build.MODEL, etc.).
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
object Environment {
    /**
     * La URL base para la API.
     * 
     * Gestiona la URL base dinámicamente, dependiendo de si la app se ejecuta
     * en un emulador o en un dispositivo real. Obtiene las URLs desde BuildConfig
     * y selecciona la apropiada según el entorno de ejecución.
     * 
     * **Flujo:**
     * 1. Detecta si se está ejecutando en emulador o dispositivo real
     * 2. Selecciona la URL correspondiente desde BuildConfig
     * 3. Loguea la información para debugging
     * 4. Retorna la URL seleccionada
     * 
     * **Configuración en build.gradle.kts:**
     * ```kotlin
     * buildTypes {
     *     debug {
     *         buildConfigField("String", "EMULATOR_BASE_URL", "\"http://10.0.2.2:8000/api/\"")
     *         buildConfigField("String", "DEVICE_BASE_URL", "\"http://192.168.100.94:8000/api/\"")
     *     }
     *     release {
     *         buildConfigField("String", "EMULATOR_BASE_URL", "\"https://api.example.com/api/\"")
     *         buildConfigField("String", "DEVICE_BASE_URL", "\"https://api.example.com/api/\"")
     *     }
     * }
     * ```
     * 
     * @return La URL base apropiada según el entorno de ejecución (emulador o dispositivo).
     */
    val URL_BASE: String
        get() {
            val isEmulator = isRunningOnEmulator()
            val url = if (isEmulator) {
                BuildConfig.EMULATOR_BASE_URL
            } else {
                BuildConfig.DEVICE_BASE_URL
            }
            logInfo("Environment: isRunningOnEmulator: $isEmulator")
            logInfo("Environment: URL base configurada: $url")
            return url
        }

    /**
     * Detecta si la aplicación se está ejecutando en un emulador.
     * 
     * Utiliza varias propiedades del sistema Android para determinar si el dispositivo
     * es un emulador. Verifica:
     * - Build.FINGERPRINT contiene "generic" o "emulator"
     * - Build.MODEL contiene "Emulator"
     * - Build.MANUFACTURER contiene "Genymotion"
     * - Build.BRAND y Build.DEVICE comienzan con "generic"
     * - Build.PRODUCT es "google_sdk"
     * 
     * @return `true` si se está ejecutando en un emulador, `false` si es un dispositivo real.
     */
    private fun isRunningOnEmulator(): Boolean {
        return (Build.FINGERPRINT.contains("generic") ||
                Build.FINGERPRINT.contains("emulator") ||
                Build.FINGERPRINT.contains("sdk_gphone") ||
                Build.MODEL.contains("Emulator") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
                "google_sdk" == Build.PRODUCT)
    }

    /**
     * La versión de la API.
     * 
     * Obtiene la versión desde BuildConfig, permitiendo diferentes versiones
     * según el tipo de build.
     * 
     * @return La versión de la API configurada para el entorno actual.
     */
    val API_VERSION: String
        get() = BuildConfig.API_VERSION

    /** Endpoints para el flujo de login. */
    const val END_POINT_LOGIN = "auth/login"
    const val END_POINT_GET_DATA= "auth/me"
    const val END_POINT_REGISTER = "auth/register"
    const val END_POINT_REFRESH = "auth/refresh"

    /** Endpoints para el flujo de registro. */
    const val END_POINT_REGISTER_SCHOOL_CYCLE = "cycles/"
    const val END_POINT_REGISTER_PARTIAL = "partials/"
    const val END_POINT_REGISTER_STUDENT = "students/"
    const val END_POINT_REGISTER_FORMATIVE_FIELDS = "formative-fields/" // Solo agrega el campo formativo
    const val END_POINT_REGISTER_FORMATIVE_FIELDS_BULK = "formative-fields/bulk" //Agrega campo formativo, tipos de trabajo y tipos de trabajo asociados al campo formativo
    const val END_POINT_REGISTER_STUDENT_WORK_BULK = "student-works/bulk"

    /** Endpoints para la obtención de datos. */
    const val END_POINT_GET_PARTIAL = "partials/"
    const val END_POINT_GET_STUDENT = "students/"
    const val END_POINT_GET_FORMATIVE_FIELDS= "formative-fields/"
    const val END_POINT_GET_WORK_TYPE = "work-types/"
    const val END_POINT_GET_WORK_TYPE_BY = "work-types/by_formative_field/{formative_field_id}"
    const val END_POINT_GET_FORMATIVE_FIELD_WORK_TYPE = "formative-fields/by-cycle/{school_cycle_id}"
    const val END_POINT_GET_WORK_TYPE_STUDENT = "student-works/grouped"
    const val END_POINT_GET_WORKS_STUDENT = "student-works/"
    const val END_POINT_GET_FIELD_TYPE_STUDENTS = "student-works/by-field-type-students"

    /** Endpoints para borrar datos. */
    const val END_POINT_DELETE_STUDENT = "students/{student_id}"
    const val END_POINT_DELETE_FORMATIVE_FIELDS = "formative-fields/{field_id}"

    /** Endpoints para editar datos. */
    const val END_POINT_EDIT_STUDENT = "students/{student_id}"
    const val END_POINT_EDIT_FORMATIVE_FIELDS = "formative-fields/{field_id}"

    /** Endpoints para el flujo del menú. */
    const val END_POINT_GET_CCT = "schools/{cct}"
    const val END_POINT_GET_CYCLE_SCHOOL = "cycles/"
}