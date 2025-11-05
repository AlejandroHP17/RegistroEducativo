package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.VoiceRecognitionManager
import com.mx.liftechnology.core.util.logInfo
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.RegisterOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterStudentStateUI
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel for the Student Registration screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterStudentViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsStudentUseCase: ValidateFieldsStudentUseCase,
    private val registerOneStudentUseCase: RegisterOneStudentUseCase,
    private val validateVoiceStudentUseCase: ValidateVoiceStudentUseCase,
    private val voiceRecognitionManager: VoiceRecognitionManager
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterStudentStateUI())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ModelRegisterStudentStateUI> = _uiState.asStateFlow()
    private val myValue: ModelRegisterStudentStateUI
        get() = _uiState.value

    private var isListening = true

    init {
        // Observa los resultados del reconocimiento de voz usando StateFlow
        voiceRecognitionManager.resultsStateFlow
            .onEach { results ->
                logInfo(results.toString())
                validateDataRecord(results)
            }
            .launchIn(viewModelScope)
    }

    /**
     * Se invoca cuando el valor del campo de nombre cambia.
     *
     * @param name El nuevo valor del nombre.
     */
    fun onNameChanged(name: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                name = name.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Se invoca cuando el valor del campo de apellido paterno cambia.
     *
     * @param lastName El nuevo valor del apellido paterno.
     */
    fun onLastNameChanged(lastName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                lastName = lastName.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Se invoca cuando el valor del campo de apellido materno cambia.
     *
     * @param secondLastName El nuevo valor del apellido materno.
     */
    fun onSecondLastNameChanged(secondLastName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                secondLastName = secondLastName.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Se invoca cuando el valor del campo de CURP cambia.
     *
     * @param curp El nuevo valor de la CURP.
     */
    fun onCurpChanged(curp: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            validateCurpWithBirthday(curp)
            _uiState.update { it.copy(
                curp = curp.stringToModelStateOutFieldText()
            ) }
        }
    }

    private fun validateCurpWithBirthday(curp: String) {
        if (curp.length > 10) {
            val rawDate = curp.substring(4, 10)

            return try {
                val year = rawDate.substring(0, 2).toInt()
                val month = rawDate.substring(2, 4).toInt()
                val day = rawDate.substring(4, 6).toInt()

                val fullYear = if (year <= 29) 2000 + year else 1900 + year

                val localDate = LocalDate.of(fullYear, month, day)
                onBirthdayChanged(localDate.toString())
            } catch (_: Exception) {
                //Nothing
            }
        }
    }

    /**
     * Se invoca cuando el valor del campo de fecha de nacimiento cambia.
     *
     * @param birthday El nuevo valor de la fecha de nacimiento.
     */
    fun onBirthdayChanged(birthday: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                birthday = birthday.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Se invoca cuando el valor del campo de número de teléfono cambia.
     *
     * @param phoneNumber El nuevo valor del número de teléfono.
     */
    fun onPhoneNumberChanged(phoneNumber: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                phoneNumber = phoneNumber.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Validates the input fields and proceeds to register the student if they are valid.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch (dispatcherProvider.io){
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            val nameState = validateFieldsStudentUseCase.validateName(_uiState.value.name.valueText)
            val lastNameState = validateFieldsStudentUseCase.validateLastName(_uiState.value.lastName.valueText)
            val secondLastNameState =
                validateFieldsStudentUseCase.validateSecondLastName(_uiState.value.secondLastName.valueText)
            val curpState = validateFieldsStudentUseCase.validateCurp(_uiState.value.curp.valueText)
            val birthdayState = validateFieldsStudentUseCase.validateBirthday(_uiState.value.birthday.valueText)
            val phoneNumberState = validateFieldsStudentUseCase.validatePhoneNumber(_uiState.value.phoneNumber.valueText)

            _uiState.update {
                it.copy(
                    name = nameState,
                    lastName = lastNameState,
                    secondLastName = secondLastNameState,
                    curp = curpState,
                    birthday = birthdayState,
                    phoneNumber = phoneNumberState
                )
            }

            if (!(nameState.isError || lastNameState.isError || secondLastNameState.isError || curpState.isError
                        || birthdayState.isError || phoneNumberState.isError
            )) {
                registerOneStudentCompose()
            }else{
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    private suspend fun registerOneStudentCompose() {
        when (val result =
            registerOneStudentUseCase.invoke(
                myValue.name.valueText,
                myValue.lastName.valueText,
                myValue.secondLastName.valueText,
                myValue.curp.valueText,
                myValue.birthday.valueText,
                myValue.phoneNumber.valueText
            )){
            is SuccessResult -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.SUCCESS,
                    controlToast = ModelStateToastUI(
                        messageToast = R.string.toast_success_register_student,
                        showToast = true,
                        typeToast = ModelStateTypeToastUI.SUCCESS
                    )
                ) }
            }
            is ErrorUserResult -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.ERROR,
                    controlToast = ModelStateToastUI(
                        messageToast = R.string.toast_error_register_student,
                        showToast = true,
                        typeToast = ModelStateTypeToastUI.ERROR
                    )
                ) }
            }
            else -> {
                logInfo(result.toString())
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.ERROR
                ) }
            }
        }
    }

    /**
     * Gets the arguments for the student.
     *
     * @param student The student data.
     */
    fun getArguments(student: ModelStudentDomain) {
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update {
                it.copy(
                    name = student.name.stringToModelStateOutFieldText(),
                    lastName = student.lastName.stringToModelStateOutFieldText(),
                    secondLastName = student.secondLastName.stringToModelStateOutFieldText(),
                    curp = student.curp.stringToModelStateOutFieldText(),
                    birthday = student.birthday.stringToModelStateOutFieldText(),
                    phoneNumber = student.phoneNumber.stringToModelStateOutFieldText(),
                )
            }
        }
    }

    /**
     * Called when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        voiceRecognitionManager.release()
    }

    /**
     * Toggles the voice recognition listening state.
     */
    fun change() {
        viewModelScope.launch (dispatcherProvider.main){
            if (isListening) {
                voiceRecognitionManager.startListening()
                isListening = false
                _uiState.update { it.copy(buttonColor = colorError) }
            } else {
                voiceRecognitionManager.stopListening()
                isListening = true
                _uiState.update { it.copy(buttonColor = colorSuccess) }
            }
        }
    }

    private fun validateDataRecord(data: List<String>) {
        viewModelScope.launch (dispatcherProvider.main){
            val result = validateVoiceStudentUseCase.buildModelStudent(data.firstOrNull())
            result?.let { studentData ->
                logInfo(studentData.toString())
                _uiState.update { currentState ->
                    currentState.copy(
                        name = studentData[ModelVoiceConstants.NAME].stringToModelStateOutFieldText(),
                        lastName = studentData[ModelVoiceConstants.LAST_NAME].stringToModelStateOutFieldText(),
                        secondLastName = studentData[ModelVoiceConstants.SECOND_LAST_NAME].stringToModelStateOutFieldText(),
                        curp = studentData[ModelVoiceConstants.CURP].stringToModelStateOutFieldText(),
                        birthday = studentData[ModelVoiceConstants.BIRTHDAY].stringToModelStateOutFieldText(),
                        phoneNumber = studentData[ModelVoiceConstants.PHONE_NUMBER].stringToModelStateOutFieldText()
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