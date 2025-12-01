package com.mx.liftechnology.registroeducativo.main.ui.student.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.student.DeleteStudentUseCase
import com.mx.liftechnology.domain.usecase.student.GetListStudentUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.StudentMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ListStudentUiData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ListStudentUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for the Student List screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ListStudentViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListStudentUseCase: GetListStudentUseCase,
    private val deleteStudentUseCase: DeleteStudentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListStudentUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ListStudentUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ListStudentUiData())
    /** The data state for the screen. */
    val dataState: StateFlow<ListStudentUiData> = _dataState.asStateFlow()

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
                    _dataState.update {
                        it.copy(
                            studentList = result.data,
                            studentListUI = StudentMapper.mapStudentListToCustomCard(result.data)
                        )
                    }
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(studentList = emptyList()) }
                }
            }
        }
    }

    /**
     * Gets a student by its ID.
     *
     * @param item The custom card model of the student to get.
     * @return The [ModelStudentDomain] object, or null if not found.
     */
    fun getStudent(item: ModelCustomCard): ModelStudentDomain? = _dataState.value.studentList?.find { it.studentId == item.id }

    fun deleteStudent(card: ModelCustomCard) {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                deleteStudentUseCase.invoke(card.id)
            }

            when(result) {
                is SuccessResult -> {
                    getListStudent()
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                }
            }
        }
    }
}