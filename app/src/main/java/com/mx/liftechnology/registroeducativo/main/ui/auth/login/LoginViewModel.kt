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
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelLoginInputsUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelLoginStateUI
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    private val _uiState = MutableStateFlow(ModelLoginStateUI())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ModelLoginStateUI> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(ModelLoginInputsUI())
    /** El estado que contiene los valores de los campos de entrada del usuario. */
    val inputState: StateFlow<ModelLoginInputsUI> = _inputState.asStateFlow()
    private val inputStateVM: ModelLoginInputsUI get() = _inputState.value

    /**
     * Se invoca cuando el valor del campo de email cambia.
     *
     * @param email El nuevo valor del email.
     */
    fun onEmailChanged(email: ModelStateOutFieldText) {
        viewModelScope.launch (dispatcherProvider.default){
            _inputState.update { it.copy(
                emailInputState = email
            ) }
        }
    }

    /**
     * Se invoca cuando el valor del campo de contraseña cambia.
     *
     * @param pass El nuevo valor de la contraseña.
     */
    fun onPassChanged(pass: ModelStateOutFieldText) {
        viewModelScope.launch (dispatcherProvider.default){
            _inputState.update { it.copy(
                passInputState = pass
            )}
        }
    }

    /**
     * Se invoca cuando el estado del checkbox "recordarme" cambia.
     *
     * @param remember El nuevo estado del checkbox.
     */
    fun onRememberChanged(remember: Boolean) {
        viewModelScope.launch (dispatcherProvider.default){
            _inputState.update { it.copy(
                    isRemember = remember
            ) }
        }
    }

    /**
     * Valida los campos de entrada y, si son válidos, procede con el inicio de sesión.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            val emailState = validateFieldsLoginFlowUseCase.validateEmailCompose(inputStateVM.emailInputState.valueText)
            val passState = validateFieldsLoginFlowUseCase.validatePassCompose(inputStateVM.passInputState.valueText)

            _inputState.update { it.copy(
                emailInputState = emailState,
                passInputState = passState
            )}

            if (!(emailState.isError || passState.isError)) loginCompose()
            else _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
        }
    }

    private suspend fun loginCompose() {
        when (val result = loginUseCase.invoke(
            email = inputStateVM.emailInputState.valueText,
            pass = inputStateVM.passInputState.valueText,
            remember = inputStateVM.isRemember
        )) {
            is SuccessResult -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.SUCCESS,
                        controlToast = ModelStateToastUI(
                            messageToast = R.string.toast_success_login,
                            showToast = true,
                            typeToast = ModelStateTypeToastUI.SUCCESS
                        )
                    )
                }
            }

            is ErrorResult -> {
                val msg = when(ErrorMapper.mapErrorToUI(result.error)){
                    UserError.SHOW_GENERIC_ERROR -> R.string.toast_error_generic
                    UserError.UNAUTHORIZED -> R.string.toast_error_login_user
                    else -> null
                }

                if(msg != null){
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR,
                            controlToast = ModelStateToastUI(
                                messageToast = msg,
                                showToast = true,
                                typeToast = ModelStateTypeToastUI.ERROR
                            )
                        )
                    }
                }else{
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.ERROR) }
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
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update {
                it.copy(
                    controlToast = ModelStateToastUI(
                        messageToast = it.controlToast.messageToast,
                        showToast = show,
                        typeToast = it.controlToast.typeToast
                    )
                )
            }
        }
    }
}