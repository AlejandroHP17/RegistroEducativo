/**
 * @file Define el repositorio para la funcionalidad de registro de usuarios.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowLogin.register

import com.mx.liftechnology.core.network.apiCall.flowLogin.RegisterUserApiCall
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestRegisterUser
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de usuarios.
 * Define el contrato para ejecutar la lógica de registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterUserRepository{
  /**
   * Ejecuta la petición de registro de usuario.
   *
   * @param request Los datos de la petición de registro.
   * @return Un [ResultService] que indica el resultado de la operación.
   */
  suspend fun executeRegisterUser(request: RequestRegisterUser)
  : ResultService<List<String>?, FailureService>
}

/**
 * Implementación de [RegisterUserRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerUserApiCall La llamada a la API para el registro de usuarios.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserRepositoryImp(
    private val registerUserApiCall: RegisterUserApiCall
) : RegisterUserRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterUser(
        request: RequestRegisterUser
    ): ResultService<List<String>?, FailureService> {
        return try {
            val response = registerUserApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}