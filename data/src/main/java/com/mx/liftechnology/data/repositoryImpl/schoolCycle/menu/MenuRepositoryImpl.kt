/**
 * @file Define el repositorio para la funcionalidad del menú principal.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.schoolCycle.menu

import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toData
import com.mx.liftechnology.domain.model.schoolCycle.SchoolCycleDomain
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.data.util.executeOrError
import com.mx.liftechnology.domain.repository.schoolCycle.menu.MenuRepository

/**
 * Implementación de [MenuRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property groupApiCall La llamada a la API para obtener la lista de grupos.
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuRepositoryImpl(
    private val schoolCycleApi: SchoolCycleApi
): MenuRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getCycleSchool(teacherId: Int): ModelResult<List<SchoolCycleDomain>, NetworkModelError> {
        val result = schoolCycleApi.getGroup(teacherId = teacherId).executeOrError { it.toData() }
        return when (result) {
            is SuccessResult -> {
                if (result.data.isNotEmpty()) {
                    result
                } else {
                    ErrorResult(NetworkModelError.EMPTY)
                }
            }
            is ErrorResult -> result
        }
    }
}