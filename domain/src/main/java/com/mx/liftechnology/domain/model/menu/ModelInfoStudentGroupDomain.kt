package com.mx.liftechnology.domain.model.menu

/**
 * Data model for holding information about a student group in the domain layer.
 *
 * @property listSchool A list of student groups available.
 * @property infoSchoolSelected The currently selected student group.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelInfoStudentGroupDomain(
    val listSchool: List<ModelDialogStudentGroupDomain>,
    val infoSchoolSelected: ModelDialogStudentGroupDomain
)