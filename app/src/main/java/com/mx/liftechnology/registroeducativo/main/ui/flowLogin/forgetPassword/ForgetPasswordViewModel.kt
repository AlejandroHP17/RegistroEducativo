package com.mx.liftechnology.registroeducativo.main.ui.flowLogin.forgetPassword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelLoginStateUI
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsUseCase: ValidateFieldsLoginFlowUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelLoginStateUI())
    val uiState: StateFlow<ModelLoginStateUI> = _uiState.asStateFlow()

    private val _emailState = MutableStateFlow(ModelStateOutFieldText())
    val emailState: StateFlow<ModelStateOutFieldText> = _emailState.asStateFlow()

    fun onEmailChanged(email: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _emailState.update { email.stringToModelStateOutFieldText() }
        }
    }

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateFieldsCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            val emailState = validateFieldsUseCase.validateEmailCompose(_emailState.value.valueText)

            _emailState.update { emailState }
        }
    }

    fun getRules(context: Context): String {
        val listRules = context.resources?.getStringArray(R.array.rules_forget_pass)
        val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
        return stringBuilder
    }
}