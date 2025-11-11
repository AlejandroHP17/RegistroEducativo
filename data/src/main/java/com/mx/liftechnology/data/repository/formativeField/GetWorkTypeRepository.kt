/**
 * @file Define el repositorio para la funcionalidad de obtención de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.apiCall.formativeField.GetListWorkTypeApiCall
import com.mx.liftechnology.data.mapper.FormativeFieldDataToDomainMapper.mapperToModelListWorkTypeData
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de tipos de evaluación.
 * Define el contrato para ejecutar la lógica de obtención de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetWorkTypeRepository{
    /**
     * Ejecuta la petición para obtener la lista de tipos de evaluación.
     *
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeGetListWorkType(teacherId: Int)
            : ModelResult<List<ModelWorkTypeData>, NetworkError>
}

/**
 * Implementación de [GetWorkTypeRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListWorkTypeApiCall La llamada a la API para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetWorkTypeRepositoryImpl(
    private val getListWorkTypeApiCall: GetListWorkTypeApiCall
) : GetWorkTypeRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListWorkType(
        teacherId: Int
    ): ModelResult<List<ModelWorkTypeData>, NetworkError> {
        return try {
            val response = getListWorkTypeApiCall.callApi(teacherId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelListWorkTypeData())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }

}