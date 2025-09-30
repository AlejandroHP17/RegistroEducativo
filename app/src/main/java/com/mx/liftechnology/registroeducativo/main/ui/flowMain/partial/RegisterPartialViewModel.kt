package com.mx.liftechnology.registroeducativo.main.ui.flowMain.partial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterPartialUIData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterPartialUIState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class RegisterPartialViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsRegisterPartialUseCase: ValidateFieldsRegisterPartialUseCase,
    private val registerListPartialUseCase: RegisterListPartialUseCase,
    private val getListPartialUseCase: GetListPartialUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterPartialUIState())
    val uiState: StateFlow<ModelRegisterPartialUIState> = _uiState.asStateFlow()

    private val _uiData = MutableStateFlow(ModelRegisterPartialUIData())
    val uiData: StateFlow<ModelRegisterPartialUIData> = _uiData.asStateFlow()

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

    fun onDateChange(data: Pair<Pair<LocalDate?, LocalDate?>, Int>) {
        viewModelScope.launch (dispatcherProvider.io){
            _uiData.update { currentState ->
                currentState.copy(
                    listCalendar = currentState.listCalendar?.mapIndexed { index, date ->
                        if (index == data.second) {

                            val startDate = data.first.first?.toString() ?: ""
                            val endDate = data.first.second?.toString() ?: ""
                            // Guardamos el rango de fechas en formato "YYYY-MM-DD - YYYY-MM-DD"
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
            _uiData.value.numberPartials.valueText.toInt(),
            _uiData.value.listCalendar!!
        )){
            is SuccessState -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.SUCCESS,
                    controlToast = ModelStateToastUI(
                        messageToast = R.string.toast_success_register_partial,
                        showToast = true,
                        typeToast = ModelStateTypeToastUI.SUCCESS
                    )
                ) }
            }
            is ErrorUserState -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.ERROR,
                    controlToast = ModelStateToastUI(
                        messageToast = R.string.toast_error_register_partials,
                        showToast = true,
                        typeToast = ModelStateTypeToastUI.ERROR
                    )
                ) }
            }
            else -> {
                logs(result.toString())
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.ERROR
                ) }
            }
        }
    }

    fun getListPartialCompose(){
        viewModelScope.launch (dispatcherProvider.io) {
            when(val result = getListPartialUseCase.invoke()){
                is SuccessState -> {
                    _uiState.update { item ->
                        item.copy(
                            isAvailable = false
                        )
                    }
                    _uiData.update { item ->
                        item.copy(
                            listCalendar = result.result?.map { data ->
                                data.copy(date = data.date)
                            } ?: emptyList(),
                            numberPartials = result.result?.size?.toString().stringToModelStateOutFieldText()
                        )
                    }
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