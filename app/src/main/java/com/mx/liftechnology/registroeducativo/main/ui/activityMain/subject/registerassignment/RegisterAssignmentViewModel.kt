package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.registerassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.network.callapi.ResponseGetPercentSubjectId
import com.mx.liftechnology.core.util.log
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.SaveIdSubjectSelectedUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.GetListAssignmentPerSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.ValidateFieldsAssignmentUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterAssignmentUIState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCardStudent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterAssignmentViewModel(
    private val getListStudentUseCase: GetListStudentUseCase,
    private val saveIdSubjectSelectedUseCase: SaveIdSubjectSelectedUseCase,
    private val getListAssignmentPerSubjectUseCase: GetListAssignmentPerSubjectUseCase,
    private val validateFieldsAssignmentUseCase: ValidateFieldsAssignmentUseCase,
    private val registerAssignmentUseCase: RegisterAssignmentUseCase,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterAssignmentUIState())
    val uiState: StateFlow<ModelRegisterAssignmentUIState> = _uiState.asStateFlow()

    fun updateSubject(subject: ModelFormatSubjectDomain?) {
        viewModelScope.launch {
            saveIdSubjectSelectedUseCase.invoke(subject?.subjectId)
            getListAssessmentType()
        }
        _uiState.update {
            it.copy(
                subject = subject
            )
        }
    }

    private fun getListAssessmentType() {
        viewModelScope.launch {
            runCatching {
                getListAssignmentPerSubjectUseCase.invoke()
            }.onSuccess { state ->
                if (state is SuccessState) {
                    _uiState.update {
                        it.copy(
                            listOptions = change(state.result)
                        )
                    }

                } else {

                }
            }.onFailure {

            }
        }
    }

    private fun change(result: List<ResponseGetPercentSubjectId>?): List<String> {
        return result?.map { it.assignmentName ?: "" }?.toList() ?: listOf("")
    }

    fun onChangeName(name: String) {
        _uiState.update {
            it.copy(
                nameJob = name.stringToModelStateOutFieldText()
            )
        }
    }

    fun onChangeDate(date: String) {
        _uiState.update {
            it.copy(
                date = date.stringToModelStateOutFieldText()
            )
        }
    }

    fun onNameAssignmentChanged(partial: String) {
        _uiState.update {
            it.copy(
                nameAssignment = partial.stringToModelStateOutFieldText()
            )
        }
    }

    fun onScoreChange(data: Pair<String, String>) {
        _uiState.update { currentState ->
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

    fun getListStudent() {
        _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
        viewModelScope.launch {
            runCatching {
                getListStudentUseCase.getListStudent()
            }.onSuccess { state ->
                if (state is SuccessState) {
                    _uiState.update {
                        it.copy(
                            studentList = state.result,
                            studentListUI = state.result.convertModelCustomCard()
                        )
                    }
                }
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }.onFailure {
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
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
        _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
        val nameJobState =
            validateFieldsAssignmentUseCase.validateNameJob(_uiState.value.nameJob.valueText)
        val nameAssignmentState =
            validateFieldsAssignmentUseCase.validateNameAssignment(_uiState.value.nameAssignment.valueText)
        val dateState = validateFieldsAssignmentUseCase.validateDate(_uiState.value.date.valueText)

        _uiState.update {
            it.copy(
                nameJob = nameJobState,
                nameAssignment = nameAssignmentState,
                date = dateState
            )
        }

        if (!(nameJobState.isError || nameAssignmentState.isError || dateState.isError)) registerAssignment()
        else _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
    }

    private fun registerAssignment() {
        viewModelScope.launch {
            when (val result = registerAssignmentUseCase.invoke(
                _uiState.value.nameJob.valueText,
                _uiState.value.nameAssignment.valueText,
                _uiState.value.date.valueText
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
                    log(result.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }
            }
        }
    }

}