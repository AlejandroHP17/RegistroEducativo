/**
 * @file Define el repositorio para la funcionalidad de registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.apiCall.formativeField.RegisterFormativeFieldsBulkApiCall
import com.mx.liftechnology.core.network.apiCall.formativeField.RequestRegisterFormativeField
import com.mx.liftechnology.data.mapper.FormativeFieldDataToDomainMapper.mapperToModelListFormativeFields
import com.mx.liftechnology.data.model.formativeField.ModelFormativeFieldData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de materias.
 * Define el contrato para ejecutar la lógica de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterFormativeFieldsBulkRepository{
    /**
     * Ejecuta la petición de registro de una materia.
     *
     * @param request Los datos de la petición de registro.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeRegisterFormativeFieldsBulk(request : RequestRegisterFormativeField)
    : ModelResult<ModelFormativeFieldData, NetworkError>
}

/**
 * Implementación de [RegisterFormativeFieldsBulkRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerFormativeFieldsBulkApiCall La llamada a la API para el registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterFormativeFieldsBulkRepositoryImpl(
    private val registerFormativeFieldsBulkApiCall: RegisterFormativeFieldsBulkApiCall
) : RegisterFormativeFieldsBulkRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterFormativeFieldsBulk(
        request : RequestRegisterFormativeField
    ): ModelResult<ModelFormativeFieldData, NetworkError> {
        return try {
            val response = registerFormativeFieldsBulkApiCall.callApi(request)
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