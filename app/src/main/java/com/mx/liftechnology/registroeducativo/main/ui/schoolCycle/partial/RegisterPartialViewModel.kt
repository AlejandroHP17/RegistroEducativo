package com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.partial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.ModelDatePeriodDomain
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterPartialWithValidationUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.events.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterPartialUiData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterPartialUiState
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
 * ViewModel for the Partial Registration screen.
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
    /** The data state for the screen. */
    val uiData: StateFlow<RegisterPartialUiData> = _uiData.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    /** Eventos de UI que deben ser manejados una sola vez (navegación, etc.) */
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    /**
     * Called when the number of partials changes.
     *
     * @param partial The new number of partials.
     */
    fun onPartialChanged(partial: String) {
        // Actualizaciones de estado simples no necesitan corrutinas
        if (partial.toIntOrNull() != null && partial.toInt() > 0) {
            val list = MutableList(partial.toInt()) { index ->
                ModelDatePeriodDomain(
                    position = index,
                    date = "".stringToModelStateOutFieldText(),
                    partialCycleGroup = 0
                )
            }

            _uiData.update {
                it.copy(
                    numberPartials = partial.stringToModelStateOutFieldText(),
                    listCalendar = list
                )
            }
        }
    }

    /**
     * Called when a date range changes.
     *
     * @param data A pair containing the date range and the index of the item that changed.
     */
    fun onDateChange(data: Pair<Pair<LocalDate?, LocalDate?>, Int>) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _uiData.update { currentState ->
            currentState.copy(
                listCalendar = currentState.listCalendar?.mapIndexed { index, date ->
                    if (index == data.second) {
                        val startDate = data.first.first?.toString() ?: ""
                        val endDate = data.first.second?.toString() ?: ""
                        date.copy(
                            date = "$startDate / $endDate".stringToModelStateOutFieldText()
                        )
                    } else {
                        date
                    }
                }
            )
        }
    }

    /**
     * Validates the input fields and proceeds to register the partials if they are valid.
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
                        // Emitir evento de navegación en lugar de depender del estado
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
                // Si hay errores de validación, solo actualizar el estado
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    /**
     * Gets list of partials.
     */
    fun getListPartialCompose(){
        viewModelScope.launch {
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
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
     * Modifies the visibility of the toast message.
     *
     * @param show True to show the toast, false to hide it.
     */
    fun modifyShowToast(show: Boolean) {
        // Las actualizaciones de estado ya están en el hilo principal, no necesitan corrutina
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(showToast = show)
            )
        }
    }
}