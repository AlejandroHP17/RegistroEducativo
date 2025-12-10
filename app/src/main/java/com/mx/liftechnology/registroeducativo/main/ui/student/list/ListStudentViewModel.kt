package com.mx.liftechnology.registroeducativo.main.ui.student.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.registroeducativo.main.model.student.StudentDomainPar
import com.mx.liftechnology.domain.usecase.student.DeleteStudentUseCase
import com.mx.liftechnology.domain.usecase.share.GetListStudentUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.StudentMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.student.ListStudentUiData
import com.mx.liftechnology.registroeducativo.main.model.student.ListStudentUiState
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCard
import com.mx.liftechnology.registroeducativo.main.model.student.toStudentDomainList
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla de lista de estudiantes.
 * 
 * Gestiona el estado de la UI, la obtención de la lista de estudiantes y la eliminación de estudiantes.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property getListStudentUseCase El caso de uso para obtener la lista de estudiantes.
 * @property deleteStudentUseCase El caso de uso para eliminar un estudiante.
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
    /** El estado de los datos de la pantalla. */
    val dataState: StateFlow<ListStudentUiData> = _dataState.asStateFlow()

    /**
     * Obtiene la lista de estudiantes desde el servidor.
     */
    fun getListStudent() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = EnumUi.LOADING) }

            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getListStudentUseCase.invoke()
            }

            when(result) {
                is SuccessResult -> {
                    val listFormativeField = result.data.toStudentDomainList()
                    _uiState.update {
                        it.copy(uiState = EnumUi.NOTHING)
                    }
                    _dataState.update {
                        it.copy(
                            studentList = listFormativeField,
                            studentListUI = StudentMapper.mapStudentListToCustomCard(listFormativeField)
                        )
                    }
                }
                else -> {
                    _uiState.update { it.copy(uiState = EnumUi.NOTHING) }
                    _dataState.update { it.copy(studentList = emptyList()) }
                }
            }
        }
    }

    /**
     * Obtiene un estudiante por su ID.
     *
     * @param item El modelo de tarjeta personalizada del estudiante a obtener.
     * @return El objeto [StudentDomainPar], o null si no se encuentra.
     */
    fun getStudent(item: CustomCard): StudentDomainPar? = _dataState.value.studentList?.find { it.studentId == item.id }

    /**
     * Elimina un estudiante de la lista.
     *
     * @param card El modelo de tarjeta personalizada del estudiante a eliminar.
     */
    fun deleteStudent(card: CustomCard) {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = EnumUi.LOADING) }

            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                deleteStudentUseCase.invoke(card.id)
            }

            when(result) {
                is SuccessResult -> {
                    getListStudent()
                }
                else -> {
                    _uiState.update { it.copy(uiState = EnumUi.NOTHING) }
                }
            }
        }
    }
}