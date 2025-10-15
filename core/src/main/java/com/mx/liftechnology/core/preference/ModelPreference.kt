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
object ModelPreference {
    /** Clave para almacenar el estado de inicio de sesión del usuario. */
    const val LOGIN = "LOGIN"
    /** Clave para almacenar el token de acceso del usuario. */
    const val ACCESS_TOKEN = "ACCESS_TOKEN"

    /** Clave para almacenar el ID de usuario global. */
    const val ID_USER = "ID_USER"
    /** Clave para almacenar el rol del usuario (ej: "profesor" o "alumno"). */
    const val USER_ROLE = "USER_ROLE"
    /** Clave para almacenar el ID único asociado al rol del usuario. */
    const val ID_ROLE = "ID_ROLE"

    /** Clave para almacenar el ID del grupo seleccionado. */
    const val ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP = "ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP"
    /** Clave para almacenar el ID del parcial seleccionado. */
    const val ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP = "ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP"
    /** Clave para almacenar el ID de la materia seleccionada. */
    const val ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP = "ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP"

    /** Clave para almacenar el rango de fechas del parcial activo. */
    const val RANGE_DATES_PARTIAL= "RANGE_DATES_PARTIAL"
}
