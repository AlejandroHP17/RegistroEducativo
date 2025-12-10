package com.mx.liftechnology.registroeducativo.main.ui.student.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.voice.VoiceRecognitionManager
import com.mx.liftechnology.core.util.extension.logDebug
import com.mx.liftechnology.core.util.extension.logInfo
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.student.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.student.EditStudentWithValidationUseCase
import com.mx.liftechnology.domain.usecase.student.RegisterStudentWithValidationUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.student.RegisterStudentUiInputs
import com.mx.liftechnology.registroeducativo.main.model.student.RegisterStudentUiState
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

/**
 * ViewModel para la pantalla de registro y edición de estudiantes.
 * 
 * Gestiona el estado de la UI, la validación de campos y la comunicación con los casos de uso.
 * Incluye funcionalidad de reconocimiento de voz para llenar automáticamente los campos del formulario.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property registerStudentWithValidationUseCase El caso de uso para registrar un estudiante con validación.
 * @property editStudentWithValidationUseCase El caso de uso para editar un estudiante con validación.
 * @property validateVoiceStudentUseCase El caso de uso para validar y procesar datos de voz.
 * @property voiceRecognitionManager El manager para el reconocimiento de voz.
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

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    /** Eventos de UI que deben ser manejados una sola vez (navegación, etc.) */
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

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
                        
                        _uiEvent.emit(UiEvent.NavigateBack)
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
                
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    /**
     * Obtiene y establece los argumentos del estudiante para edición.
     *
     * @param student Los datos del estudiante a editar.
     */
    fun getArguments(student: StudentDomain) {
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
     * Se llama cuando el ViewModel es limpiado.
     */
    override fun onCleared() {
        super.onCleared()
        voiceRecognitionManager.release()
    }

    /**
     * Alterna el estado de escucha del reconocimiento de voz.
     * Inicia o detiene el reconocimiento de voz y actualiza el color del botón correspondiente.
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
     * Modifica la visibilidad del mensaje toast.
     *
     * @param show `true` para mostrar el toast, `false` para ocultarlo.
     */
    fun modifyShowToast(show: Boolean) {
        
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(showToast = show)
            )
        }
    }
}