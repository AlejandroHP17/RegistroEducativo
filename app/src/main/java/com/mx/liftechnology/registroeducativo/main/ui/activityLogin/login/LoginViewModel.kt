package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login

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
import com.mx.liftechnology.domain.usecase.flowlogin.LoginUseCase
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel (
    private val loginUseCase: LoginUseCase
): ViewModel()  {
    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // Help to know when the student was save, and post a notification
    private val _emailField = SingleLiveEvent<ModelState<Int>>()
    val emailField: LiveData<ModelState<Int>> get() = _emailField

    // Help to know when the student was save, and post a notification
    private val _passField = SingleLiveEvent<ModelState<Int>>()
    val passField: LiveData<ModelState<Int>> get() = _passField

    // Help to know when the student was save, and post a notification
    private val _responseLogin = SingleLiveEvent<ModelState<Int>>()
    val responseLogin: LiveData<ModelState<Int>> get() = _responseLogin

    fun validateFields(email: String?, pass: String?) {
        coroutine.scopeIO.launch{
            val patEmail = ModelRegex.EMAIL
            when{
                email.isNullOrEmpty() -> {_emailField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                !patEmail.matches(email) -> {_emailField.postValue(ErrorState(ModelCodeError.ET_FORMAT))}
                else -> {_emailField.postValue(SuccessState(ModelCodeSuccess.ET_FORMART))}
            }

            when{
                pass.isNullOrEmpty() -> {_passField.postValue(ErrorState(ModelCodeError.ET_EMPTY)) }
                else -> {_passField.postValue(SuccessState(ModelCodeSuccess.ET_FORMART))}
            }
            Handler(Looper.getMainLooper()).postDelayed({
                if(emailField.value is SuccessState && passField.value is SuccessState){
                    login(email, pass)
                    ///
                }
            },10)

        }
    }

    private fun login(email: String?, pass: String?){
        coroutine.scopeIO.launch {
            runCatching {
                loginUseCase.login(email,pass)
            }.onSuccess {
                _responseLogin.postValue(SuccessState(ModelCodeSuccess.ET_FORMART))
            }.onFailure {
                _responseLogin.postValue(SuccessState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }
}
