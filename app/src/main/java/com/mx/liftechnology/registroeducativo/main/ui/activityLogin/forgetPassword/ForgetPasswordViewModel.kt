package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.login.LoginUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsUseCase: ValidateFieldsLoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(
                email = ModelStateOutFieldText(
                    valueText = email,
                    isError = false,
                    errorMessage = ""
                )
            )
        }
    }

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateFieldsCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            val emailState =
                validateFieldsUseCase.validateEmailCompose(_uiState.value.email.valueText)

            _uiState.update {
                it.copy(email = emailState)
            }
        }
    }

    fun getRules(context: Context): String {
        val listRules = context.resources?.getStringArray(R.array.rules_forget_pass)
        val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
        return stringBuilder
    }
}