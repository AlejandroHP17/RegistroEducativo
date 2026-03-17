package com.mx.liftechnology.registroeducativo.main.ui.auth.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.auth.RegisterUserWithValidationUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.TypeToastUi
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.auth.RegisterUserUiInputs
import com.mx.liftechnology.registroeducativo.main.model.auth.RegisterUserUiState
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
 * ViewModel para la pantalla de registro de usuario.
 *
 * @property dispatcherProvider El proveedor de dispatchers de Corrutinas.
 * @property RegisterUserWithValidationUseCase El caso de uso para manejar el registro de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerUserWithValidationUseCase: RegisterUserWithValidationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUserUiState())
    /** El estado de la UI para la pantalla. */
    val uiState: StateFlow<RegisterUserUiState> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(RegisterUserUiInputs())
    /** El estado de los campos de entrada. */
    val inputState: StateFlow<RegisterUserUiInputs> = _inputState.asStateFlow()
    private val inputStateVM: RegisterUserUiInputs get() = _inputState.value

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    /** Eventos de UI que deben ser manejados una sola vez (navegación, etc.) */
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    /**
     * Se llama cuando cambia la entrada de email.
     *
     * @param email El nuevo valor del email.
     */
    fun onEmailChanged(email: ModelStateOutFieldText) {
        _inputState.update { it.copy(emailInputState = email) }
    }

    /**
     * Se llama cuando cambia la entrada de contraseña.
     *
     * @param pass El nuevo valor de la contraseña.
     */
    fun onPassChanged(pass: ModelStateOutFieldText) {
        _inputState.update { it.copy(passInputState = pass) }
    }

    /**
     * Se llama cuando cambia la entrada de contraseña repetida.
     *
     * @param repeatPass El nuevo valor de la contraseña repetida.
     */
    fun onRepeatPassChanged(repeatPass: ModelStateOutFieldText) {
        _inputState.update { it.copy(repeatPassInputState = repeatPass) }
    }

    /**
     * Se llama cuando cambia la entrada del código de activación.
     *
     * @param code El nuevo valor del código de activación.
     */
    fun onCodeChanged(code: ModelStateOutFieldText) {
        _inputState.update { it.copy(codeInputState = code) }
    }

    /**
     * Valida los campos de entrada y, si son válidos, procede con el registro de usuario.
     * La lógica de validación + operación está encapsulada en el Use Case.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = EnumUi.LOADING) }
            
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
                                uiState = EnumUi.SUCCESS,
                                controlToast = ToastUiState(
                                    messageToast = R.string.toast_success_register_user,
                                    showToast = true,
                                    typeToast = TypeToastUi.SUCCESS
                                )
                            )
                        }
                        
                        _uiEvent.emit(UiEvent.NavigateBack)
                    }

                    is ErrorResult -> {
                        val userError = ErrorMapper.mapErrorToUI(result.error)
                        val messageRes = ErrorToMessageMapper.mapErrorToMessage(
                            error = userError,
                            context = ErrorToMessageMapper.ErrorContext.REGISTER_USER
                        )

                        _uiState.update {
                            it.copy(
                                uiState = EnumUi.ERROR,
                                controlToast = messageRes?.let { msg ->
                                    ToastUiState(
                                        messageToast = msg,
                                        showToast = true,
                                        typeToast = TypeToastUi.ERROR
                                    )
                                } ?: it.controlToast.copy(showToast = false)
                            )
                        }
                    }
                    else ->{}
                }
            } else {
                
                _uiState.update { it.copy(uiState = EnumUi.NOTHING) }
            }
        }
    }

    /**
     * Obtiene las reglas para la contraseña.
     *
     * @param context El contexto de la aplicación.
     * @return Un string que contiene las reglas formateadas.
     */
    fun getRules(context: Context): String {
        val listRules = context.resources?.getStringArray(R.array.rules_pass)
        val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
        return stringBuilder
    }

    /**
     * Modifica la visibilidad del mensaje toast.
     *
     * @param show True para mostrar el toast, false para ocultarlo.
     */
    fun modifyShowToast(show: Boolean) {
        
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(showToast = show)
            )
        }
    }
}