/**
 * @file Define el modelo de dominio para el registro de asignaciones de un estudiante y su mapeo desde la capa de datos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.student

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetStudent

/**
 * Modelo de datos que representa a un estudiante para el registro de una asignación en la capa de dominio.
 *
 * @property studentId El ID del estudiante.
 * @property curp La CURP del estudiante.
 * @property completeName El nombre completo del estudiante.
 * @property qualification La calificación del estudiante para la asignación (inicialmente nula).
 * @property listNumber El número del estudiante en la lista ordenada.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelStudentRegisterAssignmentDomain (
    val studentId : String?,
    val curp : String?,
    val completeName : String?,
    val qualification : Float?,
    val listNumber : Int?
)

/**
 * Función de extensión para convertir una lista de [ResponseGetStudent] (modelo de red)
 * a una lista de [ModelStudentRegisterAssignmentDomain] (modelo de dominio).
 * La lista resultante está ordenada alfabéticamente por el nombre completo del estudiante.
 *
 * @receiver Una lista nulable de objetos [ResponseGetStudent].
 * @return Una lista de [ModelStudentRegisterAssignmentDomain]. Si la lista de entrada es nula, vacía o contiene solo nulos, devuelve una lista vacía.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun List<ResponseGetStudent?>?.toModelStudentRegisterAssignmentList(): List<ModelStudentRegisterAssignmentDomain> {
    return this
        ?.filterNotNull() // Elimina elementos nulos para seguridad.
        ?.sortedBy { "${it.lastName} ${it.secondLastName} ${it.name}" } // Ordena la lista por nombre completo.
        ?.mapIndexed { index, response ->
            val completeName = "${response.lastName} ${response.secondLastName} ${response.name}"
            ModelStudentRegisterAssignmentDomain(
                studentId = response.id.toString(),
                curp = response.curp,
                completeName = completeName,
                qualification = null, // La calificación se establece posteriormente.
                listNumber = index + 1 // Asigna el número de lista basado en la posición ordenada.
            )
        } ?: emptyList() // Devuelve una lista vacía si la lista original es nula.
}
