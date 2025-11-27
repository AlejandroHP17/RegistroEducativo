/**
 * @file Define el repositorio para la funcionalidad del menú principal.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.schoolCycle.menu

import com.mx.liftechnology.core.network.apiCall.schoolCycle.GroupApiCall
import com.mx.liftechnology.data.mapper.SchoolCycleDataToDomainMapper.mapperToCycleSchool
import com.mx.liftechnology.data.model.schoolCycle.ModelSchoolCycleData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el menú principal.
 * Define el contrato para obtener la lista de grupos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface MenuRepository{
    /**
     * Ejecuta la petición para obtener la lista de grupos.
     *
     * @param request Los datos de la petición.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeGetCycleSchool(
        request: Int
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
    private val groupApiCall: GroupApiCall
): MenuRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetCycleSchool(request: Int): ModelResult<List<ModelSchoolCycleData>, NetworkModelError> {
        return try {
            val response = groupApiCall.callApi(teacherId = request)
            if (response.isSuccessful && response.body()?.data != null) {
                if(response.body()?.data?.isNotEmpty() == true) SuccessResult(response.body()?.data.mapperToCycleSchool())
                else ErrorResult(NetworkModelError.EMPTY)
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}