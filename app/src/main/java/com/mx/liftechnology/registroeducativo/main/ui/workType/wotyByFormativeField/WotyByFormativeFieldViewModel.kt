package com.mx.liftechnology.registroeducativo.main.ui.workType.wotyByFormativeField

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.usecase.share.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListByFieldTypeStudentUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListWorkEvaluationFormativeFieldUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.FormativeFieldMapper.toComplexCardUI
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.registroeducativo.main.model.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.share.ModelSubComplexCard
import com.mx.liftechnology.registroeducativo.main.model.share.ModelSubSubComplexCard
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyFofiUiData
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyFofiUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla de asignaciones por campo formativo (materia).
 * 
 * Gestiona el estado de la UI y la obtención de evaluaciones agrupadas por tipo de trabajo
 * y estudiantes para un campo formativo específico.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property getListWorkEvaluationFormativeFieldUseCase El caso de uso para obtener las evaluaciones de trabajo por campo formativo.
 * @property getListByFieldTypeStudentUseCase El caso de uso para obtener la lista de estudiantes por tipo de campo formativo.
 * @property saveFormativeFieldIdSelectedUseCase El caso de uso para guardar el ID del campo formativo seleccionado.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class WotyByFormativeFieldViewModel (
    private val dispatcherProvider: DispatcherProvider,
    private val getListWorkEvaluationFormativeFieldUseCase: GetListWorkEvaluationFormativeFieldUseCase,
    private val getListByFieldTypeStudentUseCase: GetListByFieldTypeStudentUseCase,
    private val saveFormativeFieldIdSelectedUseCase: SaveFormativeFieldIdSelectedUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(WotyFofiUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<WotyFofiUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(WotyFofiUiData())
    /** El estado de datos de la pantalla. */
    val dataState: StateFlow<WotyFofiUiData> = _dataState.asStateFlow()

    /**
     * Actualiza la materia actual.
     *
     * @param formativeField La nueva materia.
     */
    fun updateFormativeField(formativeField: FormativeFieldDomainPar?) {
        viewModelScope.launch {
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            withContext(dispatcherProvider.io) {
                saveFormativeFieldIdSelectedUseCase.invoke(formativeField?.formativeFieldId)
            }
            _uiState.update { it.copy(formativeFields = formativeField) }
        }
    }

    fun getListWotyFormativeField(){
        viewModelScope.launch {
            val result = withContext(dispatcherProvider.io) {
                getListWorkEvaluationFormativeFieldUseCase.invoke()
            }

            when (result) {
                is SuccessResult -> {
                    _dataState.update {
                        it.copy(dataCard = result.data.toComplexCardUI())
                    }
                }
                else -> {
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.ERROR)
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
        getListEvaluationsStudents(subItem.idSubTitle, subItem.nameSubTitle, subItem.date, parentItem.idTitle)
    }

    private fun getListEvaluationsStudents(
        idSubTitle: Int?,
        workName: String?,
        workDate: String?,
        idTitle: Int?
    ) {
        viewModelScope.launch {
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getListByFieldTypeStudentUseCase.invoke(
                    workTypeId = idTitle,
                    workName = workName,
                    workDate = workDate
                )
            }

            when (result) {
                is SuccessResult -> {
                    _dataState.update { currentState ->
                        currentState.copy(
                            dataCard = currentState.dataCard?.map { card ->
                                if (card.idTitle == idTitle) {
                                    val updatedList = card.list?.map { subCard ->
                                        if (subCard?.idSubTitle == idSubTitle) {
                                            subCard?.copy(
                                                list = result.data.works.firstOrNull()?.listStudents?.map { item ->
                                                    ModelSubSubComplexCard(
                                                        idDescription = item.studentId,
                                                        nameDescription = item.studentName,
                                                        grade = item.grade?.toDouble(),
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
                        it.copy(uiState = ModelStateUIEnum.ERROR)
                    }
                }
            }
        }
    }

}