package com.mx.liftechnology.registroeducativo.main.ui.evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.evaluation.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetWorkTypeByFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.formativeField.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.domain.usecase.student.GetListStudentUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.main.mapper.DomainToUIMapper.toCustomSpinnerList
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterAssignmentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterAssignmentStateUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCalendar
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCardStudent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
    private val validateFieldsEvaluationUseCase: ValidateFieldsEvaluationUseCase,
    private val getDatesActivePartialUseCase: GetDatesActivePartialUseCase,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterAssignmentStateUI())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ModelRegisterAssignmentStateUI> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelRegisterAssignmentDataState())
    /** The data state for the screen. */
    val dataState: StateFlow<ModelRegisterAssignmentDataState> = _dataState.asStateFlow()

    private val _dialogState = MutableStateFlow(ModelCustomCalendar())
    /** The state for the date picker dialog. */
    val dialogState: StateFlow<ModelCustomCalendar> = _dialogState.asStateFlow()

    /**
     * Updates the current subject.
     *
     * @param formativeField The new subject.
     */
    fun updateFormativeField(formativeField: ModelFormatFormativeFieldsDomain?) {
        viewModelScope.launch(dispatcherProvider.io) {
            saveFormativeFieldIdSelectedUseCase.invoke(formativeField?.formativeFieldId)
            getListWorkType()
            _uiState.update {
                it.copy(
                    formativeField = formativeField
                )
            }
        }
    }

    private fun getListWorkType() {
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getWorkTypeByFormativeFieldUseCase.invoke()) {
                is SuccessResult -> {
                    val convertData = result.data.toCustomSpinnerList()
                    onNameAssignmentChanged(convertData?.first()!!)
                    _dataState.update {
                        it.copy(
                            listOptions = convertData
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
    }

    /**
     * Called when the name of the job changes.
     *
     * @param name The new name.
     */
    fun onNameChanged(name: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _dataState.update {
                it.copy(
                    nameJob = name.stringToModelStateOutFieldText()
                )
            }
        }
    }

    /**
     * Called when the date changes.
     *
     * @param date The new date.
     */
    fun onDateChanged(date: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _dialogState.update {
                it.copy(
                    date = date.stringToModelStateOutFieldText()
                )
            }
        }
    }

    /**
     * Called when the name of the assignment changes.
     *
     * @param assignment The new assignment.
     */
    fun onNameAssignmentChanged(assignment: ModelCustomSpinner) {
        viewModelScope.launch(dispatcherProvider.io) {
            _dataState.update {
                it.copy(
                    nameAssignment = assignment.value.stringToModelStateOutFieldText(),
                    options = assignment
                )
            }
        }
    }

    /**
     * Called when a student's score changes.
     *
     * @param data A pair containing the student ID and the new score.
     */
    fun onScoreChange(data: Pair<String, String>) {
        viewModelScope.launch(dispatcherProvider.io) {
            _dataState.update { currentState ->
                currentState.copy(
                    studentListUI = currentState.studentListUI.mapIndexed { _, score ->
                        if (score.id == data.first) {
                            score.copy(
                                score = data.second.stringToModelStateOutFieldText(),
                            )
                        } else {
                            score
                        }
                    }
                )
            }
        }
    }

    /**
     * Gets the list of students.
     */
    fun getListStudent() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            when(val result = getListStudentUseCase.invoke()){
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
    fun validateFields() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            val nameJobState =
                validateFieldsEvaluationUseCase.validateNameJob(_dataState.value.nameJob.valueText)
            val nameAssignmentState =
                validateFieldsEvaluationUseCase.validateNameAssignment(_dataState.value.nameAssignment.valueText)
            val dateState = validateFieldsEvaluationUseCase.validateDate(_dialogState.value.date.valueText)

            _dataState.update {
                it.copy(
                    nameJob = nameJobState,
                    nameAssignment = nameAssignmentState,
                )
            }
            _dialogState.update {
                it.copy( date = dateState )
            }

            /*if (!(nameJobState.isError || nameAssignmentState.isError || dateState.isError)) registerAssignment()
            else _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }*/
        }
    }

    /*private suspend fun registerAssignment() {
        when (val result = registerAssignmentUseCase.invoke(
            nameJob = _dataState.value.nameJob.valueText,
            typeJob = _dataState.value.options?.id!!,
            date = _dialogState.value.date.valueText,
            studentListUI = _dataState.value.studentListUI.toCredentialStudent()
        )) {
            is SuccessResult -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.SUCCESS,
                        controlToast = ModelStateToastUI(
                            messageToast = R.string.toast_success_register_assignment,
                            showToast = true,
                            typeToast = ModelStateTypeToastUI.SUCCESS
                        )
                    )
                }
            }

            is ErrorUserResult -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR,
                        controlToast = ModelStateToastUI(
                            messageToast = R.string.toast_error_register_assignment,
                            showToast = true,
                            typeToast = ModelStateTypeToastUI.ERROR
                        )
                    )
                }
            }

            else -> {
                logInfo(result.toString())
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR
                    )
                }
            }
        }
    }*/

    /**
     * Updates the date range for the active partial.
     */
    fun updateDates(){
        viewModelScope.launch(dispatcherProvider.io) {
            val dates = getDatesActivePartialUseCase.invoke()
            _dialogState.update {
                it.copy(
                    rangeDate = dates
                )
            }
        }
    }


    /*private fun List<ModelCustomCardStudent>.toCredentialStudent()  : List<RequestStudentJobs>{
        return this.map { student ->
            RequestStudentJobs(
                studentSchoolCycleGroupId = student.id.toIntOrNull() ?: 0,
                qualification = student.score.valueText.toFloatOrNull() ?: 0f,
                comment = "Asistió"
            )
        }
    }*/

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