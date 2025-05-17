package com.mx.liftechnology.core.preference

/** Model - Include all the elements to preferences
 * @author pelkidev
 * @since 1.0.0
 */
object ModelPreference {
    const val LOGIN = "LOGIN"
    const val ACCESS_TOKEN = "ACCESS_TOKEN"

    const val ID_USER = "ID_USER"  // Global User
    const val USER_ROLE = "USER_ROLE"  // User Type (teacher or student)
    const val ID_ROLE = "ID_ROLE"   // ID by type and unique

    const val ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP = "ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP"  // Group Selected
    const val ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP = "ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP"  // Partial Selected
    const val ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP = "ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP"  // Subject Selected
}