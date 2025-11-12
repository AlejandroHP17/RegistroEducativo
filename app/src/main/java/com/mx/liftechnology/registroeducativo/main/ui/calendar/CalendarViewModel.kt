package com.mx.liftechnology.registroeducativo.main.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.student.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetListSubjectUseCase
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListStudentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListFormativeFieldsDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelMenuStateUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Calendar screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class CalendarViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListSubjectUseCase: GetListSubjectUseCase,
    private val getListStudentUseCase: GetListStudentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelMenuStateUI())
    /** The UI state for the screen. */
    val uiState: StateFlow<ModelMenuStateUI> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelListFormativeFieldsDataState())
    /** The data state for subjects. */
    val dataState: StateFlow<ModelListFormativeFieldsDataState> = _dataState.asStateFlow()

    private val _dataState2 = MutableStateFlow(ModelListStudentDataState())
    /** The data state for students. */
    val dataState2: StateFlow<ModelListStudentDataState> = _dataState2.asStateFlow()

    /**
     * Gets the list of subjects.
     */
    /*fun getSubject() {
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            when(val result = getListSubjectUseCase.invoke()){
                is SuccessResult -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(
                        subjectList = result.data,
                        subjectListUI = result.data.convertModelCustomCard(),
                    ) }
                }
                else -> {
                    logInfo(result.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR,
                        )
                    }
                }
            }
        }
    }

    private fun List<ModelFormatSubjectDomain>?.convertModelCustomCard():List<ModelCustomCard>{
        return this?.map {
            ModelCustomCard(
                id = it.subjectId.toString(),
                numberList = "",
                nameCard = "${it.name}"
            )
        }?: emptyList()
    }*/

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
                    _dataState2.update {
                        it.copy(
                            studentList = result.data,
                            studentListUI = result.data.convertModelCustomCard2()
                        )
                    }
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                }
            }
        }
    }

    private fun List<ModelStudentDomain>?.convertModelCustomCard2(): List<ModelCustomCard> {
        return this?.sortedWith(
            compareBy(
                { it.lastName ?: "" },
                { it.secondLastName ?: "" },
                { it.name ?: "" }
            ))
            ?.mapIndexed { index, student ->
                ModelCustomCard(
                    id = student.studentId ?: 0,
                    numberList = (index + 1).toString(),
                    nameCard = "${student.lastName} ${student.secondLastName} ${student.name}".trim()
                )
            } ?: emptyList()
    }
}