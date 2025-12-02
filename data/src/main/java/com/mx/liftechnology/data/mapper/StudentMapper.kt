package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseEditStudent
import com.mx.liftechnology.core.network.api.ResponseGetListEvaluationsStudent
import com.mx.liftechnology.core.network.api.ResponseGetStudent
import com.mx.liftechnology.core.network.api.ResponseRegisterStudent
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain
import com.mx.liftechnology.domain.model.student.StudentDomain
import kotlin.jvm.JvmName

object StudentMapper {
    /**
     * Convierte un [ResponseEditStudent] a [StudentDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para editar estudiante.
     * @return Un objeto [StudentDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseEditStudent?.toData(): StudentDomain? {
        return this?.let {
            StudentDomain(
                curp = curp,
                name = name,
                lastName = lastName,
                secondLastName = secondLastName,
                birthday = birthday,
                phoneNumber = phoneNumber,
                userId = studentId,
                studentId = studentId
            )
        }
    }

    /**
     * Convierte una lista de [ResponseGetStudent] a una lista de [StudentDomain] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener estudiantes.
     * @return Una lista de objetos [StudentDomain] con los datos mapeados. Los elementos nulos son omitidos.
     */
    @JvmName("toDataFromResponseGetStudentList")
    fun List<ResponseGetStudent>?.toData(): List<StudentDomain> {
        return this?.mapNotNull { student ->
            student?.let {
                StudentDomain(
                    curp = it.curp,
                    name = it.name,
                    lastName = it.lastName,
                    secondLastName = it.secondLastName,
                    birthday = it.birthday,
                    phoneNumber = it.phoneNumber,
                    userId = it.userId,
                    studentId = it.studentId
                )
            }
        } ?: emptyList()
    }

    /**
     * Convierte un [ResponseRegisterStudent] a [StudentDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para registrar estudiante.
     * @return Un objeto [StudentDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseRegisterStudent?.toData(): StudentDomain? {
        return this?.let {
            StudentDomain(
                curp = curp,
                name = name,
                lastName = lastName,
                secondLastName = secondLastName,
                birthday = birthday,
                phoneNumber = phoneNumber,
                userId = studentId,
                studentId = studentId
            )
        }
    }

    /**
     * Convierte una lista de [ResponseGetListEvaluationsStudent] a una lista de [EvaluationsStudentDomain] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener evaluaciones de estudiantes.
     * @return Una lista de objetos [EvaluationsStudentDomain] con los datos mapeados. Los elementos nulos son omitidos.
     */
    @JvmName("toDataFromResponseGetListEvaluationsStudentList")
    fun List<ResponseGetListEvaluationsStudent>?.toData(): List<EvaluationsStudentDomain> {
        return this?.mapNotNull { evaluation ->
            evaluation?.let {
                EvaluationsStudentDomain(
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