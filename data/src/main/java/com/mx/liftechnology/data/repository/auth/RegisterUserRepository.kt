package com.mx.liftechnology.data.repository.auth

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.core.network.api.RequestRegisterUser
import com.mx.liftechnology.data.mapper.AuthMapper.toData
import com.mx.liftechnology.data.model.auth.ModelRegisterUserData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para el registro de usuarios.
 * Define el contrato para ejecutar la lógica de registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterUserRepository{
  /**
   * Realiza la petición de registro de usuario.
   *
   * @param email El correo electrónico del usuario.
   * @param pass La contraseña del usuario.
   * @param activationCode El código de activación.
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun register(
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
     * {@inheritDoc}
     */
    override suspend fun register(
        email: String,
        pass: String,
        activationCode: String
    ): ModelResult<ModelRegisterUserData, NetworkModelError> {
        val request = RequestRegisterUser(
            email = email.lowercase(),
            password = pass,
            activationCode = activationCode
        )

        return authApi.register(request).executeOrError { it.toData() }
    }
}