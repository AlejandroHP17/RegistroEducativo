/**
 * @file Define los mappers para convertir datos de evaluaciones entre capas.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseGetByFieldTypeStudent
import com.mx.liftechnology.core.network.api.ResponseGetListByFieldStudent
import com.mx.liftechnology.core.network.api.ResponseGetListByFieldTypeStudent
import com.mx.liftechnology.core.network.api.ResponseGetListEvaluationsStudent
import com.mx.liftechnology.core.network.api.ResponseGetListWorkStudents
import com.mx.liftechnology.core.network.api.ResponseListWork
import com.mx.liftechnology.core.network.api.ResponseListWorkStudent
import com.mx.liftechnology.core.network.api.ResponseWorkTypeEvaluations
import com.mx.liftechnology.domain.model.evaluation.ListWorkFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.ListWorkStudentFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeEvaluationDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.ByFieldTypeStudentDomain
import com.mx.liftechnology.domain.model.formativeFields.GetListByFieldStudentDomain
import com.mx.liftechnology.domain.model.formativeFields.GetListByFieldTypeStudentDomain
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain

/**
 * Objeto que proporciona funciones de mapeo para convertir datos de evaluaciones
 * entre la capa de red (API) y la capa de datos.
 *
 * **Estado actual:**
 * Este mapper está preparado para futuras implementaciones de funcionalidades de evaluaciones.
 * Actualmente no contiene funciones de mapeo, pero está estructurado para facilitar
 * la adición de mappers cuando sea necesario.
 *
 * **Nota:**
 * Las evaluaciones de estudiantes se mapean actualmente en [StudentMapper] mediante
 * la función `toData()` para `List<ResponseGetListEvaluationsStudent>`.
 *
 * **Uso previsto:**
 * Cuando se implementen funcionalidades adicionales de evaluaciones, este mapper contendrá
 * funciones de extensión similares a otros mappers (ej: `ResponseEvaluation?.toData()`).
 *
 * @see StudentMapper Para ver el mapeo actual de evaluaciones de estudiantes.
 * @see FormativeFieldMapper Para ver un ejemplo de implementación de mapper.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object EvaluationsMapper {

    fun ResponseWorkTypeEvaluations.toWorkTypeEvaluationDomain(): WorkTypeEvaluationDomain {
        return WorkTypeEvaluationDomain(
            createdWorks = createdWorks,
            totalStudentsWithGrade = totalStudentsWithGrade,
            totalStudentsWithoutGrade = totalStudentsWithoutGrade,
            formativeFieldName = formativeFieldName,
            partialName = partialName,
            workTypeId = workTypeId,
            workTypeName = workTypeName,
            nameWork = nameWork
        )
    }

    /**
     * Convierte un [ResponseGetListWorkStudents] a [WorkTypeFormativeFieldDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener lista de trabajos de estudiantes.
     * @return Un objeto [WorkTypeFormativeFieldDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetListWorkStudents.toWorkTypeFormativeFieldDomain(): WorkTypeFormativeFieldDomain {
        return WorkTypeFormativeFieldDomain(
            formativeFieldId = formativeFieldId,
            nameFormativeField = nameFormativeField,
            listWorks = listWorks.mapNotNull { it.toListWorkFormativeFieldDomain() }
        )
    }

    /**
     * Convierte un [ResponseListWork] a [ListWorkFormativeFieldDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para lista de trabajos.
     * @return Un objeto [ListWorkFormativeFieldDomain] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseListWork?.toListWorkFormativeFieldDomain(): ListWorkFormativeFieldDomain? {
        return this?.let {
            ListWorkFormativeFieldDomain(
                workId = workId,
                workName = workName,
                listWorks = listWorks.mapNotNull { it.toListWorkStudentFormativeFieldDomain() }
            )
        }
    }

    /**
     * Convierte un [ResponseListWorkStudent] a [ListWorkStudentFormativeFieldDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para lista de trabajos de estudiantes.
     * @return Un objeto [ListWorkStudentFormativeFieldDomain] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseListWorkStudent?.toListWorkStudentFormativeFieldDomain(): ListWorkStudentFormativeFieldDomain? {
        return this?.let {
            ListWorkStudentFormativeFieldDomain(
                workStudentId = workStudentId,
                workStudentName = workStudentName,
                workStudentDate = workStudentDate
            )
        }
    }

    /**
     * Convierte una lista de [ResponseGetListEvaluationsStudent] a una lista de [EvaluationsStudentDomain] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener evaluaciones de estudiantes.
     * @return Una lista de objetos [EvaluationsStudentDomain] con los datos mapeados. Los elementos nulos son omitidos.
     */
    fun List<ResponseGetListEvaluationsStudent>.toEvaluationsStudentDomain(): List<EvaluationsStudentDomain> {
        return this.map { evaluation ->
            evaluation.let {
                EvaluationsStudentDomain(
                    studentId = it.studentId,
                    evaluationName = it.evaluationName,
                    grade = it.grade,
                    workDate = it.workDate,
                    evaluationId = it.evaluationId
                )
            }
        }
    }

    /**
     * Convierte un [ResponseGetByFieldTypeStudent] a [ByFieldTypeStudentDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener datos por tipo de campo y estudiante.
     * @return Un objeto [ByFieldTypeStudentDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetByFieldTypeStudent.toByFieldTypeStudentDomain(): ByFieldTypeStudentDomain {
        return ByFieldTypeStudentDomain(
            formativeFieldId = formativeFieldId,
            formativeFieldName = formativeFieldName,
            workTypeId = workTypeId,
            workTypeName = workTypeName,
            works = works.mapNotNull { it.toGetListByFieldTypeStudentDomain() }
        )
    }

    /**
     * Convierte un [ResponseGetListByFieldTypeStudent] a [GetListByFieldTypeStudentDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener lista por tipo de campo y estudiante.
     * @return Un objeto [GetListByFieldTypeStudentDomain] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseGetListByFieldTypeStudent?.toGetListByFieldTypeStudentDomain(): GetListByFieldTypeStudentDomain? {
        return this?.let {
            GetListByFieldTypeStudentDomain(
                workId = workId,
                workName = workName,
                workDate = workDate,
                listStudents = listStudents.mapNotNull { it.toGetListByFieldStudentDomain() }
            )
        }
    }

    /**
     * Convierte un [ResponseGetListByFieldStudent] a [GetListByFieldStudentDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener lista por campo y estudiante.
     * @return Un objeto [GetListByFieldStudentDomain] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseGetListByFieldStudent?.toGetListByFieldStudentDomain(): GetListByFieldStudentDomain? {
        return this?.let {
            GetListByFieldStudentDomain(
                studentId = studentId,
                studentName = studentName,
                grade = grade
            )
        }
    }
}