package com.mx.liftechnology.registroeducativo.main.ui.student.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.VoiceRecognitionManager
import com.mx.liftechnology.core.util.logDebug
import com.mx.liftechnology.core.util.logInfo
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.student.EditStudentWithValidationUseCase
import com.mx.liftechnology.domain.usecase.student.RegisterStudentWithValidationUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterStudentUiInputs
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterStudentUiState
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
import kotlinx.coroutines.withContext
import java.time.LocalDate

/**
 * ViewModel for the Student Registration screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterStudentViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerStudentWithValidationUseCase: RegisterStudentWithValidationUseCase,
    private val editStudentWithValidationUseCase: EditStudentWithValidationUseCase,
    private val validateVoiceStudentUseCase: ValidateVoiceStudentUseCase,
    private val voiceRecognitionManager: VoiceRecognitionManager
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterStudentUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<RegisterStudentUiState> = _uiState.asStateFlow()

    private val _uiInputs = MutableStateFlow(RegisterStudentUiInputs())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiInputs: StateFlow<RegisterStudentUiInputs> = _uiInputs.asStateFlow()
    private val myValue: RegisterStudentUiInputs
        get() = _uiInputs.value

    private var isListening = true

    init {
        // Observa los resultados del reconocimiento de voz usando StateFlow
        voiceRecognitionManager.resultsStateFlow
            .onEach { results ->
                logDebug(results.toString())
                validateDataRecord(results)
            }
            .launchIn(viewModelScope)
    }

    /**
     * Se invoca cuando el valor del campo de nombre cambia.
     *
     * @param name El nuevo valor del nombre.
     */
    fun onNameChanged(name: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _uiInputs.update { it.copy(name = name) }
    }

    /**
     * Se invoca cuando el valor del campo de apellido paterno cambia.
     *
     * @param lastName El nuevo valor del apellido paterno.
     */
    fun onLastNameChanged(lastName: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _uiInputs.update { it.copy(lastName = lastName) }
    }

    /**
     * Se invoca cuando el valor del campo de apellido materno cambia.
     *
     * @param secondLastName El nuevo valor del apellido materno.
     */
    fun onSecondLastNameChanged(secondLastName: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _uiInputs.update { it.copy(secondLastName = secondLastName) }
    }

    /**
     * Se invoca cuando el valor del campo de CURP cambia.
     *
     * @param curp El nuevo valor de la CURP.
     */
    fun onCurpChanged(curp: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        validateCurpWithBirthday(curp.valueText)
        _uiInputs.update { it.copy(curp = curp) }
    }

    private fun validateCurpWithBirthday(curp: String) {
        if (curp.length > 10) {
            val rawDate = curp.substring(4, 10)

            try {
                val year = rawDate.take(2).toInt()
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
        // Actualizaciones de estado simples no necesitan corrutinas
        _uiInputs.update { it.copy(birthday = birthday.stringToModelStateOutFieldText()) }
    }

    /**
     * Se invoca cuando el valor del campo de número de teléfono cambia.
     *
     * @param phoneNumber El nuevo valor del número de teléfono.
     */
    fun onPhoneNumberChanged(phoneNumber: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _uiInputs.update { it.copy(phoneNumber = phoneNumber) }
    }

    /**
     * Validates the input fields and proceeds to register the student if they are valid.
     */
    /**
     * Valida los campos de entrada y, si son válidos, procede con el registro o edición del estudiante.
     * La lógica de validación + operación está encapsulada en los Use Cases.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            // El Use Case combina validación + operación
            val validationResult = withContext(dispatcherProvider.io) {
                if (_uiState.value.isNew) {
                    registerStudentWithValidationUseCase.invoke(
                        name = myValue.name.valueText,
                        lastName = myValue.lastName.valueText,
                        secondLastName = myValue.secondLastName.valueText,
                        curp = myValue.curp.valueText,
                        birthday = myValue.birthday.valueText,
                        phoneNumber = myValue.phoneNumber.valueText
                    )
                } else {
                    editStudentWithValidationUseCase.invoke(
                        name = myValue.name.valueText,
                        lastName = myValue.lastName.valueText,
                        secondLastName = myValue.secondLastName.valueText,
                        curp = myValue.curp.valueText,
                        birthday = myValue.birthday.valueText,
                        phoneNumber = myValue.phoneNumber.valueText,
                        studentId = myValue.studentId
                    )
                }
            }

            // Actualizar los estados de validación de los campos
            _uiInputs.update {
                it.copy(
                    name = validationResult.validationStates["name"] ?: it.name,
                    lastName = validationResult.validationStates["lastName"] ?: it.lastName,
                    secondLastName = validationResult.validationStates["secondLastName"] ?: it.secondLastName,
                    curp = validationResult.validationStates["curp"] ?: it.curp,
                    birthday = validationResult.validationStates["birthday"] ?: it.birthday,
                    phoneNumber = validationResult.validationStates["phoneNumber"] ?: it.phoneNumber
                )
            }

            // Si las validaciones pasaron, manejar el resultado de la operación
            if (validationResult.isValid && validationResult.operationResult != null) {
                when (val result = validationResult.operationResult) {
                    is SuccessResult -> {
                        val messageRes = if (_uiState.value.isNew) {
                            R.string.toast_success_register_student
                        } else {
                            R.string.toast_success_edit_student
                        }
                        _uiState.update { it.copy(
                            uiState = ModelStateUIEnum.SUCCESS,
                            controlToast = ToastUiState(
                                messageToast = messageRes,
                                showToast = true,
                                typeToast = ModelStateTypeToastUI.SUCCESS
                            )
                        ) }
                    }

                    is ErrorResult -> {
                        val userError = ErrorMapper.mapErrorToUI(result.error)
                        val context = if (_uiState.value.isNew) {
                            ErrorToMessageMapper.ErrorContext.REGISTER_STUDENT
                        } else {
                            ErrorToMessageMapper.ErrorContext.EDIT_STUDENT
                        }
                        val messageRes = ErrorToMessageMapper.mapErrorToMessage(
                            error = userError,
                            context = context
                        )

                        _uiState.update {
                            it.copy(
                                uiState = ModelStateUIEnum.ERROR,
                                controlToast = messageRes?.let { msg ->
                                    ToastUiState(
                                        messageToast = msg,
                                        showToast = true,
                                        typeToast = ModelStateTypeToastUI.ERROR
                                    )
                                } ?: it.controlToast.copy(showToast = false)
                            )
                        }
                    }
                    else -> {}
                }
            } else {
                // Si hay errores de validación, solo actualizar el estado
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    /**
     * Gets the arguments for the student.
     *
     * @param student The student data.
     */
    fun getArguments(student: ModelStudentDomain) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _uiInputs.update {
            it.copy(
                name = student.name.stringToModelStateOutFieldText(),
                lastName = student.lastName.stringToModelStateOutFieldText(),
                secondLastName = student.secondLastName.stringToModelStateOutFieldText(),
                curp = student.curp.stringToModelStateOutFieldText(),
                birthday = student.birthday.stringToModelStateOutFieldText(),
                phoneNumber = student.phoneNumber.stringToModelStateOutFieldText(),
                studentId = student.studentId
            )
        }
        _uiState.update { it.copy(isNew = false) }
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
        viewModelScope.launch {
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
        viewModelScope.launch {
            // Las validaciones son operaciones síncronas simples
            val result = validateVoiceStudentUseCase.buildModelStudent(data.firstOrNull())
            result?.let { studentData ->
                logInfo(studentData.toString())
                _uiInputs.update { currentState ->
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
        // Las actualizaciones de estado ya están en el hilo principal, no necesitan corrutina
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(showToast = show)
            )
        }
    }
}