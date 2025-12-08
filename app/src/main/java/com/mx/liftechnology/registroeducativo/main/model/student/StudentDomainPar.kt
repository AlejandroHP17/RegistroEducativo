package com.mx.liftechnology.registroeducativo.main.model.student

import android.os.Parcelable
import com.mx.liftechnology.domain.model.student.StudentDomain
import kotlinx.parcelize.Parcelize

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
@Parcelize
data class StudentDomainPar(
    val studentId: Int?,
    val curp: String?,
    val birthday: String?,
    val phoneNumber: String?,
    val userId: Int?,
    val name: String?,
    val lastName: String?,
    val secondLastName: String?
) : Parcelable

/**
 * Función de extensión para convertir un [StudentData] (modelo de datos) a un [com.mx.liftechnology.domain.model.student.StudentDomain] (modelo de dominio).
 *
 * @receiver Un objeto [StudentData] nulable.
 * @return Un objeto [com.mx.liftechnology.domain.model.student.StudentDomain] equivalente, o null si el receiver es null.
 * @author Pelkidev
 * @version 1.0.0
 */
fun StudentDomain?.toStudentDomain(): StudentDomainPar? {
    return this?.let {
        StudentDomainPar(
            studentId = it.studentId,
            curp = it.curp,
            birthday = it.birthday,
            phoneNumber = it.phoneNumber,
            userId = it.userId,
            name = it.name,
            lastName = it.lastName,
            secondLastName = it.secondLastName
        )
    }
}

/**
 * Función de extensión para convertir una lista de [StudentData] (modelo de datos)
 * a una lista de [StudentDomain] (modelo de dominio).
 *
 * @receiver Una lista nulable de objetos [StudentData].
 * @return Una lista de objetos [StudentDomain]. Si la lista de entrada es nula o contiene elementos nulos, estos son omitidos.
 * @author Pelkidev
 * @version 1.0.0
 */
fun List<StudentDomain?>?.toStudentDomainList(): List<StudentDomainPar> {
    return this?.mapNotNull { it.toStudentDomain() } ?: emptyList()
}