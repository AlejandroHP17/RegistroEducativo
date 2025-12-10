package com.mx.liftechnology.registroeducativo.main.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.registroeducativo.main.model.student.StudentDomainPar
import com.mx.liftechnology.domain.usecase.share.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.share.GetListFormativeFieldUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.FormativeFieldMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.student.ListStudentUiData
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.ListFormativeFieldsUiData
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.toFormativeFieldDomainList
import com.mx.liftechnology.registroeducativo.main.model.menu.MenuUiState
import com.mx.liftechnology.registroeducativo.main.model.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.model.student.toStudentDomainList
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for the Calendar screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class CalendarViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListFormativeFieldUseCase: GetListFormativeFieldUseCase,
    private val getListStudentUseCase: GetListStudentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState())
    /** The UI state for the screen. */
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ListFormativeFieldsUiData())
    /** The data state for formativeField. */
    val dataState: StateFlow<ListFormativeFieldsUiData> = _dataState.asStateFlow()

    private val _dataState2 = MutableStateFlow(ListStudentUiData())
    /** The data state for students. */
    val dataState2: StateFlow<ListStudentUiData> = _dataState2.asStateFlow()

    /**
     * Gets the list of formativeFields.
     */
    fun getFormativeFields() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getListFormativeFieldUseCase.invoke()
            }

            when(result) {
                is SuccessResult -> {
                    val listFormativeField = result.data?.toFormativeFieldDomainList()
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(
                        formativeFieldsList = listFormativeField,
                        formativeFieldsListUI = FormativeFieldMapper.mapFormativeFieldListToCustomCard(listFormativeField),
                    ) }
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(formativeFieldsList = emptyList()) }
                }
            }
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
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.NOTHING)
                    }
                    val listStudent = result.data.toStudentDomainList()
                    _dataState2.update {
                        it.copy(
                            studentList = listStudent,
                            studentListUI = listStudent.convertModelCustomCard2()
                        )
                    }
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                }
            }
        }
    }

    private fun List<StudentDomainPar>?.convertModelCustomCard2(): List<ModelCustomCard> {
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