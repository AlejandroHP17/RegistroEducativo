package com.mx.liftechnology.registroeducativo.main.ui.auth.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.auth.RegisterUserWithValidationUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.RegisterUserUiInputs
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.RegisterUserUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for the User Registration screen.
 *
 * @property dispatcherProvider The provider for Coroutine dispatchers.
 * @property registerUserUseCase The use case for handling user registration.
 * @property validateFieldsUseCase The use case for validating input fields.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerUserWithValidationUseCase: RegisterUserWithValidationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUserUiState())
    /** The UI state for the screen. */
    val uiState: StateFlow<RegisterUserUiState> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(RegisterUserUiInputs())
    /** The state of the input fields. */
    val inputState: StateFlow<RegisterUserUiInputs> = _inputState.asStateFlow()
    private val inputStateVM: RegisterUserUiInputs get() = _inputState.value

    /**
     * Called when the email input changes.
     *
     * @param email The new email value.
     */
    fun onEmailChanged(email: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update { it.copy(emailInputState = email) }
    }

    /**
     * Called when the password input changes.
     *
     * @param pass The new password value.
     */
    fun onPassChanged(pass: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update { it.copy(passInputState = pass) }
    }

    /**
     * Called when the repeated password input changes.
     *
     * @param repeatPass The new repeated password value.
     */
    fun onRepeatPassChanged(repeatPass: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update { it.copy(repeatPassInputState = repeatPass) }
    }

    /**
     * Called when the activation code input changes.
     *
     * @param code The new activation code value.
     */
    fun onCodeChanged(code: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update { it.copy(codeInputState = code) }
    }

    /**
     * Validates the input fields and proceeds to registration if they are valid.
     * La lógica de validación + operación está encapsulada en el Use Case.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            
            // El Use Case combina validación + operación
            val validationResult = withContext(dispatcherProvider.io) {
                registerUserWithValidationUseCase.invoke(
                    email = inputStateVM.emailInputState.valueText,
                    pass = inputStateVM.passInputState.valueText,
                    repeatPass = inputStateVM.repeatPassInputState.valueText,
                    activationCode = inputStateVM.codeInputState.valueText
                )
            }

            // Actualizar los estados de validación de los campos
            _inputState.update { 
                it.copy(
                    emailInputState = validationResult.validationStates["email"] ?: it.emailInputState,
                    passInputState = validationResult.validationStates["pass"] ?: it.passInputState,
                    repeatPassInputState = validationResult.validationStates["repeatPass"] ?: it.repeatPassInputState,
                    codeInputState = validationResult.validationStates["code"] ?: it.codeInputState
                )
            }

            // Si las validaciones pasaron, manejar el resultado de la operación
            if (validationResult.isValid && validationResult.operationResult != null) {
                when (val result = validationResult.operationResult) {
                    is SuccessResult -> {
                        _uiState.update {
                            it.copy(
                                uiState = ModelStateUIEnum.SUCCESS,
                                controlToast = ToastUiState(
                                    messageToast = R.string.toast_success_register_user,
                                    showToast = true,
                                    typeToast = ModelStateTypeToastUI.SUCCESS
                                )
                            )
                        }
                    }

                    is ErrorResult -> {
                        val userError = ErrorMapper.mapErrorToUI(result.error)
                        val messageRes = ErrorToMessageMapper.mapErrorToMessage(
                            error = userError,
                            context = ErrorToMessageMapper.ErrorContext.REGISTER_USER
                        )

                        _uiState.update {
                            it.copy(
                                uiState = ModelStateUIEnum.ERROR,
                                controlToast = messageRes?.let { msg ->
                                    ToastUiState(
                                        messageToast = msg,
                                        showToast = true,
                                        typeToast = ModelStateTypeToastUI.ERROR
                                    )
                                } ?: it.controlToast.copy(showToast = false)
                            )
                        }
                    }
                    else ->{}
                }
            } else {
                // Si hay errores de validación, solo actualizar el estado
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    /**
     * Gets the rules for the password.
     *
     * @param context The application context.
     * @return A string containing the formatted rules.
     */
    fun getRules(context: Context): String {
        val listRules = context.resources?.getStringArray(R.array.rules_pass)
        val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
        return stringBuilder
    }

    /**
     * Modifies the visibility of the toast message.
     *
     * @param show True to show the toast, false to hide it.
     */
    fun modifyShowToast(show: Boolean) {
        // Las actualizaciones de estado ya están en el hilo principal, no necesitan corrutina
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(showToast = show)
            )
        }
    }
}