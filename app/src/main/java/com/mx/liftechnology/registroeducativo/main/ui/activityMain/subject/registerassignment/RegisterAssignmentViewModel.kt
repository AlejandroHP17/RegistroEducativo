package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.registerassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.SaveIdSubjectSelectedUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.GetListAssignmentPerSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.ValidateFieldsAssignmentUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterAssignmentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterAssignmentUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCalendar
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCardStudent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterAssignmentViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListStudentUseCase: GetListStudentUseCase,
    private val saveIdSubjectSelectedUseCase: SaveIdSubjectSelectedUseCase,
    private val getListAssignmentPerSubjectUseCase: GetListAssignmentPerSubjectUseCase,
    private val validateFieldsAssignmentUseCase: ValidateFieldsAssignmentUseCase,
    private val registerAssignmentUseCase: RegisterAssignmentUseCase,
    private val getDatesActivePartialUseCase: GetDatesActivePartialUseCase,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterAssignmentUiState())
    val uiState: StateFlow<ModelRegisterAssignmentUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelRegisterAssignmentDataState())
    val dataState: StateFlow<ModelRegisterAssignmentDataState> = _dataState.asStateFlow()

    private val _dialogState = MutableStateFlow(ModelCustomCalendar())
    val dialogState: StateFlow<ModelCustomCalendar> = _dialogState.asStateFlow()

    fun updateSubject(subject: ModelFormatSubjectDomain?) {
        viewModelScope.launch(dispatcherProvider.io) {
            saveIdSubjectSelectedUseCase.invoke(subject?.subjectId)
            getListAssessmentType()
            _uiState.update {
                it.copy(
                    subject = subject
                )
            }
        }
    }

    private fun getListAssessmentType() {
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getListAssignmentPerSubjectUseCase.invoke()) {
                is SuccessState -> {
                    _dataState.update {
                        it.copy(
                            listOptions = result.result
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
    }

    fun onChangeName(name: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _dataState.update {
                it.copy(
                    nameJob = name.stringToModelStateOutFieldText()
                )
            }
        }
    }

    fun onChangeDate(date: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _dialogState.update {
                it.copy(
                    date = date.stringToModelStateOutFieldText()
                )
            }
        }
    }

    fun onNameAssignmentChanged(partial: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            /*_dataState.update {
                it.copy(
                    assignment = it.assignment(
                        partial.stringToModelStateOutFieldText()
                    )
                )
            }*/
        }
    }

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

    fun getListStudent() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            when(val result = getListStudentUseCase.invoke()){
                is SuccessState -> {
                    _dataState.update {
                        it.copy(
                            studentList = result.result,
                            studentListUI = result.result.convertModelCustomCard()
                        )
                    }
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.NOTHING)
                    }
                }
                is ErrorUserState -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
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
                    id = student.studentId ?: "",
                    numberList = (index + 1).toString(),
                    studentName = "${student.lastName} ${student.secondLastName} ${student.name}".trim(),
                    score = "10.0".stringToModelStateOutFieldText()
                )
            } ?: emptyList()
    }

    fun validateFields() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            val nameJobState =
                validateFieldsAssignmentUseCase.validateNameJob(_dataState.value.nameJob.valueText)
            val nameAssignmentState =
                validateFieldsAssignmentUseCase.validateNameAssignment(_dataState.value.assignment.assignmentName.valueText)
            val dateState = validateFieldsAssignmentUseCase.validateDate(_dialogState.value.date.valueText)

            _dataState.update {
                it.copy(
                    nameJob = nameJobState,
                    //assignment = nameAssignmentState,
                )
            }
            _dialogState.update {
                it.copy( date = dateState )
            }

            if (!(nameJobState.isError || nameAssignmentState.isError || dateState.isError)) registerAssignment()
            else _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
        }
    }

    private suspend fun registerAssignment() {
        when (val result = registerAssignmentUseCase.invoke(
            nameJob = _dataState.value.nameJob.valueText,
            nameAssignment = 1,
            typeJob = 1,
            date = _dialogState.value.date.valueText
        )) {
            is SuccessState -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.SUCCESS,
                        controlToast = ModelStateToastUI(
                            messageToast = R.string.toast_success_login,
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
                            messageToast = R.string.toast_error_login_user,
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
}