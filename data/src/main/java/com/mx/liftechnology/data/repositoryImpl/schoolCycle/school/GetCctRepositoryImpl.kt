/**
 * @file Define el repositorio para la funcionalidad de obtención de CCT.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.schoolCycle.school

import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toData
import com.mx.liftechnology.domain.model.schoolCycle.CCTDomain
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.repository.schoolCycle.school.GetCctRepository


/**
 * Implementación de [GetCctRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property cctApiCall La llamada a la API para obtener los datos de la escuela.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetCctRepositoryImpl(
    private val schoolCycleApi: SchoolCycleApi
) : GetCctRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getCct(cct:String): ModelResult<CCTDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { schoolCycleApi.getCct(cct) },
            mapper = { it.toData() }
        )
    }
}