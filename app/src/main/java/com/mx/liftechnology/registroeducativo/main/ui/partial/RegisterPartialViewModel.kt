package com.mx.liftechnology.registroeducativo.main.ui.partial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.usecase.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.partial.RegisterPartialWithValidationUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.partial.RegisterPartialUiData
import com.mx.liftechnology.registroeducativo.main.model.partial.RegisterPartialUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

/**
 * ViewModel para la pantalla de registro de parciales.
 * 
 * Gestiona el estado de la UI, la validación de campos y la comunicación con los casos de uso.
 * Permite registrar múltiples parciales con sus respectivos rangos de fechas.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property registerPartialWithValidationUseCase El caso de uso para registrar parciales con validación.
 * @property getListPartialUseCase El caso de uso para obtener la lista de parciales existentes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterPartialViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerPartialWithValidationUseCase: RegisterPartialWithValidationUseCase,
    private val getListPartialUseCase: GetListPartialUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterPartialUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<RegisterPartialUiState> = _uiState.asStateFlow()

    private val _uiData = MutableStateFlow(RegisterPartialUiData())
    /** El estado de datos de la pantalla. */
    val uiData: StateFlow<RegisterPartialUiData> = _uiData.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    /** Eventos de UI que deben ser manejados una sola vez (navegación, etc.) */
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    /**
     * Se llama cuando cambia el número de parciales.
     *
     * @param partial El nuevo número de parciales.
     */
    fun onPartialChanged(partial: String) {
        if (partial.toIntOrNull() != null && partial.toInt() > 0) {
            val list = MutableList(partial.toInt()) { index ->
                DatePeriodDomain(
                    position = index,
                    date = "".stringToModelStateOutFieldText(),
                    partialCycleGroup = 0
                )
            }
            val listDate = MutableList(partial.toInt()){ _ ->
                null
            }

            _uiData.update {
                it.copy(
                    numberPartials = partial.stringToModelStateOutFieldText(),
                    listCalendar = list,
                    rangeDate = listDate
                )
            }
        }
    }

    /**
     * Se llama cuando cambia un rango de fechas.
     *
     * @param data Un par que contiene el rango de fechas y el índice del elemento que cambió.
     */
    fun onDateChange(data: Pair<Pair<LocalDate, LocalDate>, Int>) {
        val startDate = data.first.first
        val endDate = data.first.second
        _uiData.update { currentState ->
            currentState.copy(
                listCalendar = currentState.listCalendar?.mapIndexed { index, date ->
                    if (index == data.second) {

                        date.copy(
                            date = "$startDate / $endDate".stringToModelStateOutFieldText()
                        )
                    } else {
                        date
                    }
                },
                rangeDate =  currentState.rangeDate?.mapIndexed { index, pair ->
                    if (index == data.second) {
                        Pair(startDate,endDate)
                    }
                    else{
                        pair
                    }
                }
            )
        }
    }

    /**
     * Valida los campos de entrada y procede a registrar los parciales si son válidos.
     * La lógica de validación + operación está encapsulada en el Use Case.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            
            // El Use Case combina validación + operación
            val result = withContext(dispatcherProvider.io) {
                registerPartialWithValidationUseCase.invoke(
                    numberPartials = _uiData.value.numberPartials.valueText,
                    listCalendar = _uiData.value.listCalendar
                )
            }

            val validationResult = result.validationResult

            // Actualizar los estados de validación de los campos
            _uiData.update {
                it.copy(
                    numberPartials = validationResult.validationStates["numberPartials"] ?: it.numberPartials,
                    listCalendar = result.updatedListCalendar ?: it.listCalendar
                )
            }

            // Si las validaciones pasaron, manejar el resultado de la operación
            if (validationResult.isValid && validationResult.operationResult != null) {
                when (val operationResult = validationResult.operationResult) {
                    is SuccessResult -> {
                        _uiState.update { it.copy(
                            uiState = ModelStateUIEnum.SUCCESS,
                            controlToast = ToastUiState(
                                messageToast = R.string.toast_success_register_partial,
                                showToast = true,
                                typeToast = ModelStateTypeToastUI.SUCCESS
                            )
                        ) }
                        
                        _uiEvent.emit(UiEvent.NavigateBack)
                    }
                    is ErrorResult -> {
                        val userError = ErrorMapper.mapErrorToUI(operationResult.error)
                        val messageRes = ErrorToMessageMapper.mapErrorToMessage(
                            error = userError,
                            context = ErrorToMessageMapper.ErrorContext.REGISTER_PARTIAL
                        )

                        _uiState.update {
                            it.copy(
                                uiState = ModelStateUIEnum.ERROR,
                                controlToast = messageRes?.let { msg ->
                                    ToastUiState(
                                        messageToast = msg,
                                        showToast = true,
                                        typeToast = ModelStateTypeToastUI.ERROR
                                    )
                                } ?: it.controlToast.copy(showToast = false)
                            )
                        }
                    }
                    else -> {}
                }
            } else {
                
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    /**
     * Obtiene la lista de parciales desde el servidor.
     */
    fun getListPartialCompose(){
        viewModelScope.launch {
            val result = withContext(dispatcherProvider.io) {
                getListPartialUseCase.invoke()
            }

            when(result) {
                is SuccessResult -> {
                    _uiState.update { item ->
                        item.copy(isAvailable = false)
                    }
                    _uiData.update { item ->
                        item.copy(
                            read = true,
                            listCalendar = result.data?.map { data ->
                                data.copy(date = data.date)
                            } ?: emptyList(),
                            numberPartials = result.data?.size?.toString().stringToModelStateOutFieldText()
                        )
                    }
                }
                is ErrorResult -> {
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.ERROR)
                    }
                }
            }
        }
    }

    /**
     * Modifica la visibilidad del mensaje toast.
     *
     * @param show `true` para mostrar el toast, `false` para ocultarlo.
     */
    fun modifyShowToast(show: Boolean) {
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(showToast = show)
            )
        }
    }
}