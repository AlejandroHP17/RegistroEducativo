package com.mx.liftechnology.registroeducativo.main.ui.student.wotyfofi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.formativeField.GetListWotyFofiUseCase
import com.mx.liftechnology.domain.usecase.student.GetListEvaluationsStudentUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.DomainToUIMapper.toComplexCardUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.WotyFofiUiData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.WotyFofiUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelSubComplexCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelSubSubComplexCard
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Student Assignment screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class WotyFofiStudentViewModel (
    private val dispatcherProvider: DispatcherProvider,
    private val getListWotyFofiUseCase: GetListWotyFofiUseCase,
    private val getListEvaluationsStudentUseCase: GetListEvaluationsStudentUseCase

    ): ViewModel() {
    private val _uiState = MutableStateFlow(WotyFofiUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<WotyFofiUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(WotyFofiUiData())
    /** The data state for the screen. */
    val dataState: StateFlow<WotyFofiUiData> = _dataState.asStateFlow()

    /**
     * Updates the current student.
     *
     * @param student The new student.
     */
    fun updateStudent(student: ModelStudentDomain?) {
        _uiState.update { it.copy(student =  student) }
    }

    fun getListWotyFofi(){
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getListWotyFofiUseCase.invoke()){
                is SuccessResult ->{
                    _dataState.update {
                        it.copy(
                            dataCard = result.data.toComplexCardUI()
                        )
                    }
                }
                else -> {
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }
            }
        }
    }

    /**
     * Updates the expanded state of the title card.
     *
     * @param expanded True to expand, false to collapse.
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
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getListEvaluationsStudentUseCase.invoke(
                formativeFieldId = idTitle,
                workTypeId = workTypeId,
                studentId = _uiState.value.student?.studentId
            )){
                is SuccessResult ->{
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
                                                })
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
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }
            }
        }
    }
}