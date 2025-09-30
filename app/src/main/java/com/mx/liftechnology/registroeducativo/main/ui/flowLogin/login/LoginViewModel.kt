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

class LoginViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val loginUseCase: LoginUseCase,
    private val validateFieldsLoginFlowUseCase: ValidateFieldsLoginFlowUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelLoginStateUI())
    val uiState: StateFlow<ModelLoginStateUI> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(ModelLoginInputsUI())
    val inputState: StateFlow<ModelLoginInputsUI> = _inputState.asStateFlow()
    private val inputStateVM: ModelLoginInputsUI get() = _inputState.value

    fun onEmailChanged(email: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                emailInputState = email.stringToModelStateOutFieldText()
            ) }
        }
    }

    fun onPassChanged(pass: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                passInputState = pass.stringToModelStateOutFieldText()
            )}
        }
    }

    fun onRememberChanged(remember: Boolean) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                    isRemember = remember
            ) }
        }
    }

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * */
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


    /** Request to Login
     * @author pelkidev
     * @since 1.0.0
     * */
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