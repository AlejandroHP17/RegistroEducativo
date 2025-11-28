package com.mx.liftechnology.registroeducativo.main.ui.evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.evaluation.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.evaluation.RegisterEvaluationWithValidationUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetWorkTypeByFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.formativeField.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.domain.usecase.student.GetListStudentUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.DomainToUIMapper.toCustomSpinnerList
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.mapper.EvaluationUIToDomainMapper.toModelCard
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterAssignmentUiData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterAssignmentUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCalendar
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCardStudent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for the Assignment Registration screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterEvaluationViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListStudentUseCase: GetListStudentUseCase,
    private val saveFormativeFieldIdSelectedUseCase: SaveFormativeFieldIdSelectedUseCase,
    private val getWorkTypeByFormativeFieldUseCase: GetWorkTypeByFormativeFieldUseCase,
    private val getDatesActivePartialUseCase: GetDatesActivePartialUseCase,
    private val registerEvaluationWithValidationUseCase: RegisterEvaluationWithValidationUseCase
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterAssignmentUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<RegisterAssignmentUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(RegisterAssignmentUiData())
    /** The data state for the screen. */
    val dataState: StateFlow<RegisterAssignmentUiData> = _dataState.asStateFlow()

    private val _dialogState = MutableStateFlow(ModelCustomCalendar())
    /** The state for the date picker dialog. */
    val dialogState: StateFlow<ModelCustomCalendar> = _dialogState.asStateFlow()

    /**
     * Updates the current subject.
     *
     * @param formativeField The new subject.
     */
    fun updateFormativeField(formativeField: ModelFormatFormativeFieldsDomain?) {
        viewModelScope.launch {
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            withContext(dispatcherProvider.io) {
                saveFormativeFieldIdSelectedUseCase.invoke(formativeField?.formativeFieldId)
            }
            getListWorkType()
            _uiState.update {
                it.copy(formativeField = formativeField)
            }
        }
    }

    private fun getListWorkType() {
        viewModelScope.launch {
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getWorkTypeByFormativeFieldUseCase.invoke()
            }

            when (result) {
                is SuccessResult -> {
                    val convertData = result.data.toCustomSpinnerList()
                    onNameAssignmentChanged(convertData?.first()!!)
                    _dataState.update {
                        it.copy(listOptions = convertData)
                    }
                }

                else -> {
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.ERROR)
                    }
                }
            }
        }
    }

    /**
     * Called when the name of the job changes.
     *
     * @param name The new name.
     */
    fun onNameChanged(name: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _dataState.update { it.copy(nameJob = name) }
    }

    /**
     * Called when the date changes.
     *
     * @param date The new date.
     */
    fun onDateChanged(date: String) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _dialogState.update {
            it.copy(date = date.stringToModelStateOutFieldText())
        }
    }

    /**
     * Called when the name of the assignment changes.
     *
     * @param assignment The new assignment.
     */
    fun onNameAssignmentChanged(assignment: ModelCustomSpinner) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _dataState.update {
            it.copy(
                nameAssignment = assignment.value.stringToModelStateOutFieldText(),
                options = assignment
            )
        }
    }

    /**
     * Called when a student's score changes.
     *
     * @param data A pair containing the student ID and the new score.
     */
    fun onScoreChange(data: Pair<String, String>) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _dataState.update { currentState ->
            currentState.copy(
                studentListUI = currentState.studentListUI.mapIndexed { _, score ->
                    if (score.id == data.first) {
                        score.copy(score = data.second.stringToModelStateOutFieldText())
                    } else {
                        score
                    }
                }
            )
        }
    }

    /**
     * Gets the list of students.
     */
    fun getListStudent() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getListStudentUseCase.invoke()
            }

            when(result) {
                is SuccessResult -> {
                    _dataState.update {
                        it.copy(
                            studentList = result.data,
                            studentListUI = result.data.convertModelCustomCard()
                        )
                    }
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.NOTHING)
                    }
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                }
            }
        }
    }

    private fun List<ModelStudentDomain>?.convertModelCustomCard(): List<ModelCustomCardStudent> {
        return this?.sortedWith(
            compareBy(
                { it.lastName ?: "" },
                { it.secondLastName ?: "" },
                { it.name ?: "" }
            ))
            ?.mapIndexed { index, student ->
                ModelCustomCardStudent(
                    id = student.studentId.toString(),
                    numberList = (index + 1).toString(),
                    studentName = "${student.lastName} ${student.secondLastName} ${student.name}".trim(),
                    score = "10.0".stringToModelStateOutFieldText()
                )
            } ?: emptyList()
    }

    /**
     * Validates the input fields and proceeds to register the assignment if they are valid.
     */
    /**
     * Validates the input fields and proceeds to register the assignment if they are valid.
     * La lógica de validación + operación está encapsulada en el Use Case.
     */
    fun validateFields() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            
            // El Use Case combina validación + operación
            val validationResult = withContext(dispatcherProvider.io) {
                registerEvaluationWithValidationUseCase.invoke(
                    nameJob = _dataState.value.nameJob.valueText,
                    nameAssignment = _dataState.value.nameAssignment.valueText,
                    date = _dialogState.value.date.valueText,
                    workTypeId = _dataState.value.options?.id,
                    studentListUI = _dataState.value.studentListUI.toModelCard()
                )
            }

            // Actualizar los estados de validación de los campos
            _dataState.update {
                it.copy(
                    nameJob = validationResult.validationStates["nameJob"] ?: it.nameJob,
                    nameAssignment = validationResult.validationStates["nameAssignment"] ?: it.nameAssignment
                )
            }
            _dialogState.update {
                it.copy(date = validationResult.validationStates["date"] ?: it.date)
            }

            // Si las validaciones pasaron, manejar el resultado de la operación
            if (validationResult.isValid && validationResult.operationResult != null) {
                when (val operationResult = validationResult.operationResult) {
                    is SuccessResult -> {
                        _uiState.update {
                            it.copy(
                                uiState = ModelStateUIEnum.SUCCESS,
                                controlToast = ToastUiState(
                                    messageToast = R.string.toast_success_register_assignment,
                                    showToast = true,
                                    typeToast = ModelStateTypeToastUI.SUCCESS
                                )
                            )
                        }
                    }

                    is ErrorResult -> {
                        val userError = ErrorMapper.mapErrorToUI(operationResult.error)
                        val messageRes = ErrorToMessageMapper.mapErrorToMessage(
                            error = userError,
                            context = ErrorToMessageMapper.ErrorContext.REGISTER_ASSIGNMENT
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
                    else ->{}
                }
            } else {
                // Si hay errores de validación, solo actualizar el estado
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    /**
     * Updates the date range for the active partial.
     */
    fun updateDates(){
        viewModelScope.launch {
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val dates = withContext(dispatcherProvider.io) {
                getDatesActivePartialUseCase.invoke()
            }
            _dialogState.update {
                it.copy(rangeDate = dates)
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