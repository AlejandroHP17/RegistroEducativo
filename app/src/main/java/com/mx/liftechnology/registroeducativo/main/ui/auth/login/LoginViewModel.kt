package com.mx.liftechnology.registroeducativo.main.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.data.util.UserError
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.auth.LoginUseCase
import com.mx.liftechnology.domain.usecase.auth.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.LoginUiInputs
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.LoginUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla de Inicio de Sesión.
 * Gestiona el estado de la UI, la lógica de validación de campos y la comunicación con los casos de uso.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property loginUseCase El caso de uso para manejar la lógica de inicio de sesión.
 * @property validateFieldsLoginFlowUseCase El caso de uso para validar los campos de entrada.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val loginUseCase: LoginUseCase,
    private val validateFieldsLoginFlowUseCase: ValidateFieldsLoginFlowUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(LoginUiInputs())
    /** El estado que contiene los valores de los campos de entrada del usuario. */
    val inputState: StateFlow<LoginUiInputs> = _inputState.asStateFlow()
    private val inputStateVM: LoginUiInputs get() = _inputState.value

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
     */
    fun validateFieldsCompose() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            
            // Las validaciones son operaciones síncronas simples
            val emailState = validateFieldsLoginFlowUseCase.validateEmailCompose(inputStateVM.emailInputState.valueText)
            val passState = validateFieldsLoginFlowUseCase.validatePassCompose(inputStateVM.passInputState.valueText)

            _inputState.update { it.copy(
                emailInputState = emailState,
                passInputState = passState
            )}

            if (!(emailState.isError || passState.isError)) {
                loginCompose()
            } else {
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    private suspend fun loginCompose() {
        // Las operaciones de red deben ejecutarse en el dispatcher de I/O
        val result = withContext(dispatcherProvider.io) {
            loginUseCase.invoke(
                email = inputStateVM.emailInputState.valueText,
                pass = inputStateVM.passInputState.valueText,
                remember = inputStateVM.isRemember
            )
        }

        when (result) {
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