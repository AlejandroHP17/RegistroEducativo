package com.mx.liftechnology.data.repository.auth

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.core.network.api.RequestRegisterUser
import com.mx.liftechnology.data.mapper.AuthDataToDomainMapper.mapperToRegisterUser
import com.mx.liftechnology.data.model.auth.ModelRegisterUserData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
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
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun executeRegisterUser(
      email: String,
      pass: String,
      activationCode: String
  )
  : ModelResult<ModelRegisterUserData, NetworkModelError>
}

/**
 * Implementación de [RegisterUserRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerUserApiCall La llamada a la API para el registro de usuarios.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserRepositoryImpl(
    private val authApi: AuthApi
) : RegisterUserRepository {

    /**
     * Realiza la llamada de red para el registro de usuario
     *
     * La anotación `{@inheritDoc}` indica que esta documentación hereda y cumple
     * el contrato definido en [RegisterUserRepository.executeRegisterUser].
     */
    override suspend fun executeRegisterUser(
        email: String,
        pass: String,
        activationCode: String
    ): ModelResult<ModelRegisterUserData, NetworkModelError> {

        val request = RequestRegisterUser(
            email = email.lowercase(),
            password = pass,
            activationCode = activationCode
        )

        return try {
            val response = authApi.register(request)
            if (response.isSuccessful && response.body()?.data != null) SuccessResult(response.body()?.data!!.mapperToRegisterUser())
            else  ErrorResult(NetworkException.handleException(HttpException(response)))
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}