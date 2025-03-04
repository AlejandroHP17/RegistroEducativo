package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.flowlogin.RegisterUseCase
import com.mx.liftechnology.domain.usecase.flowlogin.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerUseCase: RegisterUseCase,
    private val validateFieldsUseCase: ValidateFieldsLoginUseCase
) : ViewModel() {

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer fields
    private val _emailField = SingleLiveEvent<ModelState<String, String>>()
    val emailField: LiveData<ModelState<String, String>> get() = _emailField

    private val _passField = SingleLiveEvent<ModelState<String, String>>()
    val passField: LiveData<ModelState<String, String>> get() = _passField

    private val _repeatPassField = SingleLiveEvent<ModelState<String, String>>()
    val repeatPassField: LiveData<ModelState<String, String>> get() = _repeatPassField

    private val _codeField = SingleLiveEvent<ModelState<String, String>>()
    val codeField: LiveData<ModelState<String, String>> get() = _codeField

    // Observer response
    private val _responseRegister = SingleLiveEvent<ModelState<List<String>?, String>>()
    val responseRegister: LiveData<ModelState<List<String>?, String>> get() = _responseRegister

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * @param email the user
     * @param pass the user
     * @param repeatPass the user
     * @param code the user
     * */
    fun validateFields(
        email: String,
        pass: String,
        repeatPass: String,
        code: String
    ) {
        viewModelScope.launch(dispatcherProvider.io) {
            val emailState = validateFieldsUseCase.validateEmail(email)
            val passState = validateFieldsUseCase.validatePassRegister(pass)
            val repeatPassState = validateFieldsUseCase.validateRepeatPass(pass, repeatPass)
            val codeState = validateFieldsUseCase.validateCode(code)

            _emailField.postValue(emailState)
            _passField.postValue(passState)
            _repeatPassField.postValue(repeatPassState)
            _codeField.postValue(codeState)

            if (emailState is SuccessState && passState is SuccessState
                && repeatPassState is SuccessState && codeState is SuccessState
            ) {
                _animateLoader.postValue(LoaderState(true))
                register(email, pass, code)
            }
        }
    }

    /** Request to register
     * @author pelkidev
     * @since 1.0.0
     * @param email the user
     * @param pass the user
     * @param code the user
     * */
    private fun register(
        email: String,
        pass: String,
        code: String
    ) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerUseCase.putRegister(email, pass, code)
            }.onSuccess {
                _animateLoader.postValue(LoaderState(false))
                _responseRegister.postValue(it)
            }.onFailure {
                _animateLoader.postValue(LoaderState(false))
                _responseRegister.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }
}