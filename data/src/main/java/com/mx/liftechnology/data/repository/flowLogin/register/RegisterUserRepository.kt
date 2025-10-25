/**
 * @file Define el repositorio para la funcionalidad de registro de usuarios.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowLogin.register

import com.mx.liftechnology.core.network.apiCall.flowLogin.RegisterUserApiCall
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestRegisterUser
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.SuccessResult
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
  : ModelResult<List<String?>, NetworkError>
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
     * Realiza la llamada de red para el registro de usuario
     *
     * La anotación `{@inheritDoc}` indica que esta documentación hereda y cumple
     * el contrato definido en [RegisterUserRepository.executeRegisterUser].
     */
    override suspend fun executeRegisterUser(
        request: RequestRegisterUser
    ): ModelResult<List<String?>, NetworkError> {
        return try {
            val response = registerUserApiCall.callApi(request)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it)
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            }
            else  ErrorResult(NetworkException.handleException(HttpException(response)))
        } catch (e: Exception) {
            ErrorResult(NetworkError.UNKNOWN)
        }
    }
}