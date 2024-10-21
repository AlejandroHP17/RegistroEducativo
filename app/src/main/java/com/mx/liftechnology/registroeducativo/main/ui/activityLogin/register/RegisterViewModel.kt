package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.util.ErrorState
import com.mx.liftechnology.core.util.ModelCodeError
import com.mx.liftechnology.core.util.ModelCodeSuccess
import com.mx.liftechnology.core.util.ModelRegex
import com.mx.liftechnology.core.util.ModelState
import com.mx.liftechnology.core.util.SuccessState
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel()  {
    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

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
    //val responseRegister: LiveData<ModelState<Int>> get() = _responseRegister



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
                else -> {_emailField.postValue(SuccessState(ModelCodeSuccess.ET_FORMART))}
            }
            when{
                pass.isEmpty() -> {_passField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                else -> {_passField.postValue(SuccessState(ModelCodeSuccess.ET_FORMART))}
            }

            when{
                repeatPass.isEmpty() -> {_repeatPassField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                repeatPass !=  pass-> {_repeatPassField.postValue(ErrorState(ModelCodeError.ET_DIFFERENT)) }
                else -> {_repeatPassField.postValue(SuccessState(ModelCodeSuccess.ET_FORMART))}
            }

            when{
                cct.isEmpty() -> {_cctField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                validCCT() -> {_cctField.postValue(ErrorState(ModelCodeError.ET_NOT_FOUND)) }
                else -> {_cctField.postValue(SuccessState(ModelCodeSuccess.ET_FORMART))}
            }

            when{
                code.isEmpty() -> {_codeField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                else -> {_codeField.postValue(SuccessState(ModelCodeSuccess.ET_FORMART))}
            }
            Handler(Looper.getMainLooper()).postDelayed({
                if(emailField.value is SuccessState && passField.value is SuccessState
                    && repeatPassField.value is SuccessState && cctField.value is SuccessState
                    && codeField.value is SuccessState){
                    _responseRegister.postValue(SuccessState(ModelCodeSuccess.ET_FORMART))
                }
            },10)
        }
    }

    private fun validCCT():Boolean {
        return false
    }
}