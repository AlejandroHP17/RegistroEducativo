package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.registerassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterAssignmentUIState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCardStudent
import com.mx.liftechnology.registroeducativo.main.viewextensions.stringToModelStateOutFieldText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterAssignmentViewModel (
    private val getListStudentUseCase: GetListStudentUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterAssignmentUIState())
    val uiState: StateFlow<ModelRegisterAssignmentUIState> = _uiState.asStateFlow()

    fun updateSubject(subject: ModelFormatSubjectDomain?){
        _uiState.update { it.copy(
            subject =  subject
        ) }
    }

    fun onChangeName(name : String){
        _uiState.update { it.copy(
            nameJob =  name.stringToModelStateOutFieldText()
        ) }
    }

    fun onChangeDate(date : String){
        _uiState.update { it.copy(
            date =  date.stringToModelStateOutFieldText()
        ) }
    }

    fun onNameAssignmentChanged(partial: String) {
            _uiState.update {
                it.copy(
                    nameAssignment = partial.stringToModelStateOutFieldText()
                )
            }
    }

    fun onScoreChange(data: Pair<String, String>) {
            _uiState.update {currentState ->
                currentState.copy(
                    studentListUI = currentState.studentListUI.mapIndexed { _, score ->
                        if (score.id == data.first) {
                            score.copy(
                                score = data.second ,
                                isErrorScore = ModelStateOutFieldText(
                                    isError = false,
                                    errorMessage = ""
                                )
                            )
                        }else {
                            score
                        }
                    }
                )
            }
    }

    fun getListStudent() {
        _uiState.update { it.copy(isLoading = true) }
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
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
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
                    numberList = (index + 1).toString(), // Numeración comenzando en 1
                    studentName = "${student.lastName} ${student.secondLastName} ${student.name}".trim(),
                    score = "10.0"
                )
            } ?: emptyList()
    }

}