package com.mx.liftechnology.core.network.enviroment

import android.os.Build

/**
 * Environment;
 * Constants of end point
 * */
object Environment {
    private const val EMULATOR_BASE_URL = "http://10.0.2.2:8000/api/v1/"
    private const val DEVICE_BASE_URL = "http://192.168.100.28:8000/api/v1/"

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
    /** Login flow */
    const val END_POINT_LOGIN = "auth/login"
    const val END_POINT_REGISTER= "register/teacherRegister"

    /** Register flow*/
    const val END_POINT_REGISTER_SCHOOL= "teacher/registerTeacherCycleGroup"
    const val END_POINT_REGISTER_PARTIAL= "teacher/registerPartialCycleGroup"

    /** Menu flow */
    const val END_POINT_GET_CCT = "teacher/getSchoolCCT/{cct}"
    const val END_POINT_GET_GROUP= "teacher/getTeacherGroups"
}