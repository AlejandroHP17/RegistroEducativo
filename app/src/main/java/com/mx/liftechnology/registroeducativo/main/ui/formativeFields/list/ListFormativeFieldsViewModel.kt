package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.domain.usecase.formativeField.DeleteFormativeFieldsUseCase
import com.mx.liftechnology.domain.usecase.share.GetListFormativeFieldUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.FormativeFieldMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.ListFormativeFieldsUiData
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.ListFormativeFieldsUiState
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.toFormativeFieldDomainList
import com.mx.liftechnology.registroeducativo.main.model.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla de lista de campos formativos (materias).
 * 
 * Gestiona el estado de la UI, la obtención de la lista de campos formativos y la eliminación de campos formativos.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property getListFormativeFieldUseCase El caso de uso para obtener la lista de campos formativos.
 * @property deleteFormativeFieldsUseCase El caso de uso para eliminar un campo formativo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ListFormativeFieldsViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListFormativeFieldUseCase: GetListFormativeFieldUseCase,
    private val deleteFormativeFieldsUseCase: DeleteFormativeFieldsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListFormativeFieldsUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ListFormativeFieldsUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ListFormativeFieldsUiData())
    /** El estado de los datos de la pantalla. */
    val dataState: StateFlow<ListFormativeFieldsUiData> = _dataState.asStateFlow()

    /**
     * Obtiene la lista de campos formativos desde el servidor.
     */
    fun getFormativeFields() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getListFormativeFieldUseCase.invoke()
            }

            when(result) {
                is SuccessResult -> {
                    val listFormativeField = result.data?.toFormativeFieldDomainList()
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(
                        formativeFieldsList = listFormativeField,
                        formativeFieldsListUI = FormativeFieldMapper.mapFormativeFieldListToCustomCard(listFormativeField),
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
     * Obtiene un campo formativo por su ID.
     *
     * @param item El modelo de tarjeta personalizada del campo formativo a obtener.
     * @return El objeto [FormativeFieldDomainPar], o null si no se encuentra.
     */
    fun getFormativeFields(item: ModelCustomCard): FormativeFieldDomainPar? = _dataState.value.formativeFieldsList?.find { it.formativeFieldId == item.id }

    /**
     * Elimina un campo formativo de la lista.
     *
     * @param card El modelo de tarjeta personalizada del campo formativo a eliminar.
     */
    fun deleteFormativeField(card: ModelCustomCard) {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                deleteFormativeFieldsUseCase.invoke(card.id)
            }

            when(result) {
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