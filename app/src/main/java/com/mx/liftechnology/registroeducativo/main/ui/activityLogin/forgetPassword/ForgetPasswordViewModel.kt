package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.flowlogin.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsUseCase: ValidateFieldsLoginUseCase,
) : ViewModel() {
    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the email field
    private val _emailField = SingleLiveEvent<ModelState<Int, Int>>()
    val emailField: LiveData<ModelState<Int, Int>> get() = _emailField

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * @param email the user
     * */
    fun validateFields(
        email: String,
    ) {
        viewModelScope.launch(dispatcherProvider.io) {
            val emailState = validateFieldsUseCase.validateEmail(email)
            _emailField.postValue(emailState)
            if (emailState is SuccessState) {
                _animateLoader.postValue(LoaderState(true))
            }
        }
    }
}