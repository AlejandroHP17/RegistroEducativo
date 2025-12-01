/**
 * @file Define el repositorio para la funcionalidad de inicio de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.auth

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.core.network.api.RequestLogin
import com.mx.liftechnology.data.mapper.AuthMapper.mapperToGetLoginData
import com.mx.liftechnology.data.model.auth.LoginData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
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
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeLogin(
        email : String,
        password : String,
        latitude : Double,
        longitude : Double,
        imei : String
    ): ModelResult<LoginData, NetworkModelError>
}

/**
 * Implementación de [LoginRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property loginApiCall La llamada a la API para el inicio de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginRepositoryImpl(
    private val authApi: AuthApi,
) : LoginRepository {

    /**
     * Realiza la llamada de red para el inicio de sesión y gestiona la respuesta.
     *
     * La anotación `{@inheritDoc}` indica que esta documentación hereda y cumple
     * el contrato definido en [LoginRepository.executeLogin].
     */
    override suspend fun executeLogin(
        email : String,
        password : String,
        latitude : Double,
        longitude : Double,
        imei : String
    ): ModelResult<LoginData, NetworkModelError> {

        val request = RequestLogin(
            email = email,
            password = password,
            latitude = latitude,
            longitude = longitude,
            imei = imei
        )

        return try {
            val response = authApi.login(request)
            if (response.isSuccessful && response.body()?.data != null) SuccessResult(response.body()?.data!!.mapperToGetLoginData())
            else ErrorResult(NetworkException.handleException(HttpException(response)))
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}

