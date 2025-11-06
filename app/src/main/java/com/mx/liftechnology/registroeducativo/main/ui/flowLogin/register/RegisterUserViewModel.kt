package com.mx.liftechnology.registroeducativo.main.ui.flowLogin.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.data.util.UserError
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.register.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
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

/**
 * ViewModel for the User Registration screen.
 *
 * @property dispatcherProvider The provider for Coroutine dispatchers.
 * @property registerUserUseCase The use case for handling user registration.
 * @property validateFieldsUseCase The use case for validating input fields.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateFieldsUseCase: ValidateFieldsLoginFlowUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterUserStateUI())
    /** The UI state for the screen. */
    val uiState: StateFlow<ModelRegisterUserStateUI> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(ModelRegisterUserInputsUI())
    /** The state of the input fields. */
    val inputState: StateFlow<ModelRegisterUserInputsUI> = _inputState.asStateFlow()
    private val inputStateVM: ModelRegisterUserInputsUI get() = _inputState.value

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
     * Called when the repeated password input changes.
     *
     * @param repeatPass The new repeated password value.
     */
    fun onRepeatPassChanged(repeatPass: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                repeatPassInputState = repeatPass.stringToModelStateOutFieldText()
            )}
        }
    }

    /**
     * Called when the activation code input changes.
     *
     * @param code The new activation code value.
     */
    fun onCodeChanged(code: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _inputState.update { it.copy(
                codeInputState = code.stringToModelStateOutFieldText()
            )}
        }
    }

    /**
     * Validates the input fields and proceeds to registration if they are valid.
     */
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

    /**
     * Gets the rules for the password.
     *
     * @param context The application context.
     * @return A string containing the formatted rules.
     */
    fun getRules(context: Context): String {
        val listRules = context.resources?.getStringArray(R.array.rules_pass)
        val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
        return stringBuilder
    }

    private suspend fun registerCompose() {
        when (val result = registerUserUseCase.invoke(
            email = inputStateVM.emailInputState.valueText,
            pass = inputStateVM.passInputState.valueText,
            activationCode = inputStateVM.codeInputState.valueText
        )) {
            is SuccessResult -> {
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

            is ErrorResult -> {
                val msg = when(ErrorMapper.mapErrorToUI(result.error)){
                    UserError.SHOW_GENERIC_ERROR -> R.string.toast_error_generic
                    UserError.SHOW_SPECIFIC_ERROR -> R.string.toast_error_register_user
                    UserError.SHOW_INCOMPLETE_ERROR -> R.string.toast_error_register_user_active
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