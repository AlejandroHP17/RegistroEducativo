package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListStudentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListStudentUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListStudentViewModel(
    private val getListStudentUseCase: GetListStudentUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelListStudentUiState())
    val uiState: StateFlow<ModelListStudentUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelListStudentDataState())
    val dataState: StateFlow<ModelListStudentDataState> = _dataState.asStateFlow()

    fun getListStudent() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            when(val result = getListStudentUseCase.invoke()){
                is SuccessState -> {
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.NOTHING)
                    }
                    _dataState.update {
                        it.copy(
                            studentList = result.result,
                            studentListUI = result.result.convertModelCustomCard()
                        )
                    }
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                }
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

    fun getStudent(item: ModelCustomCard): ModelStudentDomain? = _dataState.value.studentList?.find { it.studentId == item.id }
}