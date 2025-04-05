package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.loginflowdomain.RegisterUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.login.RegisterUserUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerUseCase: RegisterUseCase,
    private val validateFieldsUseCase: ValidateFieldsLoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUserUiState())
    val uiState: StateFlow<RegisterUserUiState> = _uiState.asStateFlow()
    private val myValue: RegisterUserUiState
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

    fun onRepeatPassChanged(repeatPass: String) {
        _uiState.update {
            it.copy(
                repeatPassword = ModelStateOutFieldText(
                    valueText = repeatPass,
                    isError = false,
                    errorMessage = ""
                )
            )
        }
    }

    fun onCodeChanged(code: String) {
        _uiState.update {
            it.copy(
                code = ModelStateOutFieldText(
                    valueText = code,
                    isError = false,
                    errorMessage = ""
                )
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
        val passState =
            validateFieldsUseCase.validatePassRegisterCompose(myValue.password.valueText)
        val repeatPassState = validateFieldsUseCase.validateRepeatPassCompose(
            myValue.password.valueText,
            myValue.repeatPassword.valueText
        )
        val codeState = validateFieldsUseCase.validateCodeCompose(myValue.code.valueText)

        _uiState.update {
            it.copy(
                email = emailState,
                password = passState,
                repeatPassword = repeatPassState,
                code = codeState
            )
        }

        if (!(emailState.isError || passState.isError || repeatPassState.isError || codeState.isError)) {
            registerCompose()
        } else {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getRules(context: Context): String {
        val listRules = context.resources?.getStringArray(R.array.rules_pass)
        val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
        return stringBuilder
    }

    /** Request to register
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun registerCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerUseCase.putRegister(
                    myValue.email.valueText,
                    myValue.password.valueText,
                    myValue.code.valueText
                )
            }.onSuccess { state ->
                if (state is SuccessState) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = false
                        )
                    }
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