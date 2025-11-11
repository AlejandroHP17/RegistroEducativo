package com.mx.liftechnology.registroeducativo.main.ui.flowMain.partial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.logInfo
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.data.util.UserError
import com.mx.liftechnology.domain.model.schoolCycle.ModelDatePeriodDomain
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterPartialStateUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterPartialUIData
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel for the Partial Registration screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterPartialViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsRegisterPartialUseCase: ValidateFieldsRegisterPartialUseCase,
    private val registerListPartialUseCase: RegisterListPartialUseCase,
    private val getListPartialUseCase: GetListPartialUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterPartialStateUI())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<ModelRegisterPartialStateUI> = _uiState.asStateFlow()

    private val _uiData = MutableStateFlow(ModelRegisterPartialUIData())
    /** The data state for the screen. */
    val uiData: StateFlow<ModelRegisterPartialUIData> = _uiData.asStateFlow()

    /**
     * Called when the number of partials changes.
     *
     * @param partial The new number of partials.
     */
    fun onPartialChanged(partial: String) {
        viewModelScope.launch (dispatcherProvider.io){
            if (partial.toInt() > 0) {
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
    }

    /**
     * Called when a date range changes.
     *
     * @param data A pair containing the date range and the index of the item that changed.
     */
    fun onDateChange(data: Pair<Pair<LocalDate?, LocalDate?>, Int>) {
        viewModelScope.launch (dispatcherProvider.io){
            _uiData.update { currentState ->
                currentState.copy(
                    listCalendar = currentState.listCalendar?.mapIndexed { index, date ->
                        if (index == data.second) {

                            val startDate = data.first.first?.toString() ?: ""
                            val endDate = data.first.second?.toString() ?: ""
                            date.copy(
                                date  = "$startDate / $endDate".stringToModelStateOutFieldText()
                            )
                        } else {
                            date
                        }
                    }
                )
            }
        }
    }

    /**
     * Validates the input fields and proceeds to register the partials if they are valid.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch(dispatcherProvider.io) {

            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            val periodState = validateFieldsRegisterPartialUseCase.validatePeriod(_uiData.value.numberPartials.valueText)
            val listCalendarState = validateFieldsRegisterPartialUseCase.validateAdapter(_uiData.value.listCalendar)
            val calendarState = validateFieldsRegisterPartialUseCase.validateAdapterError(listCalendarState)

            _uiData.update {
                it.copy(
                    numberPartials = periodState,
                    listCalendar = listCalendarState
                )
            }

            if (!(periodState.isError || calendarState.isError))
                registerListPartialCompose()
            else _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
        }
    }

    private suspend fun registerListPartialCompose() {
        when (val result =  registerListPartialUseCase.invoke(
            adapterPeriods = _uiData.value.listCalendar!!
        )){
            is SuccessResult -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.SUCCESS,
                    controlToast = ModelStateToastUI(
                        messageToast = R.string.toast_success_register_partial,
                        showToast = true,
                        typeToast = ModelStateTypeToastUI.SUCCESS
                    )
                ) }
            }
            is ErrorResult -> {
                val msg = when(ErrorMapper.mapErrorToUI(result.error)){
                    UserError.SHOW_GENERIC_ERROR -> R.string.toast_error_generic
                    UserError.SHOW_SPECIFIC_ERROR -> R.string.toast_error_register_partials
                    else -> null
                }

                if(msg != null){
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR,
                            controlToast = ModelStateToastUI(
                                messageToast = msg,
                                showToast = true,
                                typeToast = ModelStateTypeToastUI.ERROR
                            )
                        )
                    }
                }else{
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.ERROR) }
                }
            }
        }
    }

    /**
     * Gets list of partials.
     */
    fun getListPartialCompose(){
        viewModelScope.launch (dispatcherProvider.io) {
            when(val result = getListPartialUseCase.invoke()){
                is SuccessResult -> {
                    _uiState.update { item ->
                        item.copy(
                            isAvailable = false
                        )
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
                is ErrorResult  -> {
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

    /**
     * Modifies the visibility of the toast message.
     *
     * @param show True to show the toast, false to hide it.
     */
    fun modifyShowToast(show: Boolean) {
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update {
                it.copy(
                    controlToast = ModelStateToastUI(
                        messageToast = it.controlToast.messageToast,
                        showToast = show,
                        typeToast = it.controlToast.typeToast
                    )
                )
            }
        }
    }
}