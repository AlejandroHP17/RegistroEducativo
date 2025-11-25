/**
 * @file Contiene las constantes para los endpoints de la API y la URL base.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.environment

import android.os.Build
import com.mx.liftechnology.core.util.logInfo

/**
 * Objeto que contiene las constantes para los endpoints de la API y la URL base.
 * Gestiona la URL base dinámicamente, dependiendo de si la app se ejecuta en un emulador o en un dispositivo real.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object Environment {
     // Para emulador: 10.0.2.2 es la dirección especial que apunta al localhost de la máquina host
    private const val EMULATOR_BASE_URL = "http://10.0.2.2:8000/api/"
    // Para dispositivo real: usar la IP de tu máquina en la red local
    private const val DEVICE_BASE_URL = "http://192.168.100.94:8000/api/"

    /**
     * La URL base para la API.
     * Devuelve la URL apropiada según si la app se está ejecutando en un emulador o en un dispositivo real.
     */
    val URL_BASE: String
        get() {
            val isEmulator = isRunningOnEmulator()
            val url = if (isEmulator) EMULATOR_BASE_URL else DEVICE_BASE_URL
            logInfo("Environment: isRunningOnEmulator: $isEmulator")
            return url
        }

    private fun isRunningOnEmulator(): Boolean {
        return (Build.FINGERPRINT.contains("generic") ||
                Build.FINGERPRINT.contains("emulator") ||
                Build.MODEL.contains("Emulator") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
                "google_sdk" == Build.PRODUCT)
    }

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
    const val END_POINT_GET_WORK_TYPE_EVALUATIONS = "work-type-evaluations/"
    const val END_POINT_GET_WORKS_STUDENT = "student-works/"

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