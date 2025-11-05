package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListStudentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListStudentStateUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Student List screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ListStudentViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListStudentUseCase: GetListStudentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelListStudentStateUI())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ModelListStudentStateUI> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelListStudentDataState())
    /** The data state for the screen. */
    val dataState: StateFlow<ModelListStudentDataState> = _dataState.asStateFlow()

    /**
     * Gets the list of students.
     */
    fun getListStudent() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            when(val result = getListStudentUseCase.invoke()){
                is SuccessResult -> {
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
                    numberList = (index + 1).toString(),
                    nameCard = "${student.lastName} ${student.secondLastName} ${student.name}".trim()
                )
            } ?: emptyList()
    }

    /**
     * Gets a student by its ID.
     *
     * @param item The custom card model of the student to get.
     * @return The [ModelStudentDomain] object, or null if not found.
     */
    fun getStudent(item: ModelCustomCard): ModelStudentDomain? = _dataState.value.studentList?.find { it.studentId == item.id }
}