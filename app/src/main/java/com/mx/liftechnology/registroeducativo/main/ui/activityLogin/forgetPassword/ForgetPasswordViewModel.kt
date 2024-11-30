package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.domain.usecase.flowlogin.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.launch

class ForgetPasswordViewModel (
    private val validateFieldsUseCase: ValidateFieldsLoginUseCase,
) : ViewModel()  {
    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // Observer the email field
    private val _emailField = SingleLiveEvent<ModelState<Int,Int>>()
    val emailField: LiveData<ModelState<Int,Int>> get() = _emailField

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * @param email the user
     * */
    fun validateFields(
        email: String,
    ) {
        coroutine.scopeIO.launch {
            val emailState = validateFieldsUseCase.validateEmail(email)
            _emailField.postValue(emailState)
        }
    }

}