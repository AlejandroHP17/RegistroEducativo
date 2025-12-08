/**
 * @file Define el modelo de dominio para la información de grupos de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.schoolCycle

/**
 * Modelo de datos que contiene información sobre grupos de estudiantes en la capa de dominio.
 *
 * @property listSchool Una lista de grupos de estudiantes disponibles.
 * @property infoSchoolSelected El grupo de estudiantes actualmente seleccionado.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class InfoStudentGroupDomain(
    val listSchool: List<DialogStudentGroupDomain>,
    val infoSchoolSelected: DialogStudentGroupDomain
)