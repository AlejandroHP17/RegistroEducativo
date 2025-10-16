/**
 * @file Define el repositorio para la funcionalidad de inicio de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.flowLogin.login

import com.mx.liftechnology.core.network.apiCall.flowLogin.LoginApiCall
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestLogin
import com.mx.liftechnology.core.network.apiCall.flowLogin.ResponseLogin
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.MessageError
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el inicio de sesión.
 * Define el contrato para ejecutar la lógica de inicio de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface LoginRepository {
    /**
     * Ejecuta la petición de inicio de sesión.
     *
     * @param request Los datos de la petición de inicio de sesión.
     * @return Un [ResultService] que indica el resultado de la operación.
     */
    suspend fun executeLogin(request: RequestLogin): ResultService<ResponseLogin, FailureService>
}

/**
 * Implementación de [LoginRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property loginApiCall La llamada a la API para el inicio de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginRepositoryImp(
    private val loginApiCall: LoginApiCall,
) : LoginRepository {

    /**
     * Realiza la llamada de red para el inicio de sesión y gestiona la respuesta.
     *
     * La anotación `{@inheritDoc}` indica que esta documentación hereda y cumple
     * el contrato definido en [LoginRepository.executeLogin].
     */
    override suspend fun executeLogin(
        request: RequestLogin,
    ): ResultService<ResponseLogin, FailureService> {
        return try {
            val response = loginApiCall.callApi(request)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    ResultSuccess(it)
                } ?: run {
                    val exception = NullPointerException(MessageError.UNEXPECTED_NULL_BODY_ERROR_MESSAGE)
                    ResultError(ExceptionHandler.handleException(exception))
                }
            } else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}

