package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.wotyfofi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.usecase.evaluation.GetListWorkEvaluationFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetListByFieldTypeStudentUseCase
import com.mx.liftechnology.domain.usecase.formativeField.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.DomainToUIMapper.toComplexCardUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelWotyFofiDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelWotyFofiStateUI
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
 * ViewModel for the Subject Assignment screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class WotyFofiViewModel (
    private val dispatcherProvider: DispatcherProvider,
    private val getListWorkEvaluationFormativeFieldUseCase: GetListWorkEvaluationFormativeFieldUseCase,
    private val getListByFieldTypeStudentUseCase: GetListByFieldTypeStudentUseCase,
    private val saveFormativeFieldIdSelectedUseCase: SaveFormativeFieldIdSelectedUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ModelWotyFofiStateUI())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ModelWotyFofiStateUI> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelWotyFofiDataState())
    /** The data state for the screen. */
    val dataState: StateFlow<ModelWotyFofiDataState> = _dataState.asStateFlow()

    /**
     * Updates the current subject.
     *
     * @param subject The new subject.
     */
    fun updateSubject(subject: ModelFormatFormativeFieldsDomain?) {
        saveFormativeFieldIdSelectedUseCase.invoke(subject?.formativeFieldId)
        _uiState.update { it.copy(formativeFields =  subject) }
    }

    fun getListWotyFofi(){
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getListWorkEvaluationFormativeFieldUseCase.invoke()){
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
        getListEvaluationsStudents(subItem.idSubTitle, subItem.nameSubTitle, subItem.date, parentItem.idTitle)
    }

    private fun getListEvaluationsStudents(
        idSubTitle: Int?,
        workName: String?,
        workDate: String?,
        idTitle: Int?
    ) {
        viewModelScope.launch (dispatcherProvider.io){
            when (val result = getListByFieldTypeStudentUseCase.invoke(
                workTypeId = idTitle,
                workName = workName,
                workDate = workDate
            )){
            is SuccessResult ->{
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