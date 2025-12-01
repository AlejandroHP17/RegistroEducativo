/**
 * @file Define el repositorio para la funcionalidad del menú principal.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.schoolCycle.menu

import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toData
import com.mx.liftechnology.data.model.schoolCycle.ModelSchoolCycleData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para el menú principal.
 * Define el contrato para obtener la lista de grupos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface MenuRepository{
    /**
     * Obtiene la lista de ciclos escolares.
     *
     * @param teacherId El ID del profesor.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getCycleSchool(
        teacherId: Int
    ): ModelResult<List<ModelSchoolCycleData>, NetworkModelError>
}

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
    override suspend fun getCycleSchool(teacherId: Int): ModelResult<List<ModelSchoolCycleData>, NetworkModelError> {
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