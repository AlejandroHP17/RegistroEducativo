package com.mx.liftechnology.data.repositoryImpl.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toFormativeFieldDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.repository.formativeFields.GetListFormativeFieldRepository


/**
 * Implementación de [GetListFormativeFieldRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property FormativeFieldApi La llamada a la API para obtener la lista de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListFormativeFieldRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
) : GetListFormativeFieldRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getList(
        cycleSchoolId: Int
    ) : ModelResult<List<FormativeFieldDomain>, NetworkModelError> {
        return safeApiCall(
            apiCall = { formativeFieldApi.getListFormativeFields(cycleSchoolId) },
            mapper = { it.toFormativeFieldDomain() }
        )
    }
}