package com.mx.liftechnology.data.repositoryImpl.evaluation

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.core.network.api.RequestListGrades
import com.mx.liftechnology.core.network.api.RequestWorkTypeEvaluations
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toByFieldTypeStudentDomain
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toEvaluationsStudentDomain
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toWorkTypeEvaluationDomain
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toWorkTypeFormativeFieldDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.evaluation.WorkTypeEvaluationDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.ByFieldTypeStudentDomain
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository

/**
 * Implementación de [EvaluationRepository].
 * Se encarga de realizar las llamadas a la API y de gestionar las respuestas de éxito y error
 * para todas las operaciones relacionadas con evaluaciones.
 *
 * @property evaluationApi La llamada a la API para operaciones de evaluaciones.
 * @author Pelkidev
 * @version 2.0.0
 */
class EvaluationRepositoryImpl(
    private val evaluationApi: EvaluationApi
) : EvaluationRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun registerWorkTypeEvaluations(
        formativeFieldId: Int,
        partialId: Int,
        workTypeId: Int,
        nameWork: String,
        workDate: String,
        schoolCycleId: Int,
        grades: List<RequestListGrades>
    ): ModelResult<WorkTypeEvaluationDomain, NetworkModelError> {
        val request = RequestWorkTypeEvaluations(
            formativeFieldId = formativeFieldId,
            partialId = partialId,
            workTypeId = workTypeId,
            nameWork = nameWork,
            workDate = workDate,
            schoolCycleId = schoolCycleId,
            grades = grades
        )

        return safeApiCall(
            apiCall = { evaluationApi.registerWorkTypeEvaluations(request) },
            mapper = { it.toWorkTypeEvaluationDomain() }
        )
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun getListWorkTypeStudent(formativeFieldId: Int): ModelResult<WorkTypeFormativeFieldDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { evaluationApi.getListWorkTypeStudent(formativeFieldId) },
            mapper = { it.toWorkTypeFormativeFieldDomain() }
        )
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun getListEvaluations(
        schoolCycleId: Int,
        partialId: Int,
        formativeFieldId: Int,
        workTypeId: Int,
        studentId: Int,
        workDate: String?,
        workDateFrom: String?,
        workDateTo: String?
    ): ModelResult<List<EvaluationsStudentDomain>, NetworkModelError> {
        return safeApiCall(
            apiCall = {
                evaluationApi.getListEvaluations(
                    formativeFieldId = formativeFieldId,
                    partialId = partialId,
                    workTypeId = workTypeId,
                    schoolCycleId = schoolCycleId,
                    studentId = studentId,
                    workDate = workDate,
                    workDateFrom = workDateFrom,
                    workDateTo = workDateTo,
                )
            },
            mapper = { it.toEvaluationsStudentDomain() }
        )
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun getByFieldType(
        formativeFieldId: Int,
        workTypeId: Int,
        workName: String?,
        workDate: String?
    ): ModelResult<ByFieldTypeStudentDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = {
                evaluationApi.getListByFieldTypeStudent(
                    formativeFieldId = formativeFieldId,
                    workTypeId = workTypeId,
                    workName = workName,
                    workDate = workDate
                )
            },
            mapper = { it.toByFieldTypeStudentDomain() }
        )
    }
}
