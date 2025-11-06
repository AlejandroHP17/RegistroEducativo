package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.logInfo
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.GetListSubjectUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.DomainToUIMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListSubjectDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListSubjectStateUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Subject List screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ListSubjectViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListSubjectUseCase: GetListSubjectUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelListSubjectStateUI())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ModelListSubjectStateUI> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelListSubjectDataState())
    /** The data state for the screen. */
    val dataState: StateFlow<ModelListSubjectDataState> = _dataState.asStateFlow()

    /**
     * Gets the list of subjects.
     */
    fun getSubject() {
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            when(val result = getListSubjectUseCase.invoke()){
                is SuccessResult -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(
                        subjectList = result.result,
                        subjectListUI = DomainToUIMapper.mapSubjectListToCustomCard(result.result),
                    ) }
                }
                else -> {
                    logInfo(result.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR,
                        )
                    }
                }
            }
        }
    }

    /**
     * Gets a subject by its ID.
     *
     * @param item The custom card model of the subject to get.
     * @return The [ModelFormatSubjectDomain] object, or null if not found.
     */
    fun getSubject(item: ModelCustomCard): ModelFormatSubjectDomain? = _dataState.value.subjectList?.find { it.subjectId.toString() == item.id }
}