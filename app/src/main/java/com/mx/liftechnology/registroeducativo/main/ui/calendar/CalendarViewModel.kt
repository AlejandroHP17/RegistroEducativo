package com.mx.liftechnology.registroeducativo.main.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.registroeducativo.main.model.student.StudentDomainPar
import com.mx.liftechnology.domain.usecase.share.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.share.GetListFormativeFieldUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.FormativeFieldMapper
import com.mx.liftechnology.registroeducativo.main.mapper.StudentMapper
import com.mx.liftechnology.registroeducativo.main.model.calendar.CalendarUiState
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.student.ListStudentUiData
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.ListFormativeFieldsUiData
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.toFormativeFieldDomainList
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCard
import com.mx.liftechnology.registroeducativo.main.model.student.toStudentDomainList
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

/**
 * ViewModel para la pantalla de calendario.
 * 
 * Gestiona el estado de la UI y la obtención de listas de campos formativos y estudiantes
 * para mostrar en el calendario.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property getListFormativeFieldUseCase El caso de uso para obtener la lista de campos formativos.
 * @property getListStudentUseCase El caso de uso para obtener la lista de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class CalendarViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListFormativeFieldUseCase: GetListFormativeFieldUseCase,
    private val getListStudentUseCase: GetListStudentUseCase,
    private val preference : PreferenceUseCase
) : ViewModel() {

    private val _calendarUiState = MutableStateFlow(CalendarUiState())
    /** El estado de la UI para la pantalla. */
    val calendarUiState: StateFlow<CalendarUiState> = _calendarUiState.asStateFlow()

    private val _dataFormativeFieldState = MutableStateFlow(ListFormativeFieldsUiData())
    /** El estado de los datos para campos formativos. */
    val dataFormativeFieldState: StateFlow<ListFormativeFieldsUiData> = _dataFormativeFieldState.asStateFlow()

    private val _dataStudentState = MutableStateFlow(ListStudentUiData())
    /** El estado de los datos para estudiantes. */
    val dataStudentState: StateFlow<ListStudentUiData> = _dataStudentState.asStateFlow()

    /**
     * Obtiene la lista de campos formativos desde el servidor.
     */
    fun getFormativeFields() {
        viewModelScope.launch {
            _calendarUiState.update { it.copy(uiState = EnumUi.LOADING) }

            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getListFormativeFieldUseCase.invoke()
            }

            when(result) {
                is SuccessResult -> {
                    val listFormativeField = result.data?.toFormativeFieldDomainList()
                    _calendarUiState.update { it.copy(uiState = EnumUi.NOTHING) }
                    _dataFormativeFieldState.update { it.copy(
                        formativeFieldsList = listFormativeField,
                        formativeFieldsListUI = FormativeFieldMapper.mapFormativeFieldListToCustomCard(listFormativeField, false),
                    ) }
                }
                else -> {
                    _calendarUiState.update { it.copy(uiState = EnumUi.NOTHING) }
                    _dataFormativeFieldState.update { it.copy(formativeFieldsList = emptyList()) }
                }
            }
        }
    }

    /**
     * Obtiene un campo formativo por su ID.
     *
     * @param item El modelo de tarjeta personalizada del campo formativo a obtener.
     * @return El objeto [FormativeFieldDomainPar], o null si no se encuentra.
     */
    fun getFormativeField(item: CustomCard): FormativeFieldDomainPar? = _dataFormativeFieldState.value.formativeFieldsList?.find { it.formativeFieldId == item.id }


    /**
     * Obtiene la lista de estudiantes desde el servidor.
     */
    fun getListStudent() {
        viewModelScope.launch {
            _calendarUiState.update { it.copy(uiState = EnumUi.LOADING) }

            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getListStudentUseCase.invoke()
            }

            when(result) {
                is SuccessResult -> {
                    _calendarUiState.update {
                        it.copy(uiState = EnumUi.NOTHING)
                    }
                    val listStudent = result.data.toStudentDomainList()
                    _dataStudentState.update {
                        it.copy(
                            studentList = listStudent,
                            studentListUI = StudentMapper.mapStudentListToCustomCard(listStudent, false)
                        )
                    }
                }
                else -> {
                    _calendarUiState.update { it.copy(uiState = EnumUi.NOTHING) }
                }
            }
        }
    }

    private fun List<StudentDomainPar>?.convertModelCustomCard2(): List<CustomCard> {
        return this?.sortedWith(
            compareBy(
                { it.lastName ?: "" },
                { it.secondLastName ?: "" },
                { it.name ?: "" }
            ))
            ?.mapIndexed { index, student ->
                CustomCard(
                    id = student.studentId ?: 0,
                    numberList = (index + 1).toString(),
                    nameCard = "${student.lastName} ${student.secondLastName} ${student.name}".trim()
                )
            } ?: emptyList()
    }

    /**
     * Obtiene un estudiante por su ID.
     *
     * @param item El modelo de tarjeta personalizada del estudiante a obtener.
     * @return El objeto [StudentDomainPar], o null si no se encuentra.
     */
    fun getStudent(item: CustomCard): StudentDomainPar? = _dataStudentState.value.studentList?.find { it.studentId == item.id }

    fun rangeDates() = preference.getRangeDatesPartial()

    fun setRangeDate(date: LocalDate) {
        _calendarUiState.update {
            it.copy(date = date.toString())
        }
    }
}