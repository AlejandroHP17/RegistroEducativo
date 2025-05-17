package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.log
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.loginflowdomain.RegisterUserUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.login.RegisterUserUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateFieldsUseCase: ValidateFieldsLoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUserUiState())
    val uiState: StateFlow<RegisterUserUiState> = _uiState.asStateFlow()
    private val myValue: RegisterUserUiState
        get() = _uiState.value

    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(email = email.stringToModelStateOutFieldText())
        }
    }

    fun onPassChanged(pass: String) {
        _uiState.update {
            it.copy(password = pass.stringToModelStateOutFieldText())
        }
    }

    fun onRepeatPassChanged(repeatPass: String) {
        _uiState.update {
            it.copy(repeatPassword = repeatPass.stringToModelStateOutFieldText())
        }
    }

    fun onCodeChanged(code: String) {
        _uiState.update {
            it.copy(code = code.stringToModelStateOutFieldText())
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
            _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
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
            when (val result = registerUserUseCase.invoke(
                myValue.email.valueText,
                myValue.password.valueText,
                myValue.code.valueText
            )) {
                is SuccessState -> {
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.SUCCESS,
                            controlToast = ModelStateToastUI(
                                messageToast = R.string.toast_success_register_user,
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
                                messageToast = R.string.toast_error_register_user,
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