package com.mx.liftechnology.registroeducativo.main.ui.workType.wotyByStudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.workType.GetListEvaluationsStudentUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListWotyFofiUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.FormativeFieldMapper.toComplexCardUI
import com.mx.liftechnology.registroeducativo.main.model.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.share.ModelSubComplexCard
import com.mx.liftechnology.registroeducativo.main.model.share.ModelSubSubComplexCard
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyUiData
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla de asignaciones por estudiante.
 * 
 * Gestiona el estado de la UI y la obtención de evaluaciones agrupadas por campo formativo
 * y tipo de trabajo para un estudiante específico.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property getListWotyFofiUseCase El caso de uso para obtener la lista de tipos de trabajo por campo formativo.
 * @property getListEvaluationsStudentUseCase El caso de uso para obtener las evaluaciones de un estudiante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class WotyByStudentViewModel (
    private val dispatcherProvider: DispatcherProvider,
    private val getListWotyFofiUseCase: GetListWotyFofiUseCase,
    private val getListEvaluationsStudentUseCase: GetListEvaluationsStudentUseCase

    ): ViewModel() {
    private val _uiState = MutableStateFlow(WotyUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<WotyUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(WotyUiData())
    /** El estado de datos de la pantalla. */
    val dataState: StateFlow<WotyUiData> = _dataState.asStateFlow()

    /**
     * Actualiza el estudiante actual.
     *
     * @param student El nuevo estudiante.
     */
    fun updateStudent(student: StudentDomain?) {
        _uiState.update { it.copy(student =  student) }
    }

    /**
     * Actualiza la fecha seleccionada.
     *
     * @param date La fecha seleccionada.
     */
    fun updateDate(date: String?){
        _dataState.update { it.copy(date = date) }
    }

    fun getListWotyFofi() {
        viewModelScope.launch {
            val result = withContext(dispatcherProvider.io) {
                getListWotyFofiUseCase.invoke()
            }

            when (result) {
                is SuccessResult -> {
                    _dataState.update {
                        it.copy(dataCard = result.data.toComplexCardUI())
                    }
                }
                else -> {
                    _uiState.update {
                        it.copy(uiState = EnumUi.ERROR)
                    }
                }
            }
        }
    }

    /**
     * Actualiza el estado expandido de la tarjeta de título.
     *
     * @param expanded `true` para expandir, `false` para colapsar.
     */
    fun updateExpandedTitle(expanded: ModelComplexCard?) {
        _dataState.update { currentState ->
            currentState.copy(
                dataCard = currentState.dataCard?.map { card ->
                    if (card.idTitle == expanded?.idTitle) {
                        card.copy(isExpandedTitle = !expanded!!.isExpandedTitle)
                    } else card
                }
            )
        }
    }

    fun updateExpandedSubTitle(subItem: ModelSubComplexCard, parentItem: ModelComplexCard) {
        _dataState.update { currentState ->
            currentState.copy(
                dataCard = currentState.dataCard?.map { card ->
                    if (card.idTitle == parentItem.idTitle) {
                        val updatedList = card.list?.map { subCard ->
                            if (subCard?.idSubTitle == subItem.idSubTitle) {
                                subCard?.copy(isExpandedSubTitle = !subCard.isExpandedSubTitle)
                            } else subCard
                        }

                        card.copy(list = updatedList)

                    } else card
                }
            )
        }
        getListEvaluations(subItem.idSubTitle, parentItem.idTitle)
    }

    fun getListEvaluations(workTypeId: Int?, idTitle: Int?) {
        viewModelScope.launch {
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getListEvaluationsStudentUseCase.invoke(
                    formativeFieldId = idTitle,
                    workTypeId = workTypeId,
                    studentId = _uiState.value.student?.studentId,
                    workDate = _dataState.value.date
                )
            }

            when (result) {
                is SuccessResult -> {
                    _dataState.update { currentState ->
                        currentState.copy(
                            dataCard = currentState.dataCard?.map { card ->
                                if (card.idTitle == idTitle) {
                                    val updatedList = card.list?.map { subCard ->
                                        if (subCard?.idSubTitle == workTypeId) {
                                            subCard?.copy(
                                                list = result.data.map { item ->
                                                    ModelSubSubComplexCard(
                                                        idDescription = item.evaluationId,
                                                        nameDescription = item.evaluationName,
                                                        grade = item.grade,
                                                        isShowDescription = true
                                                    )
                                                }
                                            )
                                        } else subCard
                                    }
                                    card.copy(list = updatedList)
                                } else card
                            }
                        )
                    }
                }
                else -> {
                    _uiState.update {
                        it.copy(uiState = EnumUi.ERROR)
                    }
                }
            }
        }
    }
}