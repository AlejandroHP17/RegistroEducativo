package com.mx.liftechnology.domain.model.student

import com.mx.liftechnology.core.network.callapi.ResponseGetStudent

data class ModelStudentRegisterAssignmentDomain (
    val studentId : String?,
    val curp : String?,
    val completeName : String?,
    val qualification : Float?,
    val listNumber : Int?
)

// Función para convertir una lista de ResponseGetStudent a ModelStudentDomain
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