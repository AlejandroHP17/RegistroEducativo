package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.usecase.formativeField.DeleteFormativeFieldsUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetListSubjectUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.DomainToUIMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ListFormativeFieldsUiData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ListFormativeFieldsUiState
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
class ListFormativeFieldsViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListSubjectUseCase: GetListSubjectUseCase,
    private val deleteFormativeFieldsUseCase: DeleteFormativeFieldsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListFormativeFieldsUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ListFormativeFieldsUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ListFormativeFieldsUiData())
    /** The data state for the screen. */
    val dataState: StateFlow<ListFormativeFieldsUiData> = _dataState.asStateFlow()

    /**
     * Gets the list of subjects.
     */
    fun getFormativeFields() {
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            when(val result = getListSubjectUseCase.invoke()){
                is SuccessResult -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(
                        formativeFieldsList = result.data,
                        formativeFieldsListUI = DomainToUIMapper.mapSubjectListToCustomCard(result.data),
                    ) }
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(formativeFieldsList = emptyList()) }
                }
            }
        }
    }

    /**
     * Gets a subject by its ID.
     *
     * @param item The custom card model of the subject to get.
     * @return The [ModelFormatFormativeFieldsDomain] object, or null if not found.
     */
    fun getFormativeFields(item: ModelCustomCard): ModelFormatFormativeFieldsDomain? = _dataState.value.formativeFieldsList?.find { it.formativeFieldId == item.id }

    fun deleteFormativeField(card: ModelCustomCard) {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            when(deleteFormativeFieldsUseCase.invoke(card.id)){
                is SuccessResult -> {
                    getFormativeFields()
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                }
            }
        }
    }
}