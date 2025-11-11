/**
 * @file Define el repositorio para la funcionalidad de registro de escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.schoolCycle.school

import com.mx.liftechnology.core.network.apiCall.schoolCycle.RegisterSchoolCycleApiCall
import com.mx.liftechnology.core.network.apiCall.schoolCycle.RequestRegisterSchoolCycle
import com.mx.liftechnology.data.mapper.SchoolCycleDataToDomainMapper.mapperToRegisterCycleSchool
import com.mx.liftechnology.data.model.schoolCycle.ModelRegisterSchoolCycleData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de escuelas.
 * Define el contrato para ejecutar la lógica de registro de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterCycleSchoolRepository{
  /**
   * Ejecuta la petición de registro de una escuela.
   *
   * @param request Los datos de la petición de registro.
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun executeRegisterCycleSchool(
      request : RequestRegisterSchoolCycle
  ): ModelResult<ModelRegisterSchoolCycleData, NetworkError>
}

/**
 * Implementación de [RegisterCycleSchoolRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerSchoolCycleApiCall La llamada a la API para el registro de escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterCycleSchoolRepositoryImpl(
    private val registerSchoolCycleApiCall: RegisterSchoolCycleApiCall
) : RegisterCycleSchoolRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterCycleSchool(
        request : RequestRegisterSchoolCycle
    ): ModelResult<ModelRegisterSchoolCycleData, NetworkError> {
        return try {
            val response = registerSchoolCycleApiCall.callApi(request)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToRegisterCycleSchool())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}