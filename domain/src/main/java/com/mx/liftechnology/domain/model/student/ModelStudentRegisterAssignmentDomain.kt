package com.mx.liftechnology.domain.model.student

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetStudent

/**
 * Data model representing a student for assignment registration in the domain layer.
 *
 * @property studentId The student's ID.
 * @property curp The student's CURP.
 * @property completeName The student's full name.
 * @property qualification The student's qualification for the assignment.
 * @property listNumber The student's number in the list.
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
 * Extension function to convert a list of [ResponseGetStudent] to a list of [ModelStudentRegisterAssignmentDomain].
 * The list is sorted by the student's full name.
 *
 * @receiver A nullable list of [ResponseGetStudent] objects.
 * @return A list of [ModelStudentRegisterAssignmentDomain] objects.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun List<ResponseGetStudent?>?.toModelStudentRegisterAssignmentList(): List<ModelStudentRegisterAssignmentDomain> {

    return this
        ?.filterNotNull() // Elimina nulos antes de mapear
        ?.sortedBy { "${it.lastName} ${it.secondLastName} ${it.name}" } // Ordena por nombre completo
        ?.mapIndexed { index, response ->
            val completeName = "${response.lastName} ${response.secondLastName} ${response.name}"
            ModelStudentRegisterAssignmentDomain(
                studentId = response.studentId,
                curp = response.curp,
                completeName = completeName,
                qualification = null,
                listNumber = index + 1 // Asigna el número de lista basado en la posición
            )
        } ?: emptyList()
}