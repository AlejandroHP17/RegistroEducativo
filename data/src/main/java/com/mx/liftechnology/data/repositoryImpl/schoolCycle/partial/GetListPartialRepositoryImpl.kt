/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.schoolCycle.partial

import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toData
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain
import com.mx.liftechnology.domain.repository.schoolCycle.partial.GetListPartialRepository


/**
 * Implementación de [GetListPartialRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListPartialApiCall La llamada a la API para obtener la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialRepositoryImpl(
    private val schoolCycleApi: SchoolCycleApi
) : GetListPartialRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getList(
        schoolCycleId : Int
    ): ModelResult<List<ListPartialDomain>, NetworkModelError> {
        return safeApiCall(
            apiCall = { schoolCycleApi.getListPartial(schoolCycleId) },
            mapper = { it.toData() }
        )
    }
}