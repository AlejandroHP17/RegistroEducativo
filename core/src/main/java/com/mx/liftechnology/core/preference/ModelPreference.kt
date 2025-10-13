package com.mx.liftechnology.core.preference

/**
 * Object containing constant keys for accessing SharedPreferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ModelPreference {
    /** Key for storing the user's login status. */
    const val LOGIN = "LOGIN"
    /** Key for storing the user's access token. */
    const val ACCESS_TOKEN = "ACCESS_TOKEN"

    /** Key for storing the global user ID. */
    const val ID_USER = "ID_USER"
    /** Key for storing the user's role (e.g., "teacher" or "student"). */
    const val USER_ROLE = "USER_ROLE"
    /** Key for storing the unique ID associated with the user's role. */
    const val ID_ROLE = "ID_ROLE"

    /** Key for storing the ID of the selected group. */
    const val ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP = "ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP"
    /** Key for storing the ID of the selected partial. */
    const val ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP = "ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP"
    /** Key for storing the ID of the selected subject. */
    const val ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP = "ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP"

    /** Key for storing the date range of the active partial. */
    const val RANGE_DATES_PARTIAL= "RANGE_DATES_PARTIAL"
}
