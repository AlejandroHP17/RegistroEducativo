package com.mx.liftechnology.registroeducativo.main.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.auth.LoginWithValidationUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.events.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.LoginUiInputs
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.LoginUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla de Inicio de Sesión.
 * Gestiona el estado de la UI y la comunicación con los casos de uso.
 * La lógica de validación + operación está encapsulada en el Use Case.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property loginWithValidationUseCase El caso de uso que combina validación + operación de login.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val loginWithValidationUseCase: LoginWithValidationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(LoginUiInputs())
    /** El estado que contiene los valores de los campos de entrada del usuario. */
    val inputState: StateFlow<LoginUiInputs> = _inputState.asStateFlow()
    private val inputStateVM: LoginUiInputs get() = _inputState.value

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    /** Eventos de UI que deben ser manejados una sola vez (navegación, etc.) */
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    /**
     * Se invoca cuando el valor del campo de email cambia.
     *
     * @param email El nuevo valor del email.
     */
    fun onEmailChanged(email: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update { it.copy(emailInputState = email) }
    }

    /**
     * Se invoca cuando el valor del campo de contraseña cambia.
     *
     * @param pass El nuevo valor de la contraseña.
     */
    fun onPassChanged(pass: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update { it.copy(passInputState = pass) }
    }

    /**
     * Se invoca cuando el estado del checkbox "recordarme" cambia.
     *
     * @param remember El nuevo estado del checkbox.
     */
    fun onRememberChanged(remember: Boolean) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update { it.copy(isRemember = remember) }
    }

    /**
     * Valida los campos de entrada y, si son válidos, procede con el inicio de sesión.
     * La lógica de validación + operación está encapsulada en el Use Case.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            
            // El Use Case combina validación + operación
            val validationResult = withContext(dispatcherProvider.io) {
                loginWithValidationUseCase.invoke(
                    email = inputStateVM.emailInputState.valueText,
                    pass = inputStateVM.passInputState.valueText,
                    remember = inputStateVM.isRemember
                )
            }

            // Actualizar los estados de validación de los campos
            _inputState.update { 
                it.copy(
                    emailInputState = validationResult.validationStates["email"] ?: it.emailInputState,
                    passInputState = validationResult.validationStates["pass"] ?: it.passInputState
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
                                    messageToast = R.string.toast_success_login,
                                    showToast = true,
                                    typeToast = ModelStateTypeToastUI.SUCCESS
                                )
                            )
                        }
                        // Emitir evento de navegación en lugar de depender del estado
                        _uiEvent.emit(UiEvent.NavigateToHome)
                    }

                    is ErrorResult -> {
                        val userError = ErrorMapper.mapErrorToUI(result.error)
                        val messageRes = ErrorToMessageMapper.mapErrorToMessage(
                            error = userError,
                            context = ErrorToMessageMapper.ErrorContext.LOGIN
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
     * Modifica la visibilidad del mensaje toast.
     *
     * @param show `true` para mostrar el toast, `false` para ocultarlo.
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