package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.core.network.callapi.User
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.usecase.flowlogin.LoginUseCase
import com.mx.liftechnology.domain.usecase.flowlogin.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

class LoginViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val loginUseCase: LoginUseCase,
    private val validateFieldsUseCase: ValidateFieldsLoginUseCase,
    private val preference: PreferenceUseCase
) : ViewModel() {

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the email field
    private val _emailField = SingleLiveEvent<ModelState<Int, Int>>()
    val emailField: LiveData<ModelState<Int, Int>> get() = _emailField

    // Observer the password field
    private val _passField = SingleLiveEvent<ModelState<Int, Int>>()
    val passField: LiveData<ModelState<Int, Int>> get() = _passField

    // Observer the response of service
    private val _responseLogin = SingleLiveEvent<ModelState<User?, *>>()
    val responseLogin: LiveData<ModelState<User?, *>> get() = _responseLogin

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * @param email the user
     * @param pass the user
     * @param remember to enter on app automatically
     * */
    fun validateFields(email: String?, pass: String?, remember: Boolean) {
        viewModelScope.launch(dispatcherProvider.io) {
            val emailState = validateFieldsUseCase.validateEmail(email)
            val passState = validateFieldsUseCase.validatePass(pass)

            _emailField.postValue(emailState)
            _passField.postValue(passState)

            if (emailState is SuccessState && passState is SuccessState) {
                _animateLoader.postValue(LoaderState(true))
                login(email, pass, remember)
            }
        }
    }

    /** Request to Login
     * @author pelkidev
     * @since 1.0.0
     * @param email the user
     * @param pass the user
     * @param remember to enter on app automatically
     * */
    private fun login(email: String?, pass: String?, remember: Boolean) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                loginUseCase.login(email, pass)
            }.onSuccess {
                _animateLoader.postValue(LoaderState(false))
                if (it is SuccessState) preference.savePreferenceBoolean(
                    ModelPreference.LOGIN,
                    remember
                )
                _responseLogin.postValue(it)
            }.onFailure {
                _animateLoader.postValue(LoaderState(false))
                _responseLogin.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }
}
