package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.loginflowdomain.LoginUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.login.LoginUiState
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

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private val myValue: LoginUiState
        get() = _uiState.value

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

    fun onPassChanged(pass: String) {
        _uiState.update {
            it.copy(
                password = ModelStateOutFieldText(
                    valueText = pass,
                    isError = false,
                    errorMessage = ""
                )
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
        _uiState.update { it.copy(isLoading = true) }
        val emailState = validateFieldsUseCase.validateEmailCompose(myValue.email.valueText)
        val passState = validateFieldsUseCase.validatePassCompose(myValue.password.valueText)

        _uiState.update {
            it.copy(
                email = emailState,
                password = passState
            )
        }

        if (!(emailState.isError || passState.isError)) loginCompose()
        else _uiState.update { it.copy(isLoading = false) }
    }


    /** Request to Login
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun loginCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                loginUseCase.login(
                    _uiState.value.email.valueText,
                    _uiState.value.password.valueText,
                    _uiState.value.isRemember
                )
            }.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = false
                    )
                }
            }
        }
    }
}
