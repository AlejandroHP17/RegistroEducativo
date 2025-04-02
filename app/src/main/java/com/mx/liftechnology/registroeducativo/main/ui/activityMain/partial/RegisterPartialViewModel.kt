package com.mx.liftechnology.registroeducativo.main.ui.activityMain.partial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterPartialUIState
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

    fun onPartialChanged(partial: String) {
        if (partial.toInt() > 0) {
            val list = MutableList(partial.toInt()) { index ->
                ModelDatePeriodDomain(
                    position = index,
                    date = ""
                )
            }

            _uiState.update {
                it.copy(
                    numberPartials = partial,
                    listCalendar = list,
                    isErrorOption = ModelStateOutFieldText(false, "")
                )
            }
        }
    }

    fun onDateChange(data: Pair<Pair<LocalDate?, LocalDate?>, Int>) {
        _uiState.update { currentState ->
            currentState.copy(
                listCalendar = currentState.listCalendar?.mapIndexed { index, date ->
                    if (index == data.second) {

                        val startDate = data.first.first?.toString() ?: ""
                        val endDate = data.first.second?.toString() ?: ""
                         // Guardamos el rango de fechas en formato "YYYY-MM-DD - YYYY-MM-DD"
                        date.copy(
                            date  = "$startDate / $endDate",
                            isErrorDate = ModelStateOutFieldText(
                                isError = false,
                                errorMessage = ""
                            ),
                        )
                    } else {
                        date // No cambia si no es el índice seleccionado
                    }
                }
            )
        }
    }

    fun validateFieldsCompose() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(dispatcherProvider.io) {

            val periodState = validateFieldsRegisterPartialUseCase.validatePeriod(_uiState.value.numberPartials)
            val listCalendarState = validateFieldsRegisterPartialUseCase.validateAdapter(_uiState.value.listCalendar)
            val calendarState = validateFieldsRegisterPartialUseCase.validateAdapterError(listCalendarState)

            _uiState.update {
                it.copy(
                    isErrorOption = periodState,
                    listCalendar = listCalendarState
                )
            }

            if (!(periodState.isError || calendarState.isError))
                registerListPartialCompose()
            else _uiState.update { it.copy(isLoading = false) }

        }
    }

    private fun registerListPartialCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerListPartialUseCase.registerListPartialCompose(_uiState.value.numberPartials.toInt(), _uiState.value.listCalendar!!)
            }.onSuccess { state ->
                if(state is SuccessState){
                    _uiState.update { it.copy(
                        isLoading = false,
                        isSuccess = true
                    ) }
                }else{
                    _uiState.update { it.copy(
                        isLoading = false,
                        isSuccess = false
                    ) }
                }
            }.onFailure {
                _uiState.update { it.copy(
                    isLoading = false,
                    isSuccess = false
                ) }
            }
        }
    }

    fun getListPartialCompose(){
        viewModelScope.launch (dispatcherProvider.io) {
            runCatching {
                getListPartialUseCase.getListPartial()
            }.onSuccess {state ->
                if(state is SuccessState){
                    _uiState.update { item ->
                        item.copy(
                            listCalendar = state.result?.map { data ->
                                data.copy(date = data.date)
                            } ?: emptyList(),
                            numberPartials = state.result?.size?.toString().orEmpty()
                        )
                    }
                }
            }.onFailure {
            }
        }
    }

}