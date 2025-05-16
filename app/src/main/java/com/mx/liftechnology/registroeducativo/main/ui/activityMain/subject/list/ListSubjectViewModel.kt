package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.GetListSubjectUseCase
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelListSubjectUIState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListSubjectViewModel(
    private val getListSubjectUseCase: GetListSubjectUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelListSubjectUIState())
    val uiState: StateFlow<ModelListSubjectUIState> = _uiState.asStateFlow()

    fun getSubject() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                getListSubjectUseCase.getListSubject()
            }.onSuccess { state ->
                if (state is SuccessState) {
                    _uiState.update { it.copy(
                        subjectList = state.result,
                        subjectListUI = state.result.convertModelCustomCard()
                    ) }
                }
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun List<ModelFormatSubjectDomain>?.convertModelCustomCard():List<ModelCustomCard>{
        return this?.map {
            ModelCustomCard(
                id = it.subjectId.toString(),
                numberList = "",
                nameCard = "${it.name}"
            )
        }?: emptyList()
    }

    fun getSubject(item: ModelCustomCard): ModelFormatSubjectDomain? {
        return _uiState.value.subjectList?.find { it.subjectId.toString() == item.id }
    }
}