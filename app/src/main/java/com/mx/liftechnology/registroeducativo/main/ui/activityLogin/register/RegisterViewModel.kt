package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
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
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean,Int>>()
    val animateLoader: LiveData< ModelState<Boolean,Int>> get() = _animateLoader

    private val _responseRegister = SingleLiveEvent<ModelState<String?,String>>()
    val responseRegister: LiveData<ModelState<String?,String>> get() = _responseRegister

    // Observer the email field
    private val _emailField = SingleLiveEvent<ModelState<Int,Int>>()
    val emailField: LiveData<ModelState<Int,Int>> get() = _emailField

    // Observer the pass field
    private val _passField = SingleLiveEvent<ModelState<Int,Int>>()
    val passField: LiveData<ModelState<Int,Int>> get() = _passField

    // Observer the repeatPass field
    private val _repeatPassField = SingleLiveEvent<ModelState<Int,Int>>()
    val repeatPassField: LiveData<ModelState<Int,Int>> get() = _repeatPassField

    // Observer the code field
    private val _codeField = SingleLiveEvent<ModelState<Int,Int>>()
    val codeField: LiveData<ModelState<Int,Int>> get() = _codeField

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
        viewModelScope.launch(dispatcherProvider.io)  {
            val emailState = validateFieldsUseCase.validateEmail(email)
            val passState = validateFieldsUseCase.validatePass(pass)
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
        viewModelScope.launch(dispatcherProvider.io)  {
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