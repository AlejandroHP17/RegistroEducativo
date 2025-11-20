/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.apiCall.formativeField.GetListFormativeFieldsApiCall
import com.mx.liftechnology.data.mapper.FormativeFieldDataToDomainMapper.mapperToModelListFormativeFields
import com.mx.liftechnology.data.model.formativeField.ModelFormativeFieldData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de la lista de materias.
 * Define el contrato para ejecutar la lógica de obtención de la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListFormativeFieldRepository{
    /**
     * Ejecuta la petición para obtener la lista de materias.
     *
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeGetListFormativeFields(cycleSchoolId: Int)
    : ModelResult<List<ModelFormativeFieldData>, NetworkModelError>
}

/**
 * Implementación de [GetListFormativeFieldRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListFormativeFieldsApiCall La llamada a la API para obtener la lista de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListFormativeFieldRepositoryImpl(
    private val getListFormativeFieldsApiCall : GetListFormativeFieldsApiCall
) : GetListFormativeFieldRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListFormativeFields(
        cycleSchoolId: Int
    ) : ModelResult<List<ModelFormativeFieldData>, NetworkModelError> {
        return try {
            val response = getListFormativeFieldsApiCall.callApi(cycleSchoolId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelListFormativeFields())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e:Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }
}