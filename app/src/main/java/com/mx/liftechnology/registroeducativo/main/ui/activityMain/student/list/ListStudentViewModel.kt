package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelListStudentUIState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListStudentViewModel(
    private val getListStudentUseCase: GetListStudentUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelListStudentUIState())
    val uiState: StateFlow<ModelListStudentUIState> = _uiState.asStateFlow()

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

    private fun List<ModelStudentDomain>?.convertModelCustomCard(): List<ModelCustomCard> {
        return this?.sortedWith(
            compareBy(
            { it.lastName ?: "" },
            { it.secondLastName ?: "" },
            { it.name ?: "" }
        ))
            ?.mapIndexed { index, student ->
                ModelCustomCard(
                    id = student.studentId ?: "",
                    numberList = (index + 1).toString(), // Numeración comenzando en 1
                    nameCard = "${student.lastName} ${student.secondLastName} ${student.name}".trim()
                )
            } ?: emptyList()
    }

    fun getStudent(item: ModelCustomCard): ModelStudentDomain? {
        return _uiState.value.studentList?.find { it.studentId == item.id }
    }
}