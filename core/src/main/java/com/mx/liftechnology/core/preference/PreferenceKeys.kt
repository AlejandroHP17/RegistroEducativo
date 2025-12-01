/**
 * @file Define las claves constantes para acceder a SharedPreferences.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.preference

/**
 * Objeto que contiene las claves constantes para acceder a los valores guardados en SharedPreferences.
 * Centralizar estas claves aquí ayuda a evitar errores tipográficos y facilita el mantenimiento.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object PreferenceKeys {
    /** Clave para almacenar el estado de inicio de sesión del usuario. */
    const val REMEMBER_LOGIN = "LOGIN"
    /** Clave para almacenar el token de acceso del usuario. */
    const val ACCESS_TOKEN = "ACCESS_TOKEN"
    /** Clave para almacenar el token de acceso del usuario. */
    const val REFRESH_TOKEN = "REFRESH_TOKEN"

    /** Clave para almacenar el ID de usuario global. */
    const val ID_USER = "ID_USER"
    /** Clave para almacenar el ID único asociado al rol del usuario. */
    const val ID_USER_LEVEL = "ID_USER_LEVEL"

    /** Clave para almacenar el ID del grupo seleccionado. */
    const val ID_CYCLE_SCHOOL = "ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP"
    /** Clave para almacenar el ID de la materia seleccionada. */
    const val ID_FORMATIVE_FIELD = "ID_FORMATIVE_FIELD"

    /** Clave para almacenar el ID del parcial seleccionado. */
    const val ID_PARTIAL = "ID_PARTIAL"


    /** Clave para almacenar el rango de fechas del parcial activo. */
    const val RANGE_DATES_PARTIAL= "RANGE_DATES_PARTIAL"
}
