package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.data.model.ModelPreference
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.usecase.PreferenceUseCase
import com.mx.liftechnology.domain.usecase.flowlogin.LoginUseCase
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val preference: PreferenceUseCase
) : ViewModel() {
    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // Observer the email field
    private val _emailField = SingleLiveEvent<ModelState<Int>>()
    val emailField: LiveData<ModelState<Int>> get() = _emailField

    // Observer the password field
    private val _passField = SingleLiveEvent<ModelState<Int>>()
    val passField: LiveData<ModelState<Int>> get() = _passField

    // Observer the response of service
    private val _responseLogin = SingleLiveEvent<ModelState<Int>>()
    val responseLogin: LiveData<ModelState<Int>> get() = _responseLogin

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateFields(email: String?, pass: String?, remember: Boolean) {
        coroutine.scopeIO.launch {

            val emailState = loginUseCase.validateEmail(email)
            val passState = loginUseCase.validatePass(pass)

            _emailField.postValue(emailState)
            _passField.postValue(passState)

            if (emailState is SuccessState && passState is SuccessState) {
                login(email, pass, remember)
            }

        }
    }

    /** Request to Login
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun login(email: String?, pass: String?, remember: Boolean) {
        coroutine.scopeIO.launch {
            runCatching {
                loginUseCase.login(email, pass)
            }.onSuccess {
                when (it) {
                    is SuccessState -> {
                        preference.savePreferenceBoolean(ModelPreference.LOGIN, remember)
                        _responseLogin.postValue(SuccessState(ModelCodeSuccess.ET_FORMAT))
                    }

                    else -> {
                        _responseLogin.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
                    }
                }
            }.onFailure {
                _responseLogin.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }
}
