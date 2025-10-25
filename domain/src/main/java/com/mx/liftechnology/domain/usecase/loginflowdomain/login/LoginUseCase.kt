package com.mx.liftechnology.domain.usecase.loginflowdomain.login

import android.os.Build
import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestLogin
import com.mx.liftechnology.core.network.apiCall.flowLogin.ResponseLogin
import com.mx.liftechnology.core.network.apiCall.flowLogin.UserLogin
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.LocationHelper
import com.mx.liftechnology.data.repository.flowLogin.login.LoginRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

/**
 * Caso de uso para gestionar el inicio de sesión de un usuario.
 * Encapsula la lógica de negocio para validar credenciales, obtener la ubicación, interactuar con el repositorio y guardar las preferencias de sesión.
 *
 * @property repositoryLogin El repositorio para las operaciones de inicio de sesión.
 * @property locationHelper Utilidad para obtener la ubicación actual del dispositivo.
 * @property preference Caso de uso para la gestión de las preferencias de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginUseCase(
    private val repositoryLogin: LoginRepository,
    private val locationHelper: LocationHelper,
    private val preference: PreferenceUseCase
) {

    /**
     * Ejecuta el proceso de inicio de sesión.
     * Primero, valida las entradas locales. Si son válidas, procede a hacer la llamada de red.
     *
     * @param email El correo electrónico del usuario.
     * @param pass La contraseña del usuario.
     * @param remember Indica si la sesión del usuario debe ser recordada.
     * @return Un [ModelResult] que puede ser:
     * - [SuccessResult<UserLogin>] si el inicio de sesión es exitoso.
     * - [ErrorResult<LocalError>] si hay un error de validación local (campos vacíos).
     * - [ErrorResult<NetworkError>] si hay un error de red o del servidor.
     */
    suspend operator fun invoke (email: String?, pass: String?, remember: Boolean = false): ModelResult<UserLogin, Error> {
        // 1. Validación de Lógica de Negocio (Local)
        if (email.isNullOrBlank() || pass.isNullOrBlank()) {
            return ErrorResult(LocalError.USER_INCOMPLETE_DATA)
        }

        val location = locationHelper.getCurrentLocation()
        val latitude = location?.latitude
        val longitude = location?.longitude

        val request = RequestLogin(
            email = email.lowercase(),
            password = pass,
            latitude = latitude?.toString().orEmpty(),
            longitude = longitude?.toString().orEmpty(),
            imei = Build.FINGERPRINT + Build.ID
        )

        // 2. Ejecución de la llamada de red
        return runCatching { repositoryLogin.executeLogin(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        val userLogin = result.data.userLogin
                        if (userLogin != null && savePreferences(result.data, remember)) {
                            SuccessResult(userLogin)
                        } else {
                            ErrorResult(LocalError.RESPONSE_INCOMPLETE_DATA) // Error si no se pueden guardar las preferencias o el usuario es nulo
                        }
                    }
                    is ErrorResult -> {
                        ErrorResult(result.error) // Mapea el error de la capa de datos al dominio
                    }
                }
            },
            onFailure = { ErrorResult(NetworkError.UNKNOWN) } // Captura excepciones como problemas de conectividad
        )
    }

    /**
     * Guarda las preferencias del usuario después de un inicio de sesión exitoso.
     *
     * @param result Los datos de la respuesta de inicio de sesión.
     * @param remember Indica si se debe guardar la sesión.
     * @return `true` si las preferencias se guardaron correctamente, `false` en caso contrario.
     */
    private fun savePreferences(result: ResponseLogin, remember:Boolean): Boolean {
        return result.userLogin?.let {
            preference.savePreferenceString(ModelPreference.ACCESS_TOKEN, result.accessToken)
            preference.savePreferenceInt(ModelPreference.ID_USER, it.userId)
            preference.savePreferenceInt(ModelPreference.ID_ROLE,
                if (it.teacherId == null) it.studentId
                else it.teacherId
            )
            preference.savePreferenceString(ModelPreference.USER_ROLE, it.role)
            preference.savePreferenceBoolean(ModelPreference.LOGIN, remember)
            true
        } ?: false
    }
}