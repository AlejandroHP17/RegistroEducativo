package com.mx.liftechnology.registroeducativo.main.ui.flowLogin.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.register.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelRegisterUserInputsUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.login.ModelRegisterUserStateUI
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateFieldsUseCase: ValidateFieldsLoginFlowUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterUserStateUI())
    val uiState: StateFlow<ModelRegisterUserStateUI> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(ModelRegisterUserInputsUI())
    val inputState: StateFlow<ModelRegisterUserInputsUI> = _inputState.asStateFlow()
    private val inputStateVM: ModelRegisterUserInputsUI get() = _inputState.value


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

    fun onRepeatPassChanged(repeatPass: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                repeatPassInputState = repeatPass.stringToModelStateOutFieldText()
            )}
        }
    }

    fun onCodeChanged(code: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                codeInputState = code.stringToModelStateOutFieldText()
            )}
        }
    }

    /** Check the inputs and post error or correct states directly on the editexts
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateFieldsCompose() {
        viewModelScope.launch (dispatcherProvider.io){
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            val emailState = validateFieldsUseCase.validateEmailCompose(inputStateVM.emailInputState.valueText)
            val passState =
                validateFieldsUseCase.validatePassRegisterCompose(inputStateVM.passInputState.valueText)
            val repeatPassState = validateFieldsUseCase.validateRepeatPassCompose(
                inputStateVM.passInputState.valueText,
                inputStateVM.repeatPassInputState.valueText
            )
            val codeState = validateFieldsUseCase.validateCodeCompose(inputStateVM.codeInputState.valueText)

            _inputState.update { it.copy(
                emailInputState = emailState,
                passInputState = passState,
                repeatPassInputState = repeatPassState,
                codeInputState = codeState
            )}

            if (!(emailState.isError || passState.isError || repeatPassState.isError || codeState.isError)) {
                registerCompose()
            } else {
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
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
    private suspend fun registerCompose() {
        when (registerUserUseCase.invoke(
            email = inputStateVM.emailInputState.valueText,
            pass = inputStateVM.passInputState.valueText,
            activatationCode = inputStateVM.codeInputState.valueText
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