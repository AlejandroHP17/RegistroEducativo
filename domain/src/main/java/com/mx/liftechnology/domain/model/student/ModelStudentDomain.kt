/**
 * @file Define el modelo de dominio para un estudiante y su mapeo desde la capa de datos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.student

import android.os.Parcelable
import com.mx.liftechnology.core.network.apiCall.student.ResponseGetStudent
import com.mx.liftechnology.data.model.student.ModelStudentData
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
data class ModelStudentDomain(
    val studentId : Int?,
    val curp : String?,
    val birthday : String?,
    val phoneNumber : String?,
    val userId : Int?,
    val name : String?,
    val lastName : String?,
    val secondLastName : String?
) : Parcelable

/**
 * Función de extensión para convertir una lista de [ResponseGetStudent] (modelo de red)
 * a una lista de [ModelStudentDomain] (modelo de dominio).
 *
 * @receiver Una lista nulable de objetos [ResponseGetStudent].
 * @return Una lista de objetos [ModelStudentDomain]. Si la lista de entrada es nula o contiene elementos nulos, estos son omitidos.
 * @author Pelkidev
 * @version 1.0.0
 */
fun List<ModelStudentData?>?.toModelStudentList(): List<ModelStudentDomain> {
    return this?.mapNotNull { response ->
        response?.let {
            ModelStudentDomain(
                studentId = response.studentId,
                curp = response.curp,
                birthday = response.birthday,
                phoneNumber = response.phoneNumber,
                userId = response.userId,
                name = response.name,
                lastName = response.lastName,
                secondLastName = response.secondLastName
            )
        }
    } ?: emptyList()
}