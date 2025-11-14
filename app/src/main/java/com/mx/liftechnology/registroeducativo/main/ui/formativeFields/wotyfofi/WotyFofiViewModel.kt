package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.wotyfofi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.usecase.formativeField.GetWorkTypeByFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.formativeField.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.DomainToUIMapper.toComplexCardUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelWotyFofiDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelWotyFofiStateUI
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
    private val getWorkTypeByFormativeFieldUseCase: GetWorkTypeByFormativeFieldUseCase,
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
        _uiState.update { it.copy(subject =  subject) }
    }

    fun getListWotyFofi(){
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getWorkTypeByFormativeFieldUseCase.invoke()){
                is com.mx.liftechnology.data.util.SuccessResult ->{
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
    fun updateExpandedTitle(expanded: Pair<Boolean, Int>) {
        _dataState.update { currentState ->
            currentState.copy(
                dataCard = currentState.dataCard?.map { card ->
                    if (card.idTitle == expanded.second) {
                        card.copy(isExpandedTitle = expanded.first)
                    } else card
                }
            )
        }
    }
}