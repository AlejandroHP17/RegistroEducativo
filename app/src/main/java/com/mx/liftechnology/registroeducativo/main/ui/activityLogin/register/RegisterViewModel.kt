package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.usecase.flowlogin.CCTUseCase
import com.mx.liftechnology.domain.usecase.flowlogin.RegisterUseCase
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val cctUseCase: CCTUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // Observer the response of service
    private val _responseCCT = SingleLiveEvent<ModelState<List<DataCCT?>?>>()
    val responseCCT: LiveData<ModelState<List<DataCCT?>?>> get() = _responseCCT

    private val _responseRegister = SingleLiveEvent<ModelState<Int>>()
    val responseRegister: LiveData<ModelState<Int>> get() = _responseRegister

    // Observer the email field
    private val _emailField = SingleLiveEvent<ModelState<Int>>()
    val emailField: LiveData<ModelState<Int>> get() = _emailField

    // Observer the pass field
    private val _passField = SingleLiveEvent<ModelState<Int>>()
    val passField: LiveData<ModelState<Int>> get() = _passField

    // Observer the repeatPass field
    private val _repeatPassField = SingleLiveEvent<ModelState<Int>>()
    val repeatPassField: LiveData<ModelState<Int>> get() = _repeatPassField

    // Observer the cct field
    private val _cctField = SingleLiveEvent<ModelState<Int>>()
    val cctField: LiveData<ModelState<Int>> get() = _cctField

    // Observer the code field
    private val _codeField = SingleLiveEvent<ModelState<Int>>()
    val codeField: LiveData<ModelState<Int>> get() = _codeField

    fun getCCT() {
        coroutine.scopeIO.launch {
            runCatching {
                cctUseCase.getCCT()
            }.onSuccess {
                when (it) {
                    is SuccessState -> {
                        _responseCCT.postValue(SuccessState(it.result.data))
                    }

                    else -> {
                        _responseCCT.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
                    }
                }
            }.onFailure {
                _responseCCT.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateFields(
        email: String,
        pass: String,
        repeatPass: String,
        cct: String,
        code: String
    ) {
        coroutine.scopeIO.launch {

            val emailState = registerUseCase.validateEmail(email)
            val passState = registerUseCase.validatePass(pass)
            val repeatPassState = registerUseCase.validateRepeatPass(pass, repeatPass)
            val cctState = registerUseCase.validateCCT(cct, responseCCT)
            val codeState = registerUseCase.validateCode(code)

            _emailField.postValue(emailState)
            _passField.postValue(passState)
            _repeatPassField.postValue(repeatPassState)
            _cctField.postValue(cctState)
            _codeField.postValue(codeState)

            if (emailState is SuccessState && passState is SuccessState
                && repeatPassState is SuccessState && cctState is SuccessState
                && codeState is SuccessState
            ) {
                register(email, pass, cct, code)
            }
        }
    }

    /** Request to register
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun register(
        email: String,
        pass: String,
        cct: String,
        code: String
    ) {

        coroutine.scopeIO.launch {
            runCatching {
                registerUseCase.putRegister(email, pass, cct, code)
            }.onSuccess {
                when (it) {
                    is SuccessState -> {
                        _responseRegister.postValue(SuccessState(ModelCodeSuccess.ET_FORMAT))
                    }

                    else -> {
                        _responseRegister.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
                    }
                }
            }.onFailure {
                _responseRegister.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }
}