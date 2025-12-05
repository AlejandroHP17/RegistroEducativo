/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.partial

import com.mx.liftechnology.core.network.api.PartialApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.PartialMapper.toListPartialDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain
import com.mx.liftechnology.domain.repository.partial.GetListPartialRepository


/**
 * Implementación de [GetListPartialRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property PartialApi La llamada a la API para obtener la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialRepositoryImpl(
    private val partialApi: PartialApi
) : GetListPartialRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getList(
        schoolCycleId : Int
    ): ModelResult<List<ListPartialDomain>, NetworkModelError> {
        return safeApiCall(
            apiCall = { partialApi.getListPartial(schoolCycleId) },
            mapper = { it.toListPartialDomain() }
        )
    }
}