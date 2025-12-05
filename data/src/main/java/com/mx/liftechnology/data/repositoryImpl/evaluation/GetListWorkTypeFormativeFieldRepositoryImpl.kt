package com.mx.liftechnology.data.repositoryImpl.evaluation

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toWorkTypeFormativeFieldDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.repository.evaluation.GetListWorkTypeFormativeFieldRepository


/**
 * Implementación de [GetListWorkTypeFormativeFieldRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property EvaluationApi La llamada a la API para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListWorkTypeFormativeFieldRepositoryImpl (
    private val evaluationApi: EvaluationApi
): GetListWorkTypeFormativeFieldRepository {
    /**
     * {@inheritDoc}
     */
    override suspend fun getList(formativeFieldId:Int): ModelResult<WorkTypeFormativeFieldDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { evaluationApi.getListWorkTypeStudent(formativeFieldId) },
            mapper = { it.toWorkTypeFormativeFieldDomain() }
        )
    }
}