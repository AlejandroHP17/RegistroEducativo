package com.mx.liftechnology.core.network.enviroment

import android.os.Build

/**
 * Object containing constants for API endpoints and base URL.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object Environment {
    private const val EMULATOR_BASE_URL = "http://3.17.67.71/api/v1/"
    private const val DEVICE_BASE_URL = "http://3.17.67.71/api/v1/"

    /**
     * The base URL for the API.
     * It returns the appropriate URL based on whether the app is running on an emulator or a real device.
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

    /** Endpoints for the login flow. */
    const val END_POINT_LOGIN = "auth/login"
    const val END_POINT_REGISTER = "register/teacherRegister"

    /** Endpoints for the registration flow. */
    const val END_POINT_REGISTER_SCHOOL = "teacher/registerTeacherCycleGroup"
    const val END_POINT_REGISTER_PARTIAL = "teacher/registerPartialCycleGroup"
    const val END_POINT_REGISTER_STUDENT = "teacher/registerStudentCycleGroup"
    const val END_POINT_REGISTER_SUBJECT = "teacher/registerTeacherSubjectSchool"
    const val END_POINT_REGISTER_ASSIGNMENT = "teacher/registerTeacherSubjectSchoolGroup"
    const val END_POINT_REGISTER_JOB = "teacher/registerTeacherJobDayByStudent"

    /** Endpoints for getting data. */
    const val END_POINT_GET_STUDENT = "teacher/getTeacherGroupStudents"
    const val END_POINT_GET_SUBJECT= "teacher/getTeacherSubjectsPECG"
    const val END_POINT_GET_SUBJECT_PERCENT = "teacher/getTeacherSubjectsPercent"
    const val END_POINT_GET_PARTIAL = "teacher/getTeacherGroupPartials"
    const val END_POINT_GET_EVALUATION_TYPE = "teacher/getTeacherGroup"
    const val END_POINT_GET_ASSESSMENT_TYPE = "teacher/getTeacherTipoTrabajoPECG"

    /** Endpoints for the menu flow. */
    const val END_POINT_GET_CCT = "teacher/getSchoolCCT/{cct}"
    const val END_POINT_GET_GROUP = "teacher/getTeacherGroups"
}