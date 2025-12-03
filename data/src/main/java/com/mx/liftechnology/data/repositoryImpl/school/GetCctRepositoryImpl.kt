/**
 * @file Define el repositorio para la funcionalidad de obtención de CCT.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.school

import com.mx.liftechnology.core.network.api.SchoolApi
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toData
import com.mx.liftechnology.domain.model.schoolCycle.CCTDomain
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.repository.school.GetCctRepository


/**
 * Implementación de [GetCctRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property cctApiCall La llamada a la API para obtener los datos de la escuela.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetCctRepositoryImpl(
    private val schoolApi: SchoolApi
) : GetCctRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getCct(cct:String): ModelResult<CCTDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { schoolApi.getCct(cct) },
            mapper = { it.toData() }
        )
    }
}