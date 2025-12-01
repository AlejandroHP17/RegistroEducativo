package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseEditStudent
import com.mx.liftechnology.core.network.api.ResponseGetListEvaluationsStudent
import com.mx.liftechnology.core.network.api.ResponseGetStudent
import com.mx.liftechnology.core.network.api.ResponseRegisterStudent
import com.mx.liftechnology.data.model.student.ModelEvaluationsStudent
import com.mx.liftechnology.data.model.student.StudentData
import kotlin.jvm.JvmName

object StudentMapper {
    /**
     * Convierte un [ResponseEditStudent] a [StudentData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para editar estudiante.
     * @return Un objeto [StudentData] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseEditStudent?.toData(): StudentData? {
        return this?.let {
            StudentData(
                curp = curp,
                name = name,
                lastName = lastName,
                secondLastName = secondLastName,
                birthday = birthday,
                phoneNumber = phoneNumber,
                userId = studentId,
                studentId = studentId,
                isActive = isActive
            )
        }
    }

    /**
     * Convierte una lista de [ResponseGetStudent] a una lista de [StudentData] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener estudiantes.
     * @return Una lista de objetos [StudentData] con los datos mapeados. Los elementos nulos son omitidos.
     */
    @JvmName("toDataFromResponseGetStudentList")
    fun List<ResponseGetStudent>?.toData(): List<StudentData> {
        return this?.mapNotNull { student ->
            student?.let {
                StudentData(
                    curp = it.curp,
                    name = it.name,
                    lastName = it.lastName,
                    secondLastName = it.secondLastName,
                    birthday = it.birthday,
                    phoneNumber = it.phoneNumber,
                    userId = it.userId,
                    studentId = it.studentId,
                    isActive = it.isActive
                )
            }
        } ?: emptyList()
    }

    /**
     * Convierte un [ResponseRegisterStudent] a [StudentData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para registrar estudiante.
     * @return Un objeto [StudentData] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseRegisterStudent?.toData(): StudentData? {
        return this?.let {
            StudentData(
                curp = curp,
                name = name,
                lastName = lastName,
                secondLastName = secondLastName,
                birthday = birthday,
                phoneNumber = phoneNumber,
                userId = studentId,
                studentId = studentId,
                isActive = isActive
            )
        }
    }

    /**
     * Convierte una lista de [ResponseGetListEvaluationsStudent] a una lista de [ModelEvaluationsStudent] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener evaluaciones de estudiantes.
     * @return Una lista de objetos [ModelEvaluationsStudent] con los datos mapeados. Los elementos nulos son omitidos.
     */
    @JvmName("toDataFromResponseGetListEvaluationsStudentList")
    fun List<ResponseGetListEvaluationsStudent>?.toData(): List<ModelEvaluationsStudent> {
        return this?.mapNotNull { evaluation ->
            evaluation?.let {
                ModelEvaluationsStudent(
                    studentId = it.studentId,
                    evaluationName = it.evaluationName,
                    grade = it.grade,
                    workDate = it.workDate,
                    evaluationId = it.evaluationId
                )
            }
        } ?: emptyList()
    }
}