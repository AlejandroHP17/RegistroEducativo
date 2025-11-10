package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.logInfo
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatAssignment
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.SaveIdSubjectSelectedUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.assignment.GetListAssignmentPerSubjectUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.DomainToUIMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelAssignmentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelAssignmentStateUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard
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
class AssignmentStudentViewModel (
    private val dispatcherProvider: DispatcherProvider,
    private val getListAssignmentPerSubjectUseCase: GetListAssignmentPerSubjectUseCase,
    private val saveIdSubjectSelectedUseCase: SaveIdSubjectSelectedUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ModelAssignmentStateUI())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ModelAssignmentStateUI> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelAssignmentDataState())
    /** The data state for the screen. */
    val dataState: StateFlow<ModelAssignmentDataState> = _dataState.asStateFlow()

    /**
     * Updates the current student.
     *
     * @param student The new student.
     */
    fun updateStudent(student: ModelStudentDomain?) {
        _uiState.update { it.copy(student =  student) }
    }

    private fun getListAssessmentType(subject: ModelFormatFormativeFieldsDomain?) {
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getListAssignmentPerSubjectUseCase.invoke()) {
                is SuccessResult -> {
                    val convertData = DomainToUIMapper.mapSubjectToComplexCard(subject)
                   fillModel(result.result, convertData)
                }

                else -> {
                    logInfo(result.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }
            }
        }
    }

    private fun fillModel(result: List<ModelFormatAssignment>?, convertData: ModelComplexCard?) {
        val data = ModelComplexCard(
            idTitle = convertData?.idTitle,
            nameTitle = convertData?.nameTitle,
            isShowTitle = convertData?.isShowTitle ?: false,
            isExpandedTitle = convertData?.isExpandedTitle?: false,
            list = DomainToUIMapper.mapAssignmentListToSubComplexCard(result),
        )
        _dataState.update {
            it.copy(
                dataCard = data
            )
        }
    }

    /**
     * Updates the expanded state of the title card.
     *
     * @param expanded True to expand, false to collapse.
     */
    fun updateExpandedTitle(expanded: Boolean) {
        _dataState.update { currentState ->
            currentState.copy(
                dataCard = currentState.dataCard?.copy(
                    isExpandedTitle = expanded
                )
            )
        }
    }

    /**
     * Updates the expanded state of a subtitle card.
     *
     * @param subTitleId The ID of the subtitle to update.
     * @param expanded True to expand, false to collapse.
     */
    fun updateExpandedSubTitle(subTitleId: Int, expanded: Boolean) {
        _dataState.update { currentState ->
            currentState.copy(
                dataCard = currentState.dataCard?.copy(
                    list = currentState.dataCard.list?.map { subCard ->
                        if (subCard?.idSubTitle == subTitleId) {
                            subCard.copy(isExpandedSubTitle = expanded)
                        } else subCard
                    }
                )
            )
        }
    }
}