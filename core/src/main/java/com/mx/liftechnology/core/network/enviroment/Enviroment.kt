package com.mx.liftechnology.core.network.enviroment

/**
 * Environment;
 * Constants of end point
 * */
object Environment {
    /** The first */
    //const val URL_BASE = "http://192.168.100.28:8000/api/"
    const val URL_BASE = "http://10.0.2.2:8000/api/v1/"

    /** Login flow */
    const val END_POINT_LOGIN = "auth/login"
    const val END_POINT_REGISTER= "register/teacherRegister"

    /** Register flow*/
    const val END_POINT_CCT = "obtenCCTEscuelasCicloActivo"

    /** Menu flow */
    const val END_POINT_GET_CCT = "teacher/getSchoolCCT/{cct}"
    const val END_POINT_REGISTER_SCHOOL= "teacher/registerTeacherCycleGroup"
    const val END_POINT_GET_GROUP= "teacher/getTeacherGroups"
}