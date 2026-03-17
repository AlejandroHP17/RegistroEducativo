package com.mx.liftechnology.registroeducativo.main.ui.auth.forgetPassword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.share.ValidateAuthFieldsUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.auth.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de recuperación de contraseña.
 * 
 * Gestiona el estado de la UI y la validación del campo de email para recuperar la contraseña.
 *
 * @property validateFieldsUseCase El caso de uso para validar los campos de entrada.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ForgetPasswordViewModel(
    private val validateFieldsUseCase: ValidateAuthFieldsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    /** El estado de la UI para la pantalla. */
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _emailState = MutableStateFlow(ModelStateOutFieldText())

    /** El estado del campo de entrada de correo electrónico. */
    val emailState: StateFlow<ModelStateOutFieldText> = _emailState.asStateFlow()

    /**
     * Se llama cuando cambia el campo de entrada de correo electrónico.
     *
     * @param email El nuevo valor del correo electrónico.
     */
    fun onEmailChanged(email: ModelStateOutFieldText) {
        _emailState.update { email }
    }

    /**
     * Valida los campos de entrada y actualiza sus estados.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch {
            val emailState = validateFieldsUseCase.validateEmailCompose(_emailState.value.valueText)
            _emailState.update { emailState }
        }
    }

    /**
     * Obtiene las reglas para recuperar la contraseña.
     *
     * @param context El contexto de la aplicación.
     * @return Un string que contiene las reglas formateadas.
     */
    fun getRules(context: Context): String {
        val listRules = context.resources?.getStringArray(R.array.rules_forget_pass)
        val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
        return stringBuilder
    }
}