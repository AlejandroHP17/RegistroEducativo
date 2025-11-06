package com.mx.liftechnology.domain.usecase.loginflowdomain.login

import com.mx.liftechnology.core.network.apiCall.flowLogin.ResponseUserData
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowLogin.login.GetDataUserRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

class GetDataUserUseCase(
    private val preference: PreferenceUseCase,
    private val getDataUserRepository: GetDataUserRepository,
) {
    suspend operator fun invoke(remember: Boolean): ModelResult<ResponseUserData, Error> {

        // 2. Ejecución de la llamada de red
        return runCatching { getDataUserRepository.executeGetData() }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        val userLogin = result.data

                            savePreferences(result.data, remember)
                            SuccessResult(userLogin )
                       }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkError.UNKNOWN) }
        )
    }


    /**
     * Guarda las preferencias del usuario después de un inicio de sesión exitoso.
     *
     * @param result Los datos de la respuesta de inicio de sesión.
     * @param remember Indica si se debe guardar la sesión.
     * @return `true` si las preferencias se guardaron correctamente, `false` en caso contrario.
     */
    private fun savePreferences(result: ResponseUserData, remember: Boolean) {

        preference.savePreferenceInt(ModelPreference.ID_USER, result.userId)
        preference.savePreferenceInt(ModelPreference.ID_USER_LEVEL, result.accessLevelId)
        preference.savePreferenceBoolean(ModelPreference.REMEMBER_LOGIN, remember)
    }
}