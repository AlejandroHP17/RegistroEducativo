package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.GetListSubjectUseCase
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelListSubjectDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelListSubjectUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListSubjectViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListSubjectUseCase: GetListSubjectUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelListSubjectUiState())
    val uiState: StateFlow<ModelListSubjectUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelListSubjectDataState())
    val dataState: StateFlow<ModelListSubjectDataState> = _dataState.asStateFlow()

    fun getSubject() {
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            when(val result = getListSubjectUseCase.invoke()){
                is SuccessState -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(
                        subjectList = result.result,
                        subjectListUI = result.result.convertModelCustomCard(),
                    ) }
                }
                else -> {
                    logs(result.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR,
                        )
                    }
                }
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

    fun getSubject(item: ModelCustomCard): ModelFormatSubjectDomain? = _dataState.value.subjectList?.find { it.subjectId.toString() == item.id }
}