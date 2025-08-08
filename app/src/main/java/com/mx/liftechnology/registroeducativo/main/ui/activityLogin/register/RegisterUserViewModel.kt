package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.loginflowdomain.RegisterUserUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.login.ModelRegisterUserUiState
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

    private val _uiState = MutableStateFlow(ModelRegisterUserUiState())
    val uiState: StateFlow<ModelRegisterUserUiState> = _uiState.asStateFlow()

    private val _emailState = MutableStateFlow(ModelStateOutFieldText())
    val emailState: StateFlow<ModelStateOutFieldText> = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(ModelStateOutFieldText())
    val passwordState: StateFlow<ModelStateOutFieldText> = _passwordState.asStateFlow()

    private val _repeatPasswordState = MutableStateFlow(ModelStateOutFieldText())
    val repeatPasswordState: StateFlow<ModelStateOutFieldText> = _repeatPasswordState.asStateFlow()

    private val _codeState = MutableStateFlow(ModelStateOutFieldText())
    val codeState: StateFlow<ModelStateOutFieldText> = _codeState.asStateFlow()

    fun onEmailChanged(email: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _emailState.update { email.stringToModelStateOutFieldText() }
        }
    }

    fun onPassChanged(pass: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _passwordState.update { pass.stringToModelStateOutFieldText() }
        }
    }

    fun onRepeatPassChanged(repeatPass: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _repeatPasswordState.update { repeatPass.stringToModelStateOutFieldText() }
        }
    }

    fun onCodeChanged(code: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _codeState.update { code.stringToModelStateOutFieldText() }
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
            val emailState = validateFieldsUseCase.validateEmailCompose(_emailState.value.valueText)
            val passState =
                validateFieldsUseCase.validatePassRegisterCompose(_passwordState.value.valueText)
            val repeatPassState = validateFieldsUseCase.validateRepeatPassCompose(
                _passwordState.value.valueText,
                _repeatPasswordState.value.valueText
            )
            val codeState = validateFieldsUseCase.validateCodeCompose(_codeState.value.valueText)

            _emailState.update { emailState }
            _passwordState.update { passState }
            _repeatPasswordState.update { repeatPassState }
            _codeState.update { codeState }

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
        when (val result = registerUserUseCase.invoke(
            _emailState.value.valueText,
            _passwordState.value.valueText,
            _codeState.value.valueText,
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
                logs(result.toString())
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