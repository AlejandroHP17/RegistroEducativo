package com.mx.liftechnology.registroeducativo.main.ui.flowLogin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.login.LoginUseCase
import com.mx.liftechnology.registroeducativo.R
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
 * ViewModel for the Login screen.
 *
 * @property dispatcherProvider The provider for Coroutine dispatchers.
 * @property loginUseCase The use case for handling login.
 * @property validateFieldsLoginFlowUseCase The use case for validating input fields.
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
    /** The UI state for the screen. */
    val uiState: StateFlow<ModelLoginStateUI> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(ModelLoginInputsUI())
    /** The state of the input fields. */
    val inputState: StateFlow<ModelLoginInputsUI> = _inputState.asStateFlow()
    private val inputStateVM: ModelLoginInputsUI get() = _inputState.value

    /**
     * Called when the email input changes.
     *
     * @param email The new email value.
     */
    fun onEmailChanged(email: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                emailInputState = email.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Called when the password input changes.
     *
     * @param pass The new password value.
     */
    fun onPassChanged(pass: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                passInputState = pass.stringToModelStateOutFieldText()
            )}
        }
    }

    /**
     * Called when the "remember me" checkbox state changes.
     *
     * @param remember The new state of the checkbox.
     */
    fun onRememberChanged(remember: Boolean) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                    isRemember = remember
            ) }
        }
    }

    /**
     * Validates the input fields and proceeds to login if they are valid.
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
        when (loginUseCase.invoke(
            email = inputStateVM.emailInputState.valueText,
            pass = inputStateVM.passInputState.valueText,
            remember = inputStateVM.isRemember
        )) {
            is SuccessState -> {
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

            is ErrorUserState -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR,
                        controlToast = ModelStateToastUI(
                            messageToast = R.string.toast_error_login_user,
                            showToast = true,
                            typeToast = ModelStateTypeToastUI.ERROR
                        )
                    )
                }
            }

            else -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR
                    )
                }
            }
        }
    }

    /**
     * Modifies the visibility of the toast message.
     *
     * @param show True to show the toast, false to hide it.
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