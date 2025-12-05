package com.mx.liftechnology.domain.model.student

/**
 * Modelo de datos de dominio que representa a un estudiante.
 * Esta clase es `Parcelable` para permitir que se pase entre componentes de Android, como argumentos de navegación.
 *
 * @property studentId El ID único del estudiante.
 * @property curp La CURP del estudiante.
 * @property birthday La fecha de nacimiento del estudiante.
 * @property phoneNumber El número de teléfono del estudiante.
 * @property userId El ID de usuario asociado al estudiante.
 * @property name El nombre del estudiante.
 * @property lastName El apellido paterno del estudiante.
 * @property secondLastName El apellido materno del estudiante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class StudentDomain(
    val studentId : Int?,
    val curp : String?,
    val birthday : String?,
    val phoneNumber : String?,
    val userId : Int?,
    val name : String?,
    val lastName : String?,
    val secondLastName : String?
)