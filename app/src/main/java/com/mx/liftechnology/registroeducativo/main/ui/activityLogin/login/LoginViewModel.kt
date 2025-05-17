package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.log
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.loginflowdomain.LoginUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.login.ModelLoginUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val loginUseCase: LoginUseCase,
    private val validateFieldsUseCase: ValidateFieldsLoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelLoginUiState())
    val uiState: StateFlow<ModelLoginUiState> = _uiState.asStateFlow()
    private val myValue: ModelLoginUiState
        get() = _uiState.value

    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(
                email = email.stringToModelStateOutFieldText()
            )
        }
    }

    fun onPassChanged(pass: String) {
        _uiState.update {
            it.copy(
                password = pass.stringToModelStateOutFieldText()
            )
        }
    }

    fun onRememberChanged(remember: Boolean) {
        _uiState.update {
            it.copy(
                isRemember = remember
            )
        }
    }

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateFieldsCompose() {
        _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
        val emailState = validateFieldsUseCase.validateEmailCompose(myValue.email.valueText)
        val passState = validateFieldsUseCase.validatePassCompose(myValue.password.valueText)

        _uiState.update {
            it.copy(
                email = emailState,
                password = passState
            )
        }

        if (!(emailState.isError || passState.isError)) loginCompose()
        else _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
    }


    /** Request to Login
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun loginCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = loginUseCase.invoke(
                myValue.email.valueText,
                myValue.password.valueText,
                myValue.isRemember
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
                    log(result.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }
            }
        }
    }

    fun modifyShowToast(show: Boolean) {
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