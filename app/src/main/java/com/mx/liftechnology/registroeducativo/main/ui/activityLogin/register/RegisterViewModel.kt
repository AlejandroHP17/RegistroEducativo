package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelRegex
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.usecase.flowlogin.CCTUseCase
import com.mx.liftechnology.domain.usecase.flowlogin.RegisterUseCase
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val cctUseCase : CCTUseCase,
    private val registerUseCase : RegisterUseCase
): ViewModel()  {
    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // Help to know when the student was save, and post a notification
    private val _responseCCT = SingleLiveEvent<ModelState<List<DataCCT?>?>>()
    val responseCCT: LiveData<ModelState<List<DataCCT?>?>> get() = _responseCCT

    // Help to know when the student was save, and post a notification
    private val _emailField = SingleLiveEvent<ModelState<Int>>()
    val emailField: LiveData<ModelState<Int>> get() = _emailField

    // Help to know when the student was save, and post a notification
    private val _passField = SingleLiveEvent<ModelState<Int>>()
    val passField: LiveData<ModelState<Int>> get() = _passField

    // Help to know when the student was save, and post a notification
    private val _repeatPassField = SingleLiveEvent<ModelState<Int>>()
    val repeatPassField: LiveData<ModelState<Int>> get() = _repeatPassField

    // Help to know when the student was save, and post a notification
    private val _cctField = SingleLiveEvent<ModelState<Int>>()
    val cctField: LiveData<ModelState<Int>> get() = _cctField

    // Help to know when the student was save, and post a notification
    private val _codeField = SingleLiveEvent<ModelState<Int>>()
    val codeField: LiveData<ModelState<Int>> get() = _codeField


    // Help to know when the student was save, and post a notification
    private val _responseRegister = SingleLiveEvent<ModelState<Int>>()
    val responseRegister: LiveData<ModelState<Int>> get() = _responseRegister

    fun getCCT(){
        coroutine.scopeIO.launch {
            runCatching {
                cctUseCase.getCCT()
            }.onSuccess {
                when(it){
                    is SuccessState ->{_responseCCT.postValue(SuccessState(it.result.data))}
                    else -> { _responseCCT.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))}
                }
            }.onFailure {
                _responseCCT.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }

    fun validateFields(
        email: String,
        pass: String,
        repeatPass: String,
        cct: String,
        code: String
    ) {
        coroutine.scopeIO.launch {
            val patEmail = ModelRegex.EMAIL
            when{
                email.isEmpty() -> {_emailField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                !patEmail.matches(email) -> {_emailField.postValue(ErrorState(ModelCodeError.ET_FORMAT))}
                else -> {_emailField.postValue(SuccessState(ModelCodeSuccess.ET_FORMAT))}
            }
            when{
                pass.isEmpty() -> {_passField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                !validPass(pass) -> {_passField.postValue(ErrorState(ModelCodeError.ET_INCORRECT_FORMAT)) }
                else -> {_passField.postValue(SuccessState(ModelCodeSuccess.ET_FORMAT))}
            }

            when{
                repeatPass.isEmpty() -> {_repeatPassField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                repeatPass !=  pass-> {_repeatPassField.postValue(ErrorState(ModelCodeError.ET_DIFFERENT)) }
                else -> {_repeatPassField.postValue(SuccessState(ModelCodeSuccess.ET_FORMAT))}
            }

            when{
                cct.isEmpty() -> {_cctField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                !validCCT(cct) -> {_cctField.postValue(ErrorState(ModelCodeError.ET_NOT_FOUND)) }
                else -> {_cctField.postValue(SuccessState(ModelCodeSuccess.ET_FORMAT))}
            }

            when{
                code.isEmpty() -> {_codeField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                else -> {_codeField.postValue(SuccessState(ModelCodeSuccess.ET_FORMAT))}
            }
            Handler(Looper.getMainLooper()).postDelayed({
                if(emailField.value is SuccessState && passField.value is SuccessState
                    && repeatPassField.value is SuccessState && cctField.value is SuccessState
                    && codeField.value is SuccessState
                ){
                    register(email, pass, cct, code)
                }
            },10)
        }
    }

    private fun register(email: String,
                         pass: String,
                         cct: String,
                         code: String){

        coroutine.scopeIO.launch {
            runCatching {
                registerUseCase.putRegister(email,pass,cct,code)
            }.onSuccess {
                when(it){
                    is SuccessState -> { _responseRegister.postValue(SuccessState(ModelCodeSuccess.ET_FORMAT)) }
                    else -> {_responseRegister.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))}
                }
            }.onFailure {
                _responseRegister.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }

    private fun validCCT(cct: String): Boolean {
        val currentState = responseCCT.value
        return when (currentState) {
            is SuccessState -> {
                // Usa safe call para evitar errores si `result` es nulo
                currentState.result?.any { cct == it?.cct } ?: false
            }
            else -> false
        }
    }

    private fun validPass(pass : String): Boolean{
        val regex = ModelRegex.PASS
        return regex.matches(pass)
    }
}