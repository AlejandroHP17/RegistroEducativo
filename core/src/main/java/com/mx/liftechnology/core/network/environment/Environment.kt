/**
 * @file Contiene las constantes para los endpoints de la API y la URL base.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.environment

import android.os.Build

/**
 * Objeto que contiene las constantes para los endpoints de la API y la URL base.
 * Gestiona la URL base dinámicamente, dependiendo de si la app se ejecuta en un emulador o en un dispositivo real.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object Environment {
    private const val EMULATOR_BASE_URL = "http://3.17.67.71/api/v1/"
    private const val DEVICE_BASE_URL = "http://3.17.67.71/api/v1/"

    /**
     * La URL base para la API.
     * Devuelve la URL apropiada según si la app se está ejecutando en un emulador o en un dispositivo real.
     */
    val URL_BASE: String
        get() = if (isRunningOnEmulator()) EMULATOR_BASE_URL else DEVICE_BASE_URL

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
    const val END_POINT_REGISTER = "register/teacherRegister"

    /** Endpoints para el flujo de registro. */
    const val END_POINT_REGISTER_SCHOOL = "teacher/registerTeacherCycleGroup"
    const val END_POINT_REGISTER_PARTIAL = "teacher/registerPartialCycleGroup"
    const val END_POINT_REGISTER_STUDENT = "teacher/registerStudentCycleGroup"
    const val END_POINT_REGISTER_SUBJECT = "teacher/registerTeacherSubjectSchool"
    const val END_POINT_REGISTER_ASSIGNMENT = "teacher/registerTeacherSubjectSchoolGroup"
    const val END_POINT_REGISTER_JOB = "teacher/registerTeacherJobDayByStudent"

    /** Endpoints para la obtención de datos. */
    const val END_POINT_GET_STUDENT = "teacher/getTeacherGroupStudents"
    const val END_POINT_GET_SUBJECT= "teacher/getTeacherSubjectsPECG"
    const val END_POINT_GET_SUBJECT_PERCENT = "teacher/getTeacherSubjectsPercent"
    const val END_POINT_GET_PARTIAL = "teacher/getTeacherGroupPartials"
    const val END_POINT_GET_EVALUATION_TYPE = "teacher/getTeacherGroup"
    const val END_POINT_GET_ASSESSMENT_TYPE = "teacher/getTeacherTipoTrabajoPECG"

    /** Endpoints para el flujo del menú. */
    const val END_POINT_GET_CCT = "teacher/getSchoolCCT/{cct}"
    const val END_POINT_GET_GROUP = "teacher/getTeacherGroups"
}