package com.mx.liftechnology.registroeducativo.main.ui.auth.forgetPassword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.auth.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelLoginStateUI
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the "Forget Password" screen.
 *
 * @property dispatcherProvider The provider for Coroutine dispatchers.
 * @property validateFieldsUseCase The use case for validating input fields.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ForgetPasswordViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsUseCase: ValidateFieldsLoginFlowUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelLoginStateUI())
    /** The UI state for the screen. */
    val uiState: StateFlow<ModelLoginStateUI> = _uiState.asStateFlow()

    private val _emailState = MutableStateFlow(ModelStateOutFieldText())
    /** The state of the email input field. */
    val emailState: StateFlow<ModelStateOutFieldText> = _emailState.asStateFlow()

    /**
     * Called when the email input changes.
     *
     * @param email The new email value.
     */
    fun onEmailChanged(email: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _emailState.update { email.stringToModelStateOutFieldText() }
        }
    }

    /**
     * Validates the input fields and updates their states.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            val emailState = validateFieldsUseCase.validateEmailCompose(_emailState.value.valueText)

            _emailState.update { emailState }
        }
    }

    /**
     * Gets the rules for forgetting a password.
     *
     * @param context The application context.
     * @return A string containing the formatted rules.
     */
    fun getRules(context: Context): String {
        val listRules = context.resources?.getStringArray(R.array.rules_forget_pass)
        val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
        return stringBuilder
    }
}