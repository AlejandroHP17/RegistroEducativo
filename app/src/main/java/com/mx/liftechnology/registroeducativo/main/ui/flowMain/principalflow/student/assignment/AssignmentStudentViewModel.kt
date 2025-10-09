package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatAssignment
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.SaveIdSubjectSelectedUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.GetListAssignmentPerSubjectUseCase
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelAssignmentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelAssignmentUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.toModelSubComplexCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.toModelComplexCard
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AssignmentStudentViewModel (
    private val dispatcherProvider: DispatcherProvider,
    private val getListAssignmentPerSubjectUseCase: GetListAssignmentPerSubjectUseCase,
    private val saveIdSubjectSelectedUseCase: SaveIdSubjectSelectedUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ModelAssignmentUiState())
    val uiState: StateFlow<ModelAssignmentUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelAssignmentDataState())
    val dataState: StateFlow<ModelAssignmentDataState> = _dataState.asStateFlow()

    fun updateStudent(student: ModelStudentDomain?) {
        /*saveIdSubjectSelectedUseCase.invoke(subject?.subjectId)
        getListAssessmentType(subject)*/
        _uiState.update { it.copy(student =  student) }
    }

    private fun getListAssessmentType(subject: ModelFormatSubjectDomain?) {
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getListAssignmentPerSubjectUseCase.invoke()) {
                is SuccessState -> {
                    val convertData = subject?.toModelComplexCard()
                   fillModel(result.result, convertData)
                }

                else -> {
                    logs(result.toString())
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
            list = result.toModelSubComplexCard(),
        )
        _dataState.update {
            it.copy(
                dataCard = data
            )
        }
    }

    fun updateExpandedTitle(expanded: Boolean) {
        _dataState.update { currentState ->
            currentState.copy(
                dataCard = currentState.dataCard?.copy(
                    isExpandedTitle = expanded
                )
            )
        }
    }

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